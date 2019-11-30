package searchbox

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.searchbox.SearchBoxView
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searchbox.connectView
import shouldEqual
import shouldNotBeNull
import kotlin.test.Test

class TestSearchBoxConnectView {

    private val text = "text"

    private class MockView : SearchBoxView {

        var string: String? = null
        var queryChanged: String? = null
        var querySubmitted: String? = null

        override fun setText(text: String?, submitQuery: Boolean) {
            string = text
        }

        override var onQueryChanged: Callback<String?>? = {
            queryChanged = it
        }
        override var onQuerySubmitted: Callback<String?>? = {
            querySubmitted = it
        }
    }

    @Test
    fun connectShouldSetItem() {
        val viewModel = SearchBoxViewModel()
        val view = MockView()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.string shouldEqual text
    }

    @Test
    fun onQueryChangedShouldCallSubscription() {
        val viewModel = SearchBoxViewModel()
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
    fun onQuerySubmittedShouldCallSubscription() {
        val viewModel = SearchBoxViewModel()
        val view = MockView()
        var expected: String? = null
        val connection = viewModel.connectView(view)

        viewModel.eventSubmit.subscribe { expected = it }
        connection.connect()
        view.onQuerySubmitted.shouldNotBeNull()
        view.onQuerySubmitted!!(text)
        viewModel.query.value shouldEqual text
        expected shouldEqual text
    }
}