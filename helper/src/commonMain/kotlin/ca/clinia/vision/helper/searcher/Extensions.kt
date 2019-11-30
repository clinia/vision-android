package ca.clinia.vision.helper.searcher

import ca.clinia.search.dsl.requestOptions
import ca.clinia.search.transport.RequestOptions
import ca.clinia.vision.core.Vision
import io.ktor.http.HttpHeaders

internal fun RequestOptions?.withUserAgent(): RequestOptions {
    return requestOptions(this) {
        header(HttpHeaders.UserAgent, "$osVersion; ${Vision.userAgent}")
    }
}