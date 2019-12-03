package querysuggestion

import android.os.Build
import android.widget.SearchView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import applicationContext
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.querysuggestion.connectView
import ca.clinia.vision.helper.android.querysuggestion.QuerySuggestionBoxViewImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import shouldEqual

@SmallTest
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class TestQuerySuggestionBoxSearchView{

    private val text = "text"

    private fun view() = QuerySuggestionBoxViewImpl(SearchView(applicationContext))

    @Test
    fun connectShouldUpdateQuery() {
        val view = view()
        val viewModel =
            QuerySuggestionViewModel()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.searchView.query.toString() shouldEqual text
    }

}