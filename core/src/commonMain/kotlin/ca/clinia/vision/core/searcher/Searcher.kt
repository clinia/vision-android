package ca.clinia.vision.core.searcher

import ca.clinia.search.model.search.BoundingBox
import ca.clinia.search.model.search.Point
import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

public interface Searcher<R> {

    public val coroutineScope: CoroutineScope

    public val isLoading: SubscriptionValue<Boolean>
    public val error: SubscriptionValue<Throwable?>
    public val response: SubscriptionValue<R?>

    public fun setQuery(text: String?)
    public fun setLocation(text: String?)
    public fun setInsideBoundingBox(insideBoundingBox: BoundingBox?)
    public fun setAroundLatLng(aroundLatLng: Point?)

    public fun searchAsync(): Job
    public suspend fun search(): R

    public fun cancel()
}