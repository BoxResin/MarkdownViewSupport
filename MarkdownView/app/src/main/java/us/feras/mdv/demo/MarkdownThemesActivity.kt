package us.feras.mdv.demo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import us.feras.mdv.MarkdownView

class MarkdownThemesActivity : AppCompatActivity(), OnItemSelectedListener {
    private val scope = MainScope()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.markdown_themes)

        val themesSpinner = findViewById<Spinner>(R.id.themes_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.md_themes, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themesSpinner.adapter = adapter
        themesSpinner.setSelection(0)
        themesSpinner.onItemSelectedListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        this.scope.cancel()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        this.scope.launch {
            val mdv: MarkdownView = findViewById(R.id.markdownView)
            mdv.loadMarkdownFromAssets("hello.md")
            mdv.loadCssFromAssets("markdown_css_themes/${parent.getItemAtPosition(pos)}.css")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // no-op
    }
}
