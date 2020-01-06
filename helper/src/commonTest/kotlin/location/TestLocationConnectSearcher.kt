package location

import blocking
import ca.clinia.vision.core.location.LocationBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.helper.location.connectSearcher
import searcher.MockSearcher
import searcher.MockSearcherPlaces
import shouldEqual
import kotlin.test.Test

class TestLocationConnectSearcher {

    private val text = "montreal"
    private val debouncer = Debouncer(100)

    @Test
    fun searchPlaces() {
        val searcher = MockSearcher()
        val searcherPlaces = MockSearcherPlaces()
        val viewModel =
            LocationBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherPlaces, debouncer)

        connection.connect()
        viewModel.query.value = text
        blocking { debouncer.job!!.join() }
        searcherPlaces.searchCount shouldEqual 1
        searcherPlaces.string shouldEqual text
    }

    @Test
    fun onEventSend() {
        val searcher = MockSearcher()
        val searcherPlaces = MockSearcherPlaces()
        val viewModel =
            LocationBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherPlaces, debouncer)

        connection.connect()
        viewModel.eventSubmit.send(text)
        blocking { searcher.job!!.join() }
        searcher.searchCount shouldEqual 1
        searcher.locationString shouldEqual text
    }

    @Test
    fun debounce() {
        val searcher = MockSearcher()
        val searcherPlaces = MockSearcherPlaces()
        val viewModel =
            LocationBoxViewModel()
        val connection = viewModel.connectSearcher(searcher, searcherPlaces, debouncer)

        connection.connect()
        viewModel.query.value = "m"
        viewModel.query.value = "mo"
        viewModel.query.value = "mon"
        blocking { debouncer.job!!.join() }
        searcherPlaces.searchCount shouldEqual 1
        searcherPlaces.string shouldEqual "mon"
    }

}