package querysuggestion

import blocking
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.helper.querysuggestion.connectSearcher
import searcher.MockSearcher
import searcher.MockSearcherQuerySuggestions
import shouldEqual
import kotlin.test.Test

class TestQuerySuggestionConnectSearcher {

    private val text = "jean coutu"
    private val debouncer = Debouncer(100)

    @Test
    fun searchQuerySuggestions() {
        val searcher = MockSearcher()
        val searcherQuerySuggestions = MockSearcherQuerySuggestions()
        val viewModel = QuerySuggestionViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherQuerySuggestions, debouncer)

        connection.connect()
        viewModel.query.value = text
        blocking { debouncer.job!!.join() }
        searcherQuerySuggestions.searchCount shouldEqual 1
        searcherQuerySuggestions.string shouldEqual text
    }

    @Test
    fun onEventSend() {
        val searcher = MockSearcher()
        val searcherQuerySuggestions = MockSearcherQuerySuggestions()
        val viewModel =
            QuerySuggestionViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherQuerySuggestions, debouncer)

        connection.connect()
        viewModel.eventSubmit.send(text)
        blocking { searcher.job!!.join() }
        searcher.searchCount shouldEqual 1
        searcher.string shouldEqual text
    }

    @Test
    fun debounce() {
        val searcher = MockSearcher()
        val searcherQuerySuggestions = MockSearcherQuerySuggestions()
        val viewModel =
            QuerySuggestionViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherQuerySuggestions, debouncer)

        connection.connect()
        viewModel.query.value = "j"
        viewModel.query.value = "je"
        viewModel.query.value = "jea"
        blocking { debouncer.job!!.join() }
        searcherQuerySuggestions.searchCount shouldEqual 1
        searcherQuerySuggestions.string shouldEqual "jea"
    }

}