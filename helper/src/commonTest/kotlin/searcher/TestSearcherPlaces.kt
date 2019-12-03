package searcher

import blocking
import io.ktor.client.engine.mock.respondBadRequest
import mockClientPlaces
import responseSearchPlaces
import shouldBeFalse
import shouldBeNull
import shouldBeTrue
import shouldEqual
import shouldNotBeNull
import kotlin.test.Test

class TestSearcherPlaces  {

    private val client = mockClientPlaces()
    private val clientError = mockClientPlaces(respondBadRequest())

    @Test
    fun searchShouldUpdateLoading() {
        val searcher = TestSearcherPlaces(client)
        var count = 0

        searcher.isLoading.subscribe { if (it) count++ }
        searcher.isLoading.value.shouldBeFalse()
        blocking { searcher.searchPlacesAsync().join() }
        count shouldEqual 1
    }

    @Test
    fun searchPlacesShouldUpdateResponse() {
        val searcher = TestSearcherPlaces(client)
        var responded = false

        searcher.response.subscribe { responded = true }
        searcher.response.value.shouldBeNull()
        blocking { searcher.searchPlacesAsync().join() }
        searcher.response.value shouldEqual responseSearchPlaces
        responded.shouldBeTrue()
        searcher.error.value.shouldBeNull()
    }

    @Test
    fun searchPlacesShouldUpdateError() {
        val searcher = TestSearcherPlaces(clientError)
        var error = false

        searcher.error.subscribe { error = true }
        searcher.error.value.shouldBeNull()
        blocking { searcher.searchPlacesAsync().join() }
        searcher.error.value.shouldNotBeNull()
        searcher.response.value.shouldBeNull()
        error.shouldBeTrue()
    }
}