package com.greatdayhr.support.core.extension

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.greatdayhr.support.core.R


fun ImageView.loadImageRounded(url: Any, name: String, radius: Int) {
    val drawable = TextDrawable.builder()
        .buildRoundRect(
            initialName(name),
            ContextCompat.getColor(context, R.color.colorMango),
            radius
        )
    Glide.with(this.context).load(url).asBitmap().centerCrop().error(drawable)
        .into(object : BitmapImageViewTarget(this) {
            override fun setResource(resource: Bitmap) {
                val circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(context.resources, resource)
                circularBitmapDrawable.isCircular = true
                this@loadImageRounded.setImageDrawable(circularBitmapDrawable)
            }
        })
}

fun initialName(name: String): String {
    return name
        .split(' ')
        .mapNotNull { it.firstOrNull()?.toString() }
        .reduce { acc, s -> acc + s }
}