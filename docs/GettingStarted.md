# Getting started


## Installation

To use Vision Android, you need credentials:
- App ID
- API Key

### Create a new Project and add Vision Android

In Android Studio, create a new project:
- On the Target screen, select **Phone and Tablet**
- On the Add an Activity screen, select Empty Activity

In your app's `build.gradle`, add the following dependency:

```groovy
implementation "ca.clinia:vision-android:1.X.X"
```

## Implementation

### Overview

- `MyActivity`: This activity controls the fragment currently displayed
- `MyViewModel`: A `ViewModel` from Android Architecture Components. The business logic lives here.
- `HealthFacilityFragment`: This fragment displays a list of search results in a `RecyclerView`, and a `SearchView`.

### Initializing a searcher

The central part of the widgets is the `Searcher`. The `Searcher` performs search requests and obtains search results. Almost all Vision widgets are connected with the `Searcher`.

Inside `MyViewModel.kt`

```kotlin
class MyViewModel : ViewModel() {
    
    // Initialize Clinia Search Client
    val client = ClientSearch(ApplicationID("appID"), APIKey("apiKey"))
    val index = client.initIndex(IndexName("health_facility"))
    val searcher = SearcherSingleIndex(index)

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
    }
}
```

A `ViewModel` is a good place to put your data sources. This way, the data persists during orientation changes and you can share it across multiple fragments.

### Displaying your results: `Hits`

We want to display search results in a `RecyclerView`. To simultaneously provide a good user experience and display thousands of health facilities, we will implement an infinite scrolling mechanism using the Paging Library from Android Architecture Component.

The first step to display your results is to create a `LiveData` object holding a PagedList of `HealthFacility`.

Create the `HealthFacility` data class which an id, name and geoPoint.

```kotlin
import ca.clinia.search.model.ID
import ca.clinia.search.model.search.Point

data class HealthFacility (
    id: ID,
    name: String,
    geoPoint: Point
)
```

Create the `health_facility_item.xml` file.

```xml
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="?attr/listPreferredItemHeightSmall"
    android:layout_marginBottom="0.5dp"
    app:cardCornerRadius="0dp"
    tools:layout_height="50dp">

    <TextView
        android:id="@+id/itemName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:ellipsize="end"
        android:maxLines="1"
        android:textAppearance="?attr/textAppearanceBody1"
        tools:text="@tools:sample/lorem/random" />

</com.google.android.material.card.MaterialCardView>
```

Create the HealthFacilityViewHolder to bind a `HealthFacility` item to a `RecyclerView.ViewHolder`.

```kotlin
class HealthFacilityViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(healthFacility: HealthFacility) {
        view.healthFacilityName.text = healthFacility.name
    }
}
```

Create a `HealthFacilityAdapter` by extending PagedListAdapter, that will bind health facilities to the ViewHolder.

```kotlin
class HealthFacilityAdapter : PagedListAdapter<HealthFacility, HealthFacilityViewHolder>(
    HealthFacilityAdapter
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthFacilityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_small, parent, false)

        return HealthFacilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: HealthFacilityViewHolder, position: Int) {
        val healthFacility = getItem(position)

        if (healthFacility != null) holder.bind(healthFacility)
    }

    companion object : DiffUtil.ItemCallback<HealthFacility>() {

        override fun areItemsTheSame(oldItem: HealthFacility, newItem: HealthFacility): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(oldItem: HealthFacility, newItem: HealthFacility): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
```

You can now use the `SearchSingleIndexDataSource.Factory` with your searcher to create a `LiveData<PagedList<HealthFacility>>`. Do this in your `ViewModel`.

```kotlin
class MyViewModel : ViewModel() {

    // Searcher initialization
    // ...

    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) { hit ->
        HealthFacility(
            ID(hit.json.getPrimitive("id").content),
            hit.json.getPrimitive("name").content,
            Point(
                hit.json.getObject("geoPoint").getPrimitive("lat").float,
                hit.json.getObject("geoPoint").getPrimitive("lng").float
            )
        )
    }
    val pagedListConfig = PagedList.Config.Builder().setPageSize(50).build()
    val healthFacilities: LiveData<PagedList<HealthFacility>> = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    val adapterHealthFacility = HealthFacilityAdapter()

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.disconnect()
    }
}
```

Now that your `ViewModel` has some data, let’s create a simple health_facility_fragment.xml with a Toolbar and a RecyclerView to display the health facilities:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:layout_height="?attr/actionBarSize"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/healthFacilityList"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="0dp"
        android:layout_height="0dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

In the HealthFacilityFragment, get a reference of MyViewModel with a ViewModelProviders.
Then, observe the LiveData to update your HealthFacilityAdapter on every new page of health facilities.
Finally, configure your RecyclerView by setting its adapter and LayoutManager.

```kotlin
class HealthFacilityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.health_facility_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(requireActivity())[MyViewModel::class.java]

        viewModel.healthfacilities.observe(this, Observer { hits -> viewModel.adapterHealthFacility.submitList(hits) })

        healthFacilityList.let {
            it.itemAnimator = null
            it.adapter = viewModel.adapterHealthFacility
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(viewModel.adapterHealthFacility)
        }
    }
}
```

You have now learned how to display search results in an infinite scrolling RecyclerView.

### Searching your data: `SearchBox`

To search your data, users will need an input field. Any change in this field should trigger a new request, and then update the search results displayed.

To achieve this, you will use a SearchBoxConnectorPagedList. This takes a searcher and one or multiple LiveData<PagedList<T>> as arguments.

First, pass your products LiveData defined above:

```kotlin
class MyViewModel : ViewModel() {

    // Searcher initialization
    // Hits initialization
    // ...

    val searchBox = SearchBoxConnectorPagedList(searcher, listOf(healthFacilities))
    val connection = ConnectionHandler()

    init {
        connection += searchBox
    }

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.disconnect()
    }
}
```

You can now add a SearchView in your Toolbar:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:layout_height="?attr/actionBarSize">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:clipChildren="false"
            android:clipToPadding="false">

            <androidx.appcompat.widget.SearchView
                android:id="@+id/searchView"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:focusable="false"
                app:iconifiedByDefault="false"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>

    </androidx.appcompat.widget.Toolbar>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/healthFacilityList"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="0dp"
        android:layout_height="0dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

Connect SearchBoxViewAppCompat to the SearchBoxConnectorPagedList stored in MyViewModel, using a new ConnectionHandler conforming to the HealthFacilityFragment lifecycle:

```kotlin
class HealthFacilityFragment : Fragment() {

    private val connection = ConnectionHandler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.health_facility_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(requireActivity())[MyViewModel::class.java]

        // Hits
        // ...

        val searchBoxView = SearchBoxViewAppCompat(searchView)

        connection += viewModel.searchBox.connectView(searchBoxView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.disconnect()
    }
}
```