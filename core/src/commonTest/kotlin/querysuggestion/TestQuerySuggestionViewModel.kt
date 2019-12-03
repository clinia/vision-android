package querysuggestion

import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import shouldEqual
import kotlin.test.Test

class TestQuerySuggestionViewModel {

    @Test
    fun setQueryShouldCallSubscription() {
        val viewModel = QuerySuggestionViewModel()
        var expected: String? = null
        val value = "hello"

        viewModel.query.subscribe { expected = it }
        viewModel.query.value = value
        expected shouldEqual value
    }

    @Test
    fun sendEventShouldCallSubscription() {
        val viewModel = QuerySuggestionViewModel()
        var expected: String? = null
        val value = "hello"

        viewModel.eventSubmit.subscribe { expected = it }
        viewModel.eventSubmit.send(value)
        expected shouldEqual value
    }
}

