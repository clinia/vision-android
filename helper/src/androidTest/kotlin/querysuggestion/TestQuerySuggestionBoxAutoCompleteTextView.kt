package querysuggestion

import android.os.Build
import android.widget.AutoCompleteTextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import applicationContext
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.querysuggestion.connectView
import ca.clinia.vision.helper.android.querysuggestion.QuerySuggestionBoxAutoCompleteTextView
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import shouldEqual

@SmallTest
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class TestQuerySuggestionBoxAutoCompleteTextView {

    private val text = "text"

    private fun view() = QuerySuggestionBoxAutoCompleteTextView(AutoCompleteTextView(applicationContext))

    @Test
    fun connectShouldUpdateText() {
        val view = view()
        val viewModel = QuerySuggestionViewModel()
        val connection = viewModel.connectView(view)

        viewModel.query.value = text
        connection.connect()
        view.autoCompleteTextView.text.toString() shouldEqual text
    }

    @Test
    fun onTextChangedShouldUpdateItem() {
        val view = view()
        val viewModel = QuerySuggestionViewModel()
        val connection = viewModel.connectView(view)

        connection.connect()
        view.autoCompleteTextView.setText(text)
        viewModel.query.value shouldEqual text
    }
}