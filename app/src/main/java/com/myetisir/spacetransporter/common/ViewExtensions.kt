package com.myetisir.spacetransporter.common

import android.os.Build
import android.view.*
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar


fun View.addViewObserver(function: () -> Unit) {
    val view = this
    view.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            function.invoke()
        }
    })
}

fun TextView.setHtmlText(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = HtmlCompat.fromHtml(text ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = HtmlCompat.fromHtml(text ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

const val VIEW_SLIDE_ANIMATION_DURATION: Long = 500

fun View.slideUp(show: Boolean) {
    if (parent is ViewGroup) {
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = VIEW_SLIDE_ANIMATION_DURATION
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}

fun View.slideDown(show: Boolean) {
    if (parent is ViewGroup) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = VIEW_SLIDE_ANIMATION_DURATION
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}


fun ViewGroup.inflater(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}