package ca.clinia.vision.core.loading

import ca.clinia.vision.core.subscription.SubscriptionEvent
import ca.clinia.vision.core.subscription.SubscriptionValue


public open class LoadingViewModel(
    isLoading: Boolean = false
) {

    public val isLoading = SubscriptionValue(isLoading)
    public val eventReload = SubscriptionEvent<Unit>()
}