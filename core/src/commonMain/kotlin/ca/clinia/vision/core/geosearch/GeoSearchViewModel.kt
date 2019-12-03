package ca.clinia.vision.core.geosearch

import ca.clinia.search.model.search.BoundingBox
import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class GeoSearchViewModel {

    public val insideBoundingBox = SubscriptionValue<BoundingBox?>(null)
    public val eventSubmit = SubscriptionEvent<BoundingBox?>()
}