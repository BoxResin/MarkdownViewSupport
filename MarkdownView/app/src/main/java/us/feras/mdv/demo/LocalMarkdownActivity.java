package us.feras.mdv.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.feras.mdv.MarkdownView;

public class LocalMarkdownActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MarkdownView webView = new MarkdownView(this);
		setContentView(webView);
		webView.loadMarkdownFromAssets("hello.md");
	}
}
