package us.feras.mdv.demo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import us.feras.mdv.MarkdownView;

public class MarkdownDataActivity extends AppCompatActivity {

	private EditText markdownEditText;
	private MarkdownView markdownView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.markdown_view);
		markdownEditText = (EditText) findViewById(R.id.markdownText);
		markdownView = (MarkdownView) findViewById(R.id.markdownView);
		String text = getResources().getString(R.string.md_sample_data);
		markdownEditText.setText(text);
		updateMarkdownView();

		markdownEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateMarkdownView();
			}
		});


	}

	private void updateMarkdownView() {
		markdownView.loadMarkdown(markdownEditText.getText().toString());
	}
}
