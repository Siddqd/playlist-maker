package m.example.playlistmaker

import android.content.Intent
import android.net.Uri
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

class SettingsActivity : AppCompatActivity() {
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
            // Установка типа намерения
            intent.type = "text/plain"
            // Передача сообщения
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.url_ya_pr_android))
            if (intent.resolveActivity(packageManager) == null) {
                Toast.makeText(this, "Please install MSNGRS first.", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(intent)
            }
        }

        val btnTechSprt = findViewById<ImageView>(R.id.tech_sprt_btn)
        btnTechSprt.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" // MIME‑тип для email
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.tech_mail)))
                putExtra(Intent.EXTRA_SUBJECT, "Сообщение разработчикам и разработчицам приложения Playlist Maker")
                putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!")
            }

            // Проверяем, есть ли хотя бы один почтовый клиент
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Не найдено почтового клиента. Установите почтовый клиент и попробуйте снова.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val usrAgreeBtn = findViewById<ImageView>(R.id.usr_agree_btn)
        usrAgreeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_usr_agree)))
            startActivity(intent)
        }
    }


}