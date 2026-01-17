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

class SearchActivity : AppCompatActivity() {
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

        val searchEditText = findViewById<EditText>(R.id.edit_srch)

        // Показать/скрыть крестик при изменении текста
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateClearButton()
            }

            override fun afterTextChanged(s: Editable?) {}

            private fun updateClearButton() {
                val drawable_srch = ContextCompat.getDrawable(this@SearchActivity, R.drawable.search)
                val drawable_clr = if (searchEditText.text.isNotEmpty()) {
                    // Показываем крестик когда есть текст
                    ContextCompat.getDrawable(this@SearchActivity, R.drawable.ic_clear_12)
                } else {
                    // Скрываем крестик когда текст пустой
                    null
                }
                searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    drawable_srch, null, drawable_clr, null
                )
            }
        })

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