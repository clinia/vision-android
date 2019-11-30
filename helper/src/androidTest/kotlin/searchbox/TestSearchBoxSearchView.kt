package searchbox

import android.os.Build
import android.widget.SearchView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import applicationContext
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searchbox.connectView
import ca.clinia.vision.helper.android.searchbox.SearchBoxViewImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import shouldEqual

@SmallTest
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class TestSearchBoxSearchView {

    private val text = "text"

    private fun view() = SearchBoxViewImpl(SearchView(applicationContext))

    @Test
    fun connectShouldUpdateQuery() {
        val view = view()
        val viewModel = SearchBoxViewModel()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.searchView.query.toString() shouldEqual text
    }

}