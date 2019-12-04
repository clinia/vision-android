# Infinite Hits (Paging)

Infinite Hits is a view with helpers that displays a paginated list of search results.
It leverages Android Architecture Components’ Paging library and LiveData to provide lifecycle-aware, observable search results that can be loaded as the user scrolls.

To add Infinite Hits to your search experience, use these components:

- `Searcher`: The [`Searcher`](Searcher.md) that handles your searches.
- `SearcherSingleIndexDataSource`: The PageKeyedDataSource that will load hits incrementally.
- `LivePagedListBuilder`: The builder creating `LiveData`, based on the given `DataSource` and `Config`.
- `T`: A `data class` representing a search result.

## Example

```kotlin
class MyActivity : AppCompatActivity() {

    val client = ClientSearch(
        ApplicationID("YourApplicationID"),
        APIKey("YourAPIKey")
    )
    val index = client.initIndex(IndexName("YourIndexName"))
    val searcher = SearcherSingleIndex(index)
    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) { it.deserialize(HealthFacility.serializer()) }
    val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(10) // configure according to your needs
        .build()
    val healthFacilities = LivePagedListBuilder<Int, HealthFacility>(dataSourceFactory, pagedListConfig).build()
    val adapter = HealthFacilityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        healthFacilities.observe(this, Observer { records -> adapter.submitList(records) })

        searcher.searchAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        searcher.cancel()
    }
}

@Serializable
data class HealthFacility(
    val title: String
)

class HealthFacilityViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {

    fun bind(data: HealthFacility) {
        view.text = data.title
    }
}

class HealthFacilityAdapter : PagedListAdapter<HealthFacility, HealthFacilityViewHolder>(HealthFacilityAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthFacilityViewHolder {
        return HealthFacilityViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: HealthFacilityViewHolder, position: Int) {
        val healthFacility = getItem(position)

        if (healthFacility != null) holder.bind(healthFacility)
    }

    companion object : DiffUtil.ItemCallback<HealthFacility>() {

        override fun areItemsTheSame(
            oldItem: HealthFacility,
            newItem: HealthFacility
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HealthFacility,
            newItem: HealthFacility
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}
```

## Parameters

`observer` A function called on new records. You should forward the records to the UI to display them.

```kotlin
val observer = Observer<PagedList<HealthFacility>> { records -> adapter.submitList(records) }
healthFacilities.observe(this, observer)
```