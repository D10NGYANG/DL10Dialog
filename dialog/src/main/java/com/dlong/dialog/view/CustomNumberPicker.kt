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
    attrs: AttributeSet? = null
) : NumberPicker(context, attrs) {

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is EditText) {
            //设置文字的颜色和大小
            child.setTextColor(ContextCompat.getColor(context, R.color.colorTextMessage))
            child.textSize = 16f
        }
    }
}