# SearchBox

The `SearchBox` is used to perform a text-based query.

To add a `SearchBox` to your search experience, use theres components:
- `Searcher`: The [`Searcher`](Searcher.md) that handles your searches.
- `SearchBoxViewModel`: The business logic that handles new search inputs
- `SearchBoxView`: The view that handles the input.

## Example

```kotlin
class Activity : AppCompatActivity() {

    val client = ClientSearch(ApplicationID("appId"), APIKey("apiKey"))
    val index = client.initIndex(IndexName("health_facility))
    val search = SearcherSingleIndex(index)
    val viewModel = SearchBoxViewModel()
    val connection = ConnectionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val someView = SearchView(this)
        val view: SearchBoxView = SearchBoxViewAppCompat(someView)

        connection += viewModel.connectSearcher(searcher)
        connection += viewModel.connectView(view)

        searcher.searchAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        connection.disconnect()
        searcher.cancel()
    }
}
```

## Parameters

`searcher` The [`Searcher`](Searcher.md) that handles your searches.

```kotlin
viewModel.connectSearcher(searcher)
```

`searchBoxView` The view than handles the input

```kotlin
viewModel.connectView(searchBoxView)
```

`searchMode`
- `SearchMode.AsYouType` will trigger a search on each keystroke.
- `SearchMode.OnSubmit` will trigger a search on submitting the query.

```kotlin
searchBoxViewModel.connectSearcher(searcher, searchMode = SearchMode.OnSubmit)
```