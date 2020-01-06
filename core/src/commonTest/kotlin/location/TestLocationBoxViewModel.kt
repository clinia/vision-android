package location

import ca.clinia.vision.core.location.LocationBoxViewModel
import shouldEqual
import kotlin.test.Test

class TestLocationBoxViewModel {

    @Test
    fun setQueryShouldCallSubscription() {
        val viewModel = LocationBoxViewModel()
        var expected: String? = null
        val value = "hello"

        viewModel.query.subscribe { expected = it }
        viewModel.query.value = value
        expected shouldEqual value
    }

    @Test
    fun sendEventShouldCallSubscription() {
        val viewModel = LocationBoxViewModel()
        var expected: String? = null
        val value = "hello"

        viewModel.eventSubmit.subscribe { expected = it }
        viewModel.eventSubmit.send(value)
        expected shouldEqual value
    }
}