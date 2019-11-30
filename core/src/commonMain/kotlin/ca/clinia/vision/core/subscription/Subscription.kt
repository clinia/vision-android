package ca.clinia.vision.core.subscription


public open class Subscription<T> {

    internal val subscriptions: MutableSet<(T) -> Unit> = mutableSetOf()

    public fun subscribe(subscription: (T) -> Unit) {
        subscriptions += subscription
    }

    public fun unsubscribe(subscription: (T) -> Unit) {
        subscriptions -= subscription
    }

    public fun unsubscribeAll() {
        subscriptions.clear()
    }
}