package ca.clinia.vision.core.location

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.SearcherPlaces

public fun <R, T> SearcherPlaces<R>.connectPlacesView(
    adapater: PlacesView<T>,
    transform: (R) -> List<T>
) : Connection {
    return PlacesConnectionView(
        this,
        adapater,
        transform
    )
}