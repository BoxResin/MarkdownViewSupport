package us.feras.mdv

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import androidx.annotation.StringRes
import kotlinx.coroutines.*
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
        typedArray.recycle()

        GlobalScope.launch {
            if (mdText.isNotEmpty()) {
                loadMarkdown(mdText)
            } else if (mdPath.isNotEmpty()) {
                loadMarkdownFromAssets(mdPath)
            }

            if (cssText.isNotEmpty()) {
                loadCss(cssText)
            } else if (cssPath.isNotEmpty()) {
                loadCssFromAssets(cssPath)
            }
        }
    }

    constructor(context: Context) : super(context)

    /**
     * Loads the given Markdown text to the view as rich formatted HTML.
     * @param markdownText input in Markdown format
     */
    suspend fun loadMarkdown(markdownText: String) {
        withContext(Dispatchers.Main) {
            this@MarkdownView.markdownText = markdownText
            syncMarkdown()
        }
    }

    /**
     * Loads the given Markdown text to the view as rich formatted HTML.
     * @param markdownTextId Markdown text id from String resource.
     */
    suspend fun loadMarkdown(@StringRes markdownTextId: Int) {
        this.loadMarkdown(this.resources.getString(markdownTextId))
    }

    /**
     * Loads the given Markdown text from Android assets.
     * @param markdownPath Markdown text file in assets directory. (ex. "hello.md")
     */
    suspend fun loadMarkdownFromAssets(markdownPath: String) {
        this.loadMarkdown(this.readFileFromAsset(markdownPath))
    }

    /**
     * Loads the given CSS text to the Markdown text.
     * @param cssText input in CSS format
     */
    suspend fun loadCss(cssText: String) {
        withContext(Dispatchers.Main) {
            this@MarkdownView.cssText = cssText
            syncMarkdown()
        }
    }

    /**
     * Loads the CSS text from Android assets.
     * @param cssPath CSS file path in assets directory. (ex. "basic_theme.css", "sample/classic.css")
     */
    suspend fun loadCssFromAssets(cssPath: String) {
        this.loadCss(this.readFileFromAsset(cssPath))
    }

    // A method to refresh markdown text on screen
    private suspend fun syncMarkdown(): Unit = withContext(Dispatchers.Main) {
        val md: String = this@MarkdownView.markdownText
        val cssText: String = this@MarkdownView.cssText
        val mdToHtml: String = withContext(Dispatchers.Default) {
            markdownProcessor.markdown(md)
        }
        val html = """
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
$cssText
        </style>
    </head>
    <body>
$mdToHtml
    </body>
</html>"""

        loadData(html, "text/html", "UTF-8")
    }

    private suspend fun readFileFromAsset(fileName: String): String = try {
        @Suppress("BlockingMethodInNonBlockingContext")
        withContext(Dispatchers.IO) {
            context.assets.open(fileName).bufferedReader().use { reader -> reader.readText() }
        }
    } catch (ex: Exception) {
        Log.d(TAG, "Error while reading file from assets", ex)
        ""
    }

    companion object {
        private const val TAG = "MarkdownView"
    }
}
