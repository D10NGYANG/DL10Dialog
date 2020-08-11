package com.dlong.dialog.utils

import android.content.Context

/**
 * @author D10NG
 * @date on 2020/8/11 10:01 AM
 */
fun Context.dp2px(dp: Float): Float = dp * this.resources.displayMetrics.density

fun Context.px2dp(px: Float): Float = px / this.resources.displayMetrics.density

