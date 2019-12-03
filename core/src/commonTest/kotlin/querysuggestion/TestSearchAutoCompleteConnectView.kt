package querysuggestion

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.querysuggestion.QuerySuggestionBoxView
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.querysuggestion.connectView
import shouldEqual
import shouldNotBeNull
import kotlin.test.Test

class TestSearchAutoCompleteConnectView {

    private val text = "text"

    private class MockView :
        QuerySuggestionBoxView {

        var string: String? = null
        var queryChanged: String? = null
        var querySubmitted: String? = null

        override fun setText(text: String?) {
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
        val viewModel =
            QuerySuggestionViewModel()
        val view = MockView()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.string shouldEqual text
    }

    @Test
    fun onQueryChangedShouldCallSubscription() {
        val viewModel =
            QuerySuggestionViewModel()
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
        val viewModel =
            QuerySuggestionViewModel()
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