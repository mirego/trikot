package com.mirego.trikot.viewmodels

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AndroidViewModelProviderFactoryTest {

    private val viewModelFactory = TestViewModelFactory()

    @Test
    fun testFragment_noArguments() {
        launchFragmentInContainer<TestFragment>().apply {
            moveToState(Lifecycle.State.CREATED)
            onFragment { fragment ->
                val viewModelController1 = fragment.getViewModelController(
                    viewModelFactory,
                    TestViewModel1::class
                )
                assertNotNull(viewModelController1)
            }
        }
    }

    @Test
    fun testFragment_withArguments() {
        launchFragmentInContainer<TestFragment>(
            Bundle()
                .extraSerializedData("expected arg")
        ).apply {
            moveToState(Lifecycle.State.CREATED)
            onFragment { fragment ->
                val viewModelControllerArg1 = fragment.getViewModelController(
                    viewModelFactory,
                    TestViewModelArg1::class
                )
                assertNotNull(viewModelControllerArg1)
                assertEquals("expected arg", viewModelControllerArg1.arg)
            }
        }
    }

    @Test
    fun testActivity_noArguments() {
        launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val viewModelController1 = activity.getViewModelController(
                    viewModelFactory,
                    TestViewModel1::class
                )
                assertNotNull(viewModelController1)
            }
        }
    }

    @Test
    fun testActivity_withArguments() {
        launch<TestActivity>(
            Intent(getApplicationContext(), TestActivity::class.java)
                .extraSerializedData("expected arg")
        ).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val viewModelControllerArg1 = activity.getViewModelController(
                    viewModelFactory,
                    TestViewModelArg1::class
                )
                assertNotNull(viewModelControllerArg1)
                assertEquals("expected arg", viewModelControllerArg1.arg)
            }
        }
    }

    @Test
    fun testFragment_viewModelDoesntExist() {
        launchFragmentInContainer<TestFragment>().apply {
            moveToState(Lifecycle.State.CREATED)
            onFragment { fragment ->
                assertThrows(IllegalArgumentException::class.java) {
                    fragment.getViewModelController(
                        viewModelFactory,
                        TestViewModelNotExist::class
                    )
                }
            }
        }
    }

    @Test
    fun testActivity_viewModelDoesntExist() {
        launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                assertThrows(IllegalArgumentException::class.java) {
                    activity.getViewModelController(
                        viewModelFactory,
                        TestViewModelNotExist::class
                    )
                }
            }
        }
    }

    @Test
    fun testActivity_wrongArgs() {
        launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                assertThrows(IllegalArgumentException::class.java) {
                    activity.getViewModelController(
                        viewModelFactory,
                        TestViewModelWrongArgs::class,
                        "abc"
                    )
                }
            }
        }
    }
}

class TestFragment : Fragment()
class TestActivity : FragmentActivity()

private class TestViewModelFactory {
    fun test1() = TestViewModel1()
    fun test2() = TestViewModel2()
    fun testArgs1(arg: String) = TestViewModelArg1(arg)
    fun testArgs2(arg: String) = TestViewModelArg2(arg)
    fun testWrongArgs(arg1: String, arg2: String) = TestViewModelWrongArgs()
}

private class TestViewModel1 : ViewModel()
private class TestViewModel2 : ViewModel()
private class TestViewModelArg1(val arg: String) : ViewModel()
private class TestViewModelArg2(val arg: String) : ViewModel()
private class TestViewModelNotExist : ViewModel()
private class TestViewModelWrongArgs : ViewModel()
