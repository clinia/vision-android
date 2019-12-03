package ca.clinia.vision.core.locationautocomplete

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class LocationAutoCompleteViewModel {

    public val location = SubscriptionValue<String?>(null)
    public val query = SubscriptionValue<String?>(null)
    public val eventSubmit = SubscriptionEvent<String?>()
}