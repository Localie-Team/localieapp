package com.example.localieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class ConsumerSettingActivity : AppCompatActivity() {
    private var back: ImageButton? = null
    private var logout: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer_settings)
        back = findViewById(R.id.settings_to_dashboard_button)
        logout = findViewById(R.id.log_out)

        back?.setOnClickListener(View.OnClickListener {
        val mainIntent = Intent(this@ConsumerSettingActivity, ConsumerDashboardActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        })

        logout?.setOnClickListener {
            startActivity(Intent(this@ConsumerSettingActivity, LoginActivity::class.java))
        }

    }

}