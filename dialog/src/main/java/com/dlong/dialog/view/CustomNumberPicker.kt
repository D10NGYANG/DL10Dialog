package com.dlong.dialog.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import com.dlong.dialog.R


/**
 * 自定义数字选择器
 *
 * @author D10NG
 * @date on 2020/7/2 7:38 PM
 */
class CustomNumberPicker constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int
) : NumberPicker(context, attrs, defStyle) {

    constructor(context: Context) : this(context, null, android.R.attr.numberPickerStyle)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.numberPickerStyle)

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is EditText) {
            //设置文字的颜色和大小
            child.setTextColor(ContextCompat.getColor(context, R.color.colorTextNormal))
            child.textSize = 16f
        }
    }
}