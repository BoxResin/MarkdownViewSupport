package us.feras.mdv

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import androidx.annotation.StringRes
import kotlinx.coroutines.*
import org.markdownj.MarkdownProcessor
import java.util.*

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
            commit {
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
    }

    constructor(context: Context) : super(context)

    suspend fun commit(block: Config.() -> Unit): Unit = coroutineScope {
        val config = Config(scope = this)
        config.block()

        // Apply config
        if (config.markdownText != null) {
            this@MarkdownView.markdownText = config.markdownText!!.await()
        }

        // A CPU intensive task
        val mdTextInHtml: String = withContext(Dispatchers.Default) {
            markdownProcessor.markdown(this@MarkdownView.markdownText)
        }

        if (config.cssText != null) {
            this@MarkdownView.cssText = config.cssText!!.await()
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
$mdTextInHtml
    </body>
</html>"""

        withContext(Dispatchers.Main) {
            loadDataWithBaseURL("fake://", html, "text/html", "UTF-8", null)
        }
    }

    inner class Config internal constructor(private val scope: CoroutineScope) {
        internal var markdownText: Deferred<String>? = null
        internal var cssText: Deferred<String>? = null

        /**
         * Loads the given Markdown text to the view as rich formatted HTML.
         * @param markdownText input in Markdown format
         */
        fun loadMarkdown(markdownText: String) {
            this.markdownText?.cancel()
            this.markdownText = this.scope.async { markdownText }
        }

        /**
         * Loads the given Markdown text to the view as rich formatted HTML.
         * @param markdownTextId Markdown text id from String resource.
         */
        fun loadMarkdown(@StringRes markdownTextId: Int) {
            this.markdownText?.cancel()
            this.markdownText = this.scope.async { resources.getString(markdownTextId) }
        }

        /**
         * Loads the given Markdown text from Android assets.
         * @param markdownPath Markdown text file in assets directory. (ex. "hello.md")
         */
        fun loadMarkdownFromAssets(markdownPath: String) {
            this.markdownText?.cancel()
            this.markdownText = this.scope.async { readFileFromAsset(markdownPath) }
        }

        /**
         * Loads the given CSS text to the Markdown text.
         * @param cssText input in CSS format
         */
        fun loadCss(cssText: String) {
            this.cssText?.cancel()
            this.cssText = this.scope.async { cssText }
        }

        /**
         * Loads the CSS text from Android assets.
         * @param cssPath CSS file path in assets directory. (ex. "basic_theme.css", "sample/classic.css")
         */
        fun loadCssFromAssets(cssPath: String) {
            this.cssText?.cancel()
            this.cssText = this.scope.async { readFileFromAsset(cssPath) }
        }
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
