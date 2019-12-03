package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue

public open class QuerySuggestionViewModel {

    public val query = SubscriptionValue<String?>(null)
    public val eventSubmit = SubscriptionEvent<String?>()
}