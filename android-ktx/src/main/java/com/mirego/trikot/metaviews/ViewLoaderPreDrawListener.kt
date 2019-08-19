package com.mirego.trikot.metaviews

import android.view.View
import android.view.ViewTreeObserver

typealias LoadViewBlock = (ImageWidth, ImageHeight) -> Unit

class ViewLoaderPreDrawListener(private val view: View, private val loadViewBlock: LoadViewBlock) :
    ViewTreeObserver.OnPreDrawListener {

    override fun onPreDraw(): Boolean {
        val vto = view.viewTreeObserver
        if (!vto.isAlive) {
            return true
        }

        val width = view.width
        val height = view.height

        if (width < 0 || height < 0) {
            return true
        }

        vto.removeOnPreDrawListener(this)

        loadViewBlock(ImageWidth(width), ImageHeight(height))

        return true
    }
}
