import com.mirego.trikot.streams.attachable.AbstractAttachable
import com.mirego.trikot.streams.cancelable.Cancelable
import com.mirego.trikot.streams.cancelable.CancelableManager
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class AbstractAttachableTests extends Specification {
    AttachableImpl attachable = new AttachableImpl(10)

    def '''
        given attachable
        when attached for the first time
        then doAttach is called
        '''() {
        when:
        attachable.attach()

        then:
        attachable.doAttachCount == 1
    }

    def '''
        given attachable
        when attached multiple times
        then doAttach is called only once
        '''() {
        when:
        attachable.attach()
        attachable.attach()

        then:
        attachable.doAttachCount == 1
    }

    def '''
        given attachable with multiple attachment
        when the everybody is detached
        then doDetach is called only once
        '''() {
        given:
        Cancelable cancelable1 = attachable.attach()
        Cancelable cancelable2 = attachable.attach()

        when:
        cancelable1.cancel()
        cancelable2.cancel()

        then:
        attachable.doAttachCount == 1
        attachable.doDetachCount == 1
    }

    def '''
        given attachable with two attachment
        when canceling both time the first
        then doDetach is never called
        '''() {
        given:
        Cancelable cancelable1 = attachable.attach()
        attachable.attach()

        when:
        cancelable1.cancel()
        cancelable1.cancel()

        then:
        attachable.doAttachCount == 1
        attachable.doDetachCount == 0
    }

    def '''
        given attachable with an attachment
        when detaching then attaching
        then do attach is recalled
        '''() {
        given:
        Cancelable cancelable1 = attachable.attach()

        when:
        cancelable1.cancel()
        attachable.attach()

        then:
        attachable.doAttachCount == 2
        attachable.doDetachCount == 1
    }

    def '''
        given attachable with a maximum of 1 simultaneous attachment
        when attaching a second one
        then exception is thrown
        '''() {
        given:
        attachable = new AttachableImpl(1)
        attachable.attach()

        when:
        attachable.attach()

        then:
        thrown(IllegalStateException)
    }

    class AttachableImpl extends AbstractAttachable {
        int doAttachCount = 0
        int doDetachCount = 0

        AttachableImpl(int maxAttach) {
            super(maxAttach)
        }
        
        @Override
        void doAttach(@NotNull CancelableManager cancelableManager) {
            doAttachCount++
        }

        @Override
        protected void doDetach() {
            super.doDetach()
            doDetachCount++
        }
    }
}
