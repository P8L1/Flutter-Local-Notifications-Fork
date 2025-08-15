package com.dexterous.flutterlocalnotifications

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.widget.RemoteViews
import com.dexterous.flutterlocalnotifications.models.TitleStyle

internal object TitleStyler {
  private const val TAG = "TitleStyler"
  private const val MAX_SIZE_SP = 26f

  fun build(
    context: Context,
    title: CharSequence?,
    style: TitleStyle?
  ): RemoteViews? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      return null
    }
    if (title.isNullOrEmpty() || style == null) {
      return null
    }
    val remoteViews = RemoteViews(context.packageName, R.layout.fln_notif_title_only)
    val viewId = R.id.fln_title
    remoteViews.setTextViewText(viewId, title)

    style.color?.let { remoteViews.setTextColor(viewId, it) }

    style.sizeSp?.let {
      if (it > 0) {
        val size = it.toFloat().coerceAtMost(MAX_SIZE_SP)
        remoteViews.setTextViewTextSize(viewId, TypedValue.COMPLEX_UNIT_SP, size)
      } else {
        Log.d(TAG, "Ignoring non-positive sizeSp: $it")
      }
    }

    val bold = style.bold == true
    val italic = style.italic == true
    if (bold || italic) {
      val flags = (if (bold) Typeface.BOLD else 0) or (if (italic) Typeface.ITALIC else 0)
      val spannable = SpannableString(title)
      spannable.setSpan(StyleSpan(flags), 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      remoteViews.setTextViewText(viewId, spannable)
    }

    return remoteViews
  }
}
