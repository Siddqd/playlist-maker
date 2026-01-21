package m.example.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged

class SearchActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private companion object {
        private val SRCH_TXT = "search_text"
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SRCH_TXT, searchEditText.text.toString())
    }

    override fun onRestoreInstanceState(outState: Bundle) {
        super.onRestoreInstanceState(outState)
        searchEditText.setText(outState.getString(SRCH_TXT, ""))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener{
            finish()
        }

        searchEditText = findViewById<EditText>(R.id.edit_srch)


        // Показать/скрыть крестик при изменении текста
        searchEditText.doOnTextChanged { text, _, _, _ ->
            val drawableSrch = ContextCompat.getDrawable(this@SearchActivity, R.drawable.search)
            val drawableClr = if (!text.isNullOrEmpty()) {
                // Показываем крестик когда есть текст
                ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_clear_12)
            } else {
                // Скрываем крестик когда текст пустой
                null
            }
            searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawableSrch, null, drawableClr, null
            )
        }

        // Обработка клика по крестику
        searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // END = 2 (для RTL проверять START = 0)
                val drawable = searchEditText.compoundDrawablesRelative[drawableEnd]

                if (drawable != null && event.rawX >= (searchEditText.right -
                            drawable.bounds.width() - searchEditText.paddingEnd)) {
                    searchEditText.text.clear()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}