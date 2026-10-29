package com.addian.alarm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*

class MainActivity : Activity() {
    private val prefs by lazy { getSharedPreferences("settings", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 36, 32, 24) }
        layout.addView(TextView(this).apply { text = "Addian Alarm"; textSize = 28f })
        layout.addView(TextView(this).apply { text = "Manage custom notification alarms for WhatsApp and Fiverr."; textSize = 16f; setPadding(0, 16, 0, 16) })
        layout.addView(Switch(this).apply { text = "Alarm enabled"; isChecked = prefs.getBoolean("master", true); setOnCheckedChangeListener { _, v -> prefs.edit().putBoolean("master", v).apply() } })
        listOf("WhatsApp" to "com.whatsapp", "Fiverr" to "com.fiverr.fiverr").forEach { (label, pkg) ->
            layout.addView(Switch(this).apply { text = "$label alarm"; isChecked = prefs.getBoolean(pkg, true); setOnCheckedChangeListener { _, v -> prefs.edit().putBoolean(pkg, v).apply() } })
        }
        layout.addView(Button(this).apply { text = "Enable Notification Access"; setOnClickListener { startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)) } })
        layout.addView(TextView(this).apply { text = "Uses your phone's default alarm tone. Enable Addian Alarm under Android Notification Access settings."; textSize = 14f; setPadding(0, 18, 0, 0) })
        setContentView(ScrollView(this).apply { addView(layout) })
    }
}
