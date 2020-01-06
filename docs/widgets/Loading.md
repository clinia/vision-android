# Loading

Components that show a loading indicator during pending requests.

To add a loading indicator to your search experience, use these components
- `Searcher`: The [`Searcher`](Searcher.md) that handles your searches.
- `LoadingViewModel`: The logic applied to the loading indicator.
- `LoadingView`: The concrete view displayed during loading.

## Examples

```kotlin
class MyActivity: AppCompatActivity() {

    val client = ClientSearch(
        ApplicationID("YourApplicationID"),
        APIKey("YourAPIKey")
    )
    val index = client.initIndex(IndexName("YourIndexName"))
    val searcher = SearcherSingleIndex(index)
    val viewModel = LoadingViewModel()
    val connection = ConnectionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val swipeRefreshLayout = SwipeRefreshLayout(this)
        val view: LoadingView = LoadingViewSwipeRefreshLayout(swipeRefreshLayout)

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

`loadingView` The concrete view displayed during loading.

```kotlin
viewModel.connectView(loadingView)
```

`isLoading` When true, the interface starts in a loading state.

```kotlin
LoadingViewModel(isLoading = true)
```