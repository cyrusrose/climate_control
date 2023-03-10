package com.arduino.access.view.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.arduino.access.model.Values

@BindingAdapter("srcUrl", "circleCrop", "placeholder", requireAll = false)
fun ImageView.bindSrcUrl(
    uri: Uri,
    circleCrop: Boolean,
    holder: Drawable?
) {
    this.load(uri) {
        crossfade(true)
        if (circleCrop)
            transformations(CircleCropTransformation())
        holder?.let {
            placeholder(holder)
        }
    }
}

@BindingAdapter("pattern", "values", requireAll = true)
fun TextView.bindSrcUrl(
    pattern: String,
    values: Values?,
) {
    values?.let {
        this.text = pattern.format(values.value)
    }
}