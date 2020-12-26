package us.feras.mdv.demo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import us.feras.mdv.MarkdownView

class MarkdownDataActivity : AppCompatActivity() {
    private val scope = MainScope()

    private lateinit var markdownEditText: EditText
    private lateinit var markdownView: MarkdownView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.markdown_view)

        this.markdownEditText = findViewById(R.id.markdownText)
        this.markdownView = findViewById(R.id.markdownView)

        val text = resources.getString(R.string.md_sample_data)
        this.markdownEditText.setText(text)
        updateMarkdownView()

        this.markdownEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                updateMarkdownView()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.scope.cancel()
    }

    private fun updateMarkdownView() {
        this.scope.launch {
            markdownView.commit {
                loadMarkdown(markdownEditText.text.toString())
            }
        }
    }
}
