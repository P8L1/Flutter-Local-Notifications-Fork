package com.dexterous.flutterlocalnotifications

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.dexterous.flutterlocalnotifications.models.NotificationDetails
import com.dexterous.flutterlocalnotifications.models.TitleStyle
import com.dexterous.flutterlocalnotifications.models.styles.DefaultStyleInformation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class TitleStylerTest {
  @Test
  @Config(sdk = [24])
  fun appliesCustomTitleStyle() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val details = NotificationDetails()
    details.id = 1
    details.title = "Title"
    details.body = "Body"
    details.channelId = "id"
    details.channelName = "name"
    details.styleInformation = DefaultStyleInformation(false, false)
    val style = TitleStyle()
    style.color = Color.RED
    style.sizeSp = 16.0
    style.bold = true
    details.titleStyle = style

    val notification =
      FlutterLocalNotificationsPlugin.createNotification(context, details)
    assertNotNull(notification.contentView)
    assertEquals(R.layout.fln_notif_title_only, notification.contentView.layoutId)
    val view = notification.contentView.apply(context, null)
    val titleView = view.findViewById<TextView>(R.id.fln_title)
    assertEquals(Color.RED, titleView.currentTextColor)
    assertEquals(16f, titleView.textSize / context.resources.displayMetrics.scaledDensity, 0.1f)
    assertEquals(true, titleView.typeface.isBold)
  }

  @Test
  @Config(sdk = [23])
  fun ignoresOnPre24() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val details = NotificationDetails()
    details.id = 1
    details.title = "Title"
    details.body = "Body"
    details.channelId = "id"
    details.channelName = "name"
    details.styleInformation = DefaultStyleInformation(false, false)
    val style = TitleStyle()
    style.color = Color.RED
    details.titleStyle = style

    val notification =
      FlutterLocalNotificationsPlugin.createNotification(context, details)
    assertNull(notification.contentView)
  }
}
