# SearcherQuerySuggestions

Searches for query-suggestions, useful to provide a search experiance that gives more guidance to the user.

Instantiating the `SearcherQuerySuggestions` searcher:

```kotlin
val client = ClientSearch(ApplicationID("appId"), APIKey("apiKey"))
val searcher = SearcherQuerySuggestions(client = client)
```

## Methods

`searchForQuerySuggestions` Triggers the search. Notifies all listeners of the results

```kotlin
searcher.searchForQuerySuggestions()
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

