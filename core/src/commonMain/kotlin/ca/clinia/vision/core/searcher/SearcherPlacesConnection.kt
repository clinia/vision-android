package ca.clinia.vision.core.searcher

import ca.clinia.vision.core.connection.Connection

public fun <T, R> SearcherPlaces<R>.connectView(view: (T) -> Unit, transform: (R?) -> T): Connection {
    return SearcherPlacesConnectionView(this, view, transform)
}