package location

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.location.LocationBoxView
import ca.clinia.vision.core.location.LocationBoxViewModel
import ca.clinia.vision.core.location.connectView
import shouldEqual
import shouldNotBeNull
import kotlin.test.Test

class TestLocationBoxConnectView {

    private val text = "text"

    private class MockView : LocationBoxView {

        var string: String? = null
        var queryChanged: String? = null
        var querySubmitted: String? = null

        override fun setText(text: String?, submitLocation: Boolean) {
            string = text
        }

        override var onQueryChanged: Callback<String?>? = {
            queryChanged = it
        }
        override var onLocationSubmitted: Callback<String?>? = {
            querySubmitted = it
        }
    }

    @Test
    fun connectShouldSetItem() {
        val viewModel = LocationBoxViewModel()
        val view = MockView()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.string shouldEqual text
    }

    @Test
    fun onQueryChangedShouldCallSubscription() {
        val viewModel = LocationBoxViewModel()
        val view = MockView()
        var expected: String? = null
        val connection = viewModel.connectView(view)

        viewModel.query.subscribe { expected = it }
        connection.connect()
        view.onQueryChanged.shouldNotBeNull()
        view.onQueryChanged!!(text)
        viewModel.query.value shouldEqual text
        expected shouldEqual text
    }

    @Test
    fun onLocationSubmittedShouldCallSubscription() {
        val viewModel = LocationBoxViewModel()
        val view = MockView()
        var expected: String? = null
        val connection = viewModel.connectView(view)

        viewModel.eventSubmit.subscribe { expected = it }
        connection.connect()
        view.onLocationSubmitted.shouldNotBeNull()
        view.onLocationSubmitted!!(text)
        viewModel.query.value shouldEqual text
        expected shouldEqual text
    }
}