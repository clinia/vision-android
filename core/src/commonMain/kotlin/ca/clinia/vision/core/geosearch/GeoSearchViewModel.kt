package ca.clinia.vision.core.geosearch

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class GeoSearchViewModel {

    public val insideBoundingBox = SubscriptionValue<String?>(null)
    public val eventSubmit = SubscriptionEvent<String?>()
}