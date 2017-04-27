package us.feras.mdv;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import org.markdownj.MarkdownProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Feras Alnatsheh
 */
public class MarkdownView extends WebView {

	private static final String TAG = "MarkdownView";

	private String markdownText;
	private String cssText;
	private final MarkdownProcessor markdownProcessor;

	public MarkdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		markdownProcessor = new MarkdownProcessor();

		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarkDownView);

		String markdownText = typedArray.getString(R.styleable.MarkDownView_markdown);
		String markdownPath = typedArray.getString(R.styleable.MarkDownView_markdownFromAssets);
		String cssText = typedArray.getString(R.styleable.MarkDownView_css);
		String cssPath = typedArray.getString(R.styleable.MarkDownView_cssFromAssets);

		if (markdownText != null)
			loadMarkdown(markdownText);
		else if (markdownPath != null)
			loadMarkdownFromAssets(markdownPath);
		if (cssText != null)
			loadCss(cssText);
		else if (cssPath != null)
			loadCssFromAssets(cssPath);

		typedArray.recycle();
	}

	public MarkdownView(Context context) {
		super(context);
		markdownProcessor = new MarkdownProcessor();
	}

	/**
	 * Loads the given Markdown text to the view as rich formatted HTML.
	 * @param markdownText input in Markdown format
	 */
	public void loadMarkdown(String markdownText)
	{
		this.markdownText = markdownText;
		syncMarkdown();
	}

	/**
	 * Loads the given Markdown text to the view as rich formatted HTML.
	 * @param markdownTextId Markdown text id from String resource.
	 */
	public void loadMarkdown(@StringRes int markdownTextId)
	{
		loadMarkdown(getResources().getString(markdownTextId));
	}

	/**
	 * Loads the given Markdown text from Android assets.
	 * @param markdownPath Markdown text file in assets directory. (ex. "hello.md")
	 */
	public void loadMarkdownFromAssets(String markdownPath)
	{
		loadMarkdown(readFileFromAsset(markdownPath));
	}

	/**
	 * Loads the given CSS text to the Markdown text.
	 * @param cssText input in CSS format
	 */
	public void loadCss(String cssText)
	{
		this.cssText = cssText;
		syncMarkdown();
	}

	/**
	 * Loads the CSS text from Android assets.
	 * @param cssPath CSS file path in assets directory. (ex. "basic_theme.css", "sample/classic.css")
	 */
	public void loadCssFromAssets(String cssPath)
	{
		loadCss(readFileFromAsset(cssPath));
	}

	// A method to refresh markdown text on screen
	private void syncMarkdown()
	{
		String html = markdownProcessor.markdown(markdownText);
		if (cssText != null) {
			html = 	"<style type=\"text/css\">\n" + cssText + "</style>\n" + html;
		}
		loadDataWithBaseURL("fake://", html, "text/html", "UTF-8", null);
	}

	private String readFileFromAsset(String fileName){
		try {
			InputStream input = getContext().getAssets().open(fileName);
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
				StringBuilder content = new StringBuilder(input.available());
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					content.append(line);
					content.append(System.getProperty("line.separator"));
				}
				return content.toString();
			} finally { input.close(); }
		} catch (Exception ex){
			Log.d(TAG, "Error while reading file from assets", ex);
			return null;
		}
	}
}