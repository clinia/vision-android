package searcher

import blocking
import io.ktor.client.engine.mock.respondBadRequest
import mockClientQuerySuggestions
import responseQuerySuggestions
import shouldBeFalse
import shouldBeNull
import shouldBeTrue
import shouldEqual
import shouldNotBeNull
import kotlin.test.Test

class TestSearcherQuerySuggestions  {

    private val client = mockClientQuerySuggestions()
    private val clientError = mockClientQuerySuggestions(respondBadRequest())

    @Test
    fun searchShouldUpdateLoading() {
        val searcher = TestSearcherQuerySuggestions(client)
        var count = 0

        searcher.isLoading.subscribe { if (it) count++ }
        searcher.isLoading.value.shouldBeFalse()
        blocking { searcher.searchQuerySuggestionsAsync().join() }
        count shouldEqual 1
    }

    @Test
    fun searchPlacesShouldUpdateResponse() {
        val searcher = TestSearcherQuerySuggestions(client)
        var responded = false

        searcher.response.subscribe { responded = true }
        searcher.response.value.shouldBeNull()
        blocking { searcher.searchQuerySuggestionsAsync().join() }
        searcher.response.value shouldEqual responseQuerySuggestions
        responded.shouldBeTrue()
        searcher.error.value.shouldBeNull()
    }

    @Test
    fun searchPlacesShouldUpdateError() {
        val searcher = TestSearcherQuerySuggestions(clientError)
        var error = false

        searcher.error.subscribe { error = true }
        searcher.error.value.shouldBeNull()
        blocking { searcher.searchQuerySuggestionsAsync().join() }
        searcher.error.value.shouldNotBeNull()
        searcher.response.value.shouldBeNull()
        error.shouldBeTrue()
    }
}