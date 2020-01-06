# Searcher

The component handling search requests. Objects implementing the Searcher interface manage the search sessions.

We provide 4 searchers to build your Vision experience:
- `SearcherSingleIndex`: Searches a single index
- `SearcherMultipleIndex`: Searcher in multiple indices

## Examples

Instantiating the `SearcherSingleIndex` searcher:

```kotlin
val client = ClientSearch(ApplicationID("appId"), APIKey("apiKey"))
val index = client.initIndex(IndexName("health_facility))
val searcher = SearcherSingleIndex(index)
```

The supported index are currently `health_facility` and `professional`.

Instantiating the `SearcherMultipleIndex` searcher:

```kotlin
val client = ClientSearch(ApplicationID("appId"), APIKey("apiKey"))
val searcher = SearcherMultipleIndex(client = client, queries = listOf(
    IndexQuery(IndexName("health_facility")),
    IndexQuery(IndexName("professional"))
))
```

## Methods

`search` Triggers the search. Notifies all listeners of the results

```kotlin
searcher.search()
```

`cancel` Cancels the ongoing search requests

```kotlin
searcher.cancel()
```

`setQuery` Sets the query to the string provided.

```kotlin
searcher.setQuery("jean coutu")
```

`setLocation` Sets the location to the string provided.

```kotlin
searcher.setLocation("jean coutu")
```

`setAroundLatLng` Sets the aroundLatLng search param to the Point provided.

```kotlin
searcher.setAroundLatLng(Point(46.0f,-74.0f))
```

`setInsideBoudingBox` Sets the insideBoudingBox search param to the BoundingBox provided.

```kotlin
searcher.setInsideBoudingBox(BoundingBox(Point(46.0f,-74.0f), Point(46.3f,-74.8f)))
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

## Query

The `Query` class lets you provide raw search parameters to the Clinia API.

```kotlin
// SearcherSingleIndex
searcher.query.perPage = 20

// SearcherMultipleInex
searcher.queries[0].perPage = 20
searcher.queries.forEach { it.query.perPage = 20 } // for all indices
```