import ca.clinia.search.client.ClientSearch
import ca.clinia.search.configuration.ConfigurationSearch
import ca.clinia.search.model.APIKey
import ca.clinia.search.model.ApplicationID
import ca.clinia.search.model.response.ResponseSearch
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

val JsonNoDefaults = Json(JsonConfiguration.Stable.copy(encodeDefaults = false))

fun mockClient(
    response: HttpResponseData = respondSearch()
): ClientSearch {
    return ClientSearch(
        ConfigurationSearch(
            ApplicationID("A"),
            APIKey("B"),
            engine = MockEngine { response },
            logLevel = LogLevel.ALL
        )
    )
}

val responseSearch = ResponseSearch()

fun respondSearch(response: ResponseSearch = responseSearch) = respondJson(response, ResponseSearch.serializer())

fun <T> respondJson(response: T, serializer: KSerializer<T>): HttpResponseData {
    val responseString = JsonNoDefaults.stringify(serializer, response)

    return respond(
        headers = headersOf(
            "Content-Type",
            listOf(ContentType.Application.Json.toString())
        ),
        content = ByteReadChannel(responseString)
    )
}