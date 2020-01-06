package ca.clinia.vision.core.location

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class LocationBoxViewModel {

    public val location = SubscriptionValue<String?>(null)
    public val query = SubscriptionValue<String?>(null)
    public val eventSubmit = SubscriptionEvent<String?>()
}