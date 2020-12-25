package us.feras.mdv

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import androidx.annotation.StringRes
import org.markdownj.MarkdownProcessor

/**
 * @author Feras Alnatsheh
 */
class MarkdownView : WebView {
    private var markdownText: String = ""
    private var cssText: String = ""
    private val markdownProcessor = MarkdownProcessor()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MarkdownView)
        val mdText: String = typedArray.getString(R.styleable.MarkdownView_markdown) ?: ""
        val mdPath: String = typedArray.getString(R.styleable.MarkdownView_markdownFromAssets) ?: ""
        val cssText: String = typedArray.getString(R.styleable.MarkdownView_css) ?: ""
        val cssPath: String = typedArray.getString(R.styleable.MarkdownView_cssFromAssets) ?: ""

        if (mdText.isNotEmpty()) {
            this.loadMarkdown(mdText)
        } else if (mdPath.isNotEmpty()) {
            this.loadMarkdownFromAssets(mdPath)
        }

        if (cssText.isNotEmpty()) {
            this.loadCss(cssText)
        } else if (cssPath.isNotEmpty()) {
            this.loadCssFromAssets(cssPath)
        }

        typedArray.recycle()
    }

    constructor(context: Context) : super(context)

    /**
     * Loads the given Markdown text to the view as rich formatted HTML.
     * @param markdownText input in Markdown format
     */
    fun loadMarkdown(markdownText: String) {
        this.markdownText = markdownText
        this.syncMarkdown()
    }

    /**
     * Loads the given Markdown text to the view as rich formatted HTML.
     * @param markdownTextId Markdown text id from String resource.
     */
    fun loadMarkdown(@StringRes markdownTextId: Int) {
        this.loadMarkdown(this.resources.getString(markdownTextId))
    }

    /**
     * Loads the given Markdown text from Android assets.
     * @param markdownPath Markdown text file in assets directory. (ex. "hello.md")
     */
    fun loadMarkdownFromAssets(markdownPath: String) {
        this.loadMarkdown(this.readFileFromAsset(markdownPath))
    }

    /**
     * Loads the given CSS text to the Markdown text.
     * @param cssText input in CSS format
     */
    fun loadCss(cssText: String) {
        this.cssText = cssText
        this.syncMarkdown()
    }

    /**
     * Loads the CSS text from Android assets.
     * @param cssPath CSS file path in assets directory. (ex. "basic_theme.css", "sample/classic.css")
     */
    fun loadCssFromAssets(cssPath: String) {
        this.loadCss(this.readFileFromAsset(cssPath))
    }

    // A method to refresh markdown text on screen
    private fun syncMarkdown() {
        val html = """
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
${this.cssText}
        </style>
    </head>
    <body>
${this.markdownProcessor.markdown(this.markdownText)}
    </body>
</html>"""

        this.loadData(html, "text/html", "UTF-8")
    }

    private fun readFileFromAsset(fileName: String): String = try {
        this.context.assets.open(fileName).bufferedReader().use { reader -> reader.readText() }
    } catch (ex: Exception) {
        Log.d(TAG, "Error while reading file from assets", ex)
        ""
    }

    companion object {
        private const val TAG = "MarkdownView"
    }
}
