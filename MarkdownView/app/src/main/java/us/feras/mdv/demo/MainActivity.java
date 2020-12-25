package us.feras.mdv.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayDataActivity(View view) {
        this.startActivity(new Intent(this, MarkdownDataActivity.class));
    }

    public void displayThemesActivity(View view) {
        this.startActivity(new Intent(this, MarkdownThemesActivity.class));
    }

    public void displayLocalMdFileActivity(View view) {
        this.startActivity(new Intent(this, LocalMarkdownActivity.class));
    }

}
