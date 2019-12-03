package ca.clinia.vision.core.searcher

import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

public interface SearcherPlaces<R> {

    public val coroutineScope: CoroutineScope

    public val isLoading: SubscriptionValue<Boolean>
    public val error: SubscriptionValue<Throwable?>
    public val response: SubscriptionValue<R?>

    public fun setQuery(text: String?)
    public fun searchPlacesAsync(): Job
    public suspend fun searchPlaces(): R

    public fun cancel()
}