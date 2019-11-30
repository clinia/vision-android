package searchbox

import blocking
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.helper.searchbox.SearchMode
import ca.clinia.vision.helper.searchbox.connectSearcher
import searcher.MockSearcher
import shouldEqual
import kotlin.test.Test


class TestSearchBoxConnectSearcher {

    private val text = "text"
    private val debouncer = Debouncer(100)

    @Test
    fun searchAsYouType() {
        val searcher = MockSearcher()
        val viewModel = SearchBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, SearchMode.AsYouType, debouncer)

        connection.connect()
        viewModel.query.value = text
        blocking { debouncer.job!!.join() }
        searcher.searchCount shouldEqual 1
        searcher.string shouldEqual text
    }

    @Test
    fun onEventSend() {
        val searcher = MockSearcher()
        val viewModel = SearchBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, SearchMode.OnSubmit, debouncer)

        connection.connect()
        viewModel.eventSubmit.send(text)
        blocking { searcher.job!!.join() }
        searcher.searchCount shouldEqual 1
        searcher.string shouldEqual text
    }

    @Test
    fun debounce() {
        val searcher = MockSearcher()
        val viewModel = SearchBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, SearchMode.AsYouType, debouncer)

        connection.connect()
        viewModel.query.value = "a"
        viewModel.query.value = "ab"
        viewModel.query.value = "abc"
        blocking { debouncer.job!!.join() }
        searcher.searchCount shouldEqual 1
        searcher.string shouldEqual "abc"
    }
}