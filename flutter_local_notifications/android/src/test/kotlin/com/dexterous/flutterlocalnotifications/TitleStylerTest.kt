package com.dexterous.flutterlocalnotifications

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.dexterous.flutterlocalnotifications.models.TitleStyle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class TitleStylerTest {
  private lateinit var context: android.content.Context

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.M])
  fun build_returnsNullOnPreN_andWhenStyleMissing() {
    val style = TitleStyle().apply { bold = true }
    val rvPreN = TitleStyler.build(context, "t", null, style)
    val rvNoStyle = TitleStyler.build(context, "t", null, null)
    assertNull(rvPreN)
    assertNull(rvNoStyle)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_returnsNullWhenTitleMissing_orEmpty() {
    val style = TitleStyle()
    val rvNull = TitleStyler.build(context, null, null, style)
    val rvEmpty = TitleStyler.build(context, "", null, style)
    assertNull(rvNull)
    assertNull(rvEmpty)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_appliesBoldItalicColorAndSize() {
    val style = TitleStyle().apply {
      bold = true
      italic = true
      color = Color.GREEN
      sizeSp = 40.0
    }
    val rv = TitleStyler.build(context, "Title", "Body", style)
    val root = rv!!.apply(context, null)
    val title = root.findViewById<TextView>(R.id.fln_title)
    val expectedSize = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_SP,
      26f,
      context.resources.displayMetrics
    )
    assertEquals(Typeface.BOLD_ITALIC, title.typeface.style)
    assertEquals(Color.GREEN, title.currentTextColor)
    assertEquals(expectedSize, title.textSize, 0.1f)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_handlesBodyVisibility() {
    val style = TitleStyle()
    val withBody = TitleStyler.build(context, "t", "body", style)
    val noBody = TitleStyler.build(context, "t", null, style)
    val bodyView1 = withBody!!.apply(context, null).findViewById<TextView>(R.id.fln_body)
    val bodyView2 = noBody!!.apply(context, null).findViewById<TextView>(R.id.fln_body)
    assertEquals(View.VISIBLE, bodyView1.visibility)
    assertEquals(View.GONE, bodyView2.visibility)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_clampsSizeSpWithinBounds() {
    val big = TitleStyle().apply { sizeSp = 1000.0 }
    val small = TitleStyle().apply { sizeSp = 1.0 }
    val bigView = TitleStyler.build(context, "t", null, big)!!.apply(context, null)
    val smallView = TitleStyler.build(context, "t", null, small)!!.apply(context, null)
    val titleBig = bigView.findViewById<TextView>(R.id.fln_title)
    val titleSmall = smallView.findViewById<TextView>(R.id.fln_title)
    val maxPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 26f, context.resources.displayMetrics)
    val minPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8f, context.resources.displayMetrics)
    assertEquals(maxPx, titleBig.textSize, 0.1f)
    assertEquals(minPx, titleSmall.textSize, 0.1f)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_appliesBoldOnly_andItalicOnly() {
    val boldStyle = TitleStyle().apply { bold = true }
    val italicStyle = TitleStyle().apply { italic = true }
    val boldView = TitleStyler.build(context, "t", null, boldStyle)!!.apply(context, null)
    val italicView = TitleStyler.build(context, "t", null, italicStyle)!!.apply(context, null)
    val titleBold = boldView.findViewById<TextView>(R.id.fln_title)
    val titleItalic = italicView.findViewById<TextView>(R.id.fln_title)
    assertEquals(Typeface.BOLD, titleBold.typeface.style)
    assertEquals(Typeface.ITALIC, titleItalic.typeface.style)
  }

  @Test
  @Config(sdk = [Build.VERSION_CODES.N])
  fun build_ignoresNonPositiveSizeSp() {
    val zero = TitleStyle().apply { sizeSp = 0.0 }
    val negative = TitleStyle().apply { sizeSp = -5.0 }
    val zeroTitle = TitleStyler.build(context, "t", null, zero)!!.apply(context, null)
      .findViewById<TextView>(R.id.fln_title)
    val negativeTitle = TitleStyler.build(context, "t", null, negative)!!.apply(context, null)
      .findViewById<TextView>(R.id.fln_title)
    assertEquals(zeroTitle.textSize, negativeTitle.textSize, 0.1f)
    assertTrue(zeroTitle.textSize > 0f)
  }
}