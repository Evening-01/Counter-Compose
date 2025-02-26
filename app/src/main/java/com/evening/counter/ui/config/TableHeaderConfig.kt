package com.evening.counter.ui.config

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.evening.counter.R

/**
 * 表头配置类，用于解耦表头资源与UI逻辑
 */
object TableHeaderConfig {
    // 表头资源ID列表
    @get:ArrayRes
    val headerResIds: List<Int> = listOf(
        R.string.header_1,
        R.string.header_2,
        R.string.header_3
    )
}