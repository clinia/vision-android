package ca.clinia.vision.core.hits

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.Searcher

public fun <R, T> Searcher<R>.connectHitsView(
    adapter: HitsView<T>,
    transform: (R) -> List<T>
) : Connection {
    return HitsConnectionView(this, adapter, transform)
}