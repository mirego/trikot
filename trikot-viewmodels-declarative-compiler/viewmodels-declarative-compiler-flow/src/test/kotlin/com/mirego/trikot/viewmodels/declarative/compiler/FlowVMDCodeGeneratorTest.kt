package com.mirego.trikot.viewmodels.declarative.compiler

import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspWithCompilation
import com.tschuchort.compiletesting.symbolProcessorProviders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.superclasses
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FlowVMDCodeGeneratorTest {

    private fun compileSources(vararg kotlinSource: SourceFile): KotlinCompilation.Result =
        KotlinCompilation().apply {
            sources = kotlinSource.toList()
            symbolProcessorProviders = listOf(BuilderProcessorProvider())
            inheritClassPath = true
            kspWithCompilation = true
            compile()
        }.compile()

    @Test
    fun `When no class contains annotations nothing is generated `() {
        val result = compileSources(
            SourceFile.kotlin(
                name = "ViewModel1.kt",
                contents = """
                        interface ViewModel1 {
                            val field : String
                        }
                    """
            )
        )

        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        assertNull(
            result.generatedFiles.firstOrNull {
                it.name.contains("BaseViewModelImpl")
            }
        )
    }

    @Test
    fun `When an interface contains only Published annotations default class is generated `() {
        val result = compileSources(
            SourceFile.kotlin(
                name = "ViewModel1.kt",
                contents = """
                        import com.mirego.trikot.viewmodels.declarative.Published
                        import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
                
                        interface ViewModel1 : VMDViewModel {
                            @Published
                            val field : String
                        }
                        """
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val generatedClass = result.classLoader.loadClass("DefaultViewModel1Impl").kotlin

        generatedClass.isSuperclassOf(VMDViewModelImpl::class)
        assertFalse(generatedClass.isAbstract)

        assertNotNull(generatedClass.primaryConstructor).run {
            parameters[0].assertParam("fieldInitialValue", String::class)
            parameters[1].assertParam("coroutineScope", CoroutineScope::class)
        }

        generatedClass.assertMember(
            name = "fieldDelegate",
            type = VMDFlowProperty::class,
            typeArgument = String::class
        )

        generatedClass.assertMember(
            name = "field",
            type = String::class
        )

        generatedClass.getMandatoryFunction("bindField").run {
            assertParam("flow", Flow::class, String::class)
            assertReturnType(Unit::class)
        }

        generatedClass.getMandatoryFunction("bind").run {
            assertParam("field", Flow::class, String::class)
            assertReturnType(Unit::class)
        }

        generatedClass.getMandatoryFunction("flowForField").run {
            assertReturnType(type = Flow::class, typeParameter = String::class)
        }
    }

    @Test
    fun `When an interface contains some Published annotations abstract class is generated `() {
        val result = compileSources(
            SourceFile.kotlin(
                name = "ViewModel1.kt",
                contents = """
                        import com.mirego.trikot.viewmodels.declarative.Published
                        import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
                
                        interface ViewModel1 : VMDViewModel {
                            @Published
                            val field : String
                            val field2: String
                        }
                        """
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val generatedClass = result.classLoader.loadClass("BaseViewModel1Impl").kotlin

        generatedClass.isSuperclassOf(VMDViewModelImpl::class)
        assertTrue(generatedClass.isAbstract)

        assertNotNull(generatedClass.primaryConstructor).run {
            parameters[0].assertParam("fieldInitialValue", String::class)
            parameters[1].assertParam("coroutineScope", CoroutineScope::class)
        }

        generatedClass.assertMember(
            name = "fieldDelegate",
            type = VMDFlowProperty::class,
            typeArgument = String::class
        )

        generatedClass.assertMember(
            name = "field",
            type = String::class
        )

        generatedClass.getMandatoryFunction("bindField").run {
            assertParam("flow", Flow::class, String::class)
            assertReturnType(Unit::class)
        }

        generatedClass.getMandatoryFunction("bind").run {
            assertParam("field", Flow::class, String::class)
            assertReturnType(Unit::class)
        }

        generatedClass.getMandatoryFunction("flowForField").run {
            assertReturnType(type = Flow::class, typeParameter = String::class)
        }
    }

    @Test
    fun `When a class declares the PublishedSubClass annotation then the generated base class extends the specified parent `() {
        val result = compileSources(
            SourceFile.kotlin(
                name = "BaseClass.kt",
                contents = """
                        import com.mirego.trikot.viewmodels.declarative.PublishedSubClass
                        import kotlinx.coroutines.CoroutineScope

                        @PublishedSubClass(superClass = SuperClass::class)
                        class BaseClass(coroutineScope: CoroutineScope): ViewModel1, DefaultViewModel1Impl("value", coroutineScope)
                        """
            ),
            SourceFile.kotlin(
                name = "ViewModel1.kt",
                contents = """
                        import com.mirego.trikot.viewmodels.declarative.Published
                        import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
                
                        interface ViewModel1 : VMDViewModel {
                            @Published
                            val field : String
                        }
                        """
            ),
            SourceFile.kotlin(
                name = "SuperClass.kt",
                contents = """
                        import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
                        import kotlinx.coroutines.CoroutineScope

                        open class SuperClass(coroutineScope: CoroutineScope): VMDViewModelImpl(coroutineScope)
                        """
            )
        )

        val generatedClass = result.classLoader.loadClass("DefaultViewModel1Impl").kotlin
        assertNotNull(
            generatedClass.superclasses
                .firstOrNull {
                    it.qualifiedName == "SuperClass"
                }
        )
    }
}
