package com.dlong.dialog.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView


/**
 * 高度自动的GridView
 *
 * @author D10NG
 * @date on 2020/2/25 11:21 AM
 */
class AutoHeightGridView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GridView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = View.MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, h)
    }
}