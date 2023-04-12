package com.example.compose_dome

import android.content.res.Configuration
import android.content.res.Resources

/**
 * 设置白天黑夜模式
 */
fun Resources.applyNight(isNight: Boolean) {
    val config = Configuration(configuration)
    config.applyNight(isNight)
    // 更新
    updateConfiguration(config, displayMetrics)
}

fun Configuration.applyNight(isNight: Boolean) {
    if (isNight) {
        uiMode = (
            Configuration.UI_MODE_NIGHT_YES or
                // 清空标志位
                (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
            )
    } else {
        uiMode = (
            Configuration.UI_MODE_NIGHT_NO
                or (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
            )
    }
}
