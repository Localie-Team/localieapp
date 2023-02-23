package com.example.localieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class EditUserSettings : AppCompatActivity() {
    private var back: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_settings)
        back = findViewById(R.id.settings_to_dashboard_button)
        back?.setOnClickListener(View.OnClickListener {
        val mainIntent = Intent(this@EditUserSettings, DashboardActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        })

    }
}