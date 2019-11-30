package ca.clinia.vision.core.searchbox

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class SearchBoxViewModel {

    public val query = SubscriptionValue<String?>(null)
    public val eventSubmit = SubscriptionEvent<String?>()
}