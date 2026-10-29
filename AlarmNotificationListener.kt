package com.addian.alarm

import android.media.Ringtone
import android.media.RingtoneManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class AlarmNotificationListener : NotificationListenerService() {
    private var ringtone: Ringtone? = null
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        if (!prefs.getBoolean("master", true)) return
        val pkg = sbn.packageName
        if (pkg != "com.whatsapp" && pkg != "com.fiverr.fiverr") return
        if (!prefs.getBoolean(pkg, true) || sbn.isOngoing) return
        try {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            ringtone?.stop()
            ringtone = RingtoneManager.getRingtone(applicationContext, uri)
            ringtone?.play()
        } catch (_: Exception) { }
    }
}
