# SearcherPlaces

Searches for places, useful for a geo search experiance

Instantiating the `SearcherPlaces` searcher:

```kotlin
val client = ClientPlaces(ApplicationID("appId"), APIKey("apiKey"))
val searcher = SearcherPlaces(client = client)
```

## Methods

`searchForPlaces` Triggers the search. Notifies all listeners of the results

```kotlin
searcher.searchForPlaces()
```

`cancel` Cancels the ongoing search requests

```kotlin
searcher.cancel()
```

`setQuery` Sets the query to the string provided.

```kotlin
searcher.setQuery("jean coutu")
```

## Events

`onLoadingChanged` Triggered when the status of the search request is changed

```kotlin
searcher.onLoadingChanged += { loading ->
    print(if (loading) "Currently loading search response" else "Done loading")
}
```

`onResponseChanged` Triggered when a new response has arrived.

```kotlin
searcher.onResponseChanged += { response ->
    val records = response.records.deserialize(HealthFacility.serializer())
    // Do something with hits...
}
```

`onErrorChanged` Triggered when an error was encountered during a search request.

```kotlin
searcher.onErrorChanged += {
   errorTextView.text = it.localizedMessage
}
```