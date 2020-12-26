package us.feras.mdv.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun displayDataActivity(view: View) {
        this.startActivity(Intent(this, MarkdownDataActivity::class.java))
    }

    fun displayThemesActivity(view: View) {
        this.startActivity(Intent(this, MarkdownThemesActivity::class.java))
    }

    fun displayLocalMdFileActivity(view: View) {
        this.startActivity(Intent(this, LocalMarkdownActivity::class.java))
    }
}
