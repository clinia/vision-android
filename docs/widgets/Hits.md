# Hits

`Hits` is a view with helper components used to display a list of search results.

Read about [`HitsPaging`](InfiniteHits.md) if you want to use the infinite scroll pattern in your search.

To add `Hits` to your search experience, use these components:

- Searcher: The [`Searcher`](Searcher.md) that handles your searches.
- T: A `data class` representing a search result.
- HitsView<T>: The view that will render objects of type `T`.

## Example

You can use deserialize to convert raw hits into your own data class using the Serializable annotation.

```kotlin
class MyActivity : AppCompatActivity() {

    val client = ClientSearch(
        ApplicationID("YourApplicationID"),
        APIKey("YourAPIKey")
    )
    val index = client.initIndex(IndexName("YourIndexName"))
    val searcher = SearcherSingleIndex(index)
    val connection = ConnectionHandler()
    val adapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connection += searcher.connectHitsView(adapter) { response ->
            response.records.deserialize(Movie.serializer())
        }
        searcher.searchAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        connection.disconnect()
        searcher.cancel()
    }
}

@Serializable
data class HealthFacility(
    val name: String
)

class HealthFacilityViewHolder(val view: TextView): RecyclerView.ViewHolder(view) {

    fun bind(data: Movie) {
        view.text = data.name
    }
}

class HealthFacilityAdapter : RecyclerView.Adapter<HealthFacilityViewHolder>(), HitsView<HealthFacility> {

    private var healthfacilities: List<HealthFacility> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthFacilityViewHolder {
        return HealthFacilityViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: HealthFacilityViewHolder, position: Int) {
        val healthFacility = healthfacilities[position]

        holder.bind(healthFacility)
    }

    override fun setRecords(records: List<HealthFacility>) {
        healthfacilities = records
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return healthfacilities.size
    }
}
```

## Parameters

`transform` A function transforming the search response into a list of results of your class `T`.

```kotlin
val transform: (ResponseSearch) -> List<HealthFacility> = { response ->
    response.records.map { record -> HealthFacility(record.json.getPrimitive("name").content) }
}
searcher.connectHitsView(adapter, transform)
```