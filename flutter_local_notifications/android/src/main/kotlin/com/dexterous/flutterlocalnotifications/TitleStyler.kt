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
  private const val MIN_SIZE_SP = 8f

  /**
   * Builds a RemoteViews that renders a styled title using the given TitleStyle.
   * API 24+ only. Returns null if title is empty or style is null.
   */
  fun build(
    context: Context,
    title: CharSequence?,
    style: TitleStyle?
  ): RemoteViews? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return null
    if (title.isNullOrEmpty() || style == null) return null

    val rv = RemoteViews(context.packageName, R.layout.fln_notif_title_only)
    val id = R.id.fln_title

    // 1) Build styled text (bold/italic via spans)
    val styled: CharSequence = if ((style.bold == true) || (style.italic == true)) {
      val s = SpannableString(title)
      when {
        style.bold == true && style.italic == true ->
          s.setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.bold == true ->
          s.setSpan(StyleSpan(Typeface.BOLD), 0, s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.italic == true ->
          s.setSpan(StyleSpan(Typeface.ITALIC), 0, s.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      }
      s
    } else {
      title
    }
 style.color?.let { rv.setTextColor(id, it) }
    // 2) Apply color and size deterministically (if provided)
    style.sizeSp?.let {
      if (it > 0.0) {
        val sp = it.toFloat().coerceIn(MIN_SIZE_SP, MAX_SIZE_SP)
        rv.setTextViewTextSize(id, TypedValue.COMPLEX_UNIT_SP, sp)
      } else {
        Log.d(TAG, "Ignoring non-positive sizeSp: $it")
      }
    }

    // 3) Set the final text last (ensures spans + color/size stick)
    rv.setTextViewText(id, styled)

    Log.d(
      TAG,
      "APPLY rv; color=${style.color?.toString(16)} size=${style.sizeSp} bold=${style.bold} italic=${style.italic}"
    )
    return rv
  }
}
