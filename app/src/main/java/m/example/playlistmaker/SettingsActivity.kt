package m.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

class SettingsActivity : AppCompatActivity() {
    fun isDarkThemeCombined(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val appCompatMode = AppCompatDelegate.getDefaultNightMode()

        // Если принудительно установлен ночной режим через AppCompat
        if (appCompatMode == AppCompatDelegate.MODE_NIGHT_YES) {
            return true
        }
        // Если отключён
        if (appCompatMode == AppCompatDelegate.MODE_NIGHT_NO) {
            return false
        }
        // Иначе — смотрим системный режим
        return uiMode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<Button>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val switchDarkTheme = findViewById<SwitchCompat>(R.id.switch_dark)
        switchDarkTheme.isChecked = isDarkThemeCombined(this)

        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val btnShare = findViewById<ImageView>(R.id.share_btn)
        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.url_ya_pr_android))
            startActivity(intent)
        }

        val btnTechSprt = findViewById<ImageView>(R.id.tech_sprt_btn)
        btnTechSprt.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" // MIME‑тип для email
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.tech_mail)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.tech_mail_subj))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.tech_mail_text))
            }
            startActivity(intent)
        }

        val usrAgreeBtn = findViewById<ImageView>(R.id.usr_agree_btn)
        usrAgreeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, getString(R.string.url_usr_agree).toUri())
            startActivity(intent)
        }
    }


}