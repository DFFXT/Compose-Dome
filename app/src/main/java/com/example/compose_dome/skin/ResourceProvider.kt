package com.example.compose_dome.skin

import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.compose_dome.App
import com.example.compose_dome.applyNight

/**
 * 全局资源提供器
 */
var GlobalResourcesProvider by mutableStateOf(ResourceProvider(false))

/**
 * 资源提供器
 */
class ResourceProvider(val isNight: Boolean) {
    init {
        // 更新底层Resource对象的uiMode
        // 这里采用全局更改的方式，具体看需求实现
        ContextCollector.allContext.map { it.get() }.filterNotNull().forEach {
            it.resources.applyNight(isNight)
        }
    }

    /**
     * 换肤切换，并触发更新
     */
    fun switchTheme(isNight: Boolean) {
        // 重新new一个对象，触发更新操作
        GlobalResourcesProvider = ResourceProvider(isNight)
    }

    @Composable
    fun getColorById(id: Int): Color {
        return colorResource(id = id)
    }

    @Composable
    fun getBitmapPainter(id: Int): Painter {
        return painterResource(id = id)
    }

    @Composable
    fun getColorPainter(id: Int): Painter {
        return ColorPainter(colorResource(id))
    }

    @Composable
    fun getColorById(id: Int, state: IntArray): Color {
        val stateColor = LocalContext.current.getColorStateList(id)
        return Color(stateColor.getColorForState(state, stateColor.defaultColor))
    }
}

// region kt扩展，方便获取
/**
 * 获取颜色
 * @param provider 用哪个资源提供器，对应的资源提供器更新时会自动触发对应UI的更新
 */
@Composable
fun @receiver:ColorRes Int.color(provider: ResourceProvider = GlobalResourcesProvider): Color {
    return provider.getColorById(id = this)
}

@Composable
fun @receiver:DrawableRes Int.bitmapPainter(provider: ResourceProvider = GlobalResourcesProvider): Painter {
    return provider.getBitmapPainter(id = this)
}

@Composable
fun @receiver:ColorRes Int.colorPainter(provider: ResourceProvider = GlobalResourcesProvider): Painter {
    return provider.getColorPainter(id = this)
}

fun isNight(provider: ResourceProvider = GlobalResourcesProvider): Boolean {
    return provider.isNight
}

// endregion
/**
 * Compose中没有带状态的颜色（ColorStateList）
 * 如果硬要用状态颜色，需要自己传状态
 * @param selected 是否选中
 * 还可以根据需要添加 pressed、enable等状态，不传代表着false状态
 */
@Composable
fun @receiver:ColorRes Int.stateColor(
    provider: ResourceProvider = GlobalResourcesProvider,
    selected: Boolean = false,
    enable: Boolean = false,
): Color {
    return provider.getColorById(id = this, state = getState(selected, enable))
}

fun getState(selected: Boolean, enable: Boolean): IntArray {
    val state = mutableListOf<Int>()
    if (selected) {
        state.add(android.R.attr.state_selected)
    }
    if (enable) {
        state.add(android.R.attr.state_enabled)
    }
    return state.toIntArray()
}

/**
 * 使用原生drawable对象
 * Compose现在已经不支持设置drawable了 [painterResource] 可知只支持矢量图和bitmap
 * 而且需要传递状态啥的，有点麻烦，除非将状态也进行封装(未尝试)
 */
fun Modifier.drawable(id: Int, selected: Boolean = false, enable: Boolean = false): Modifier {
    return drawBehind {
        val drawable = App.application.getDrawable(id)!!
        val s = size
        drawable.setBounds(0, 0, s.width.toInt(), s.height.toInt())
        if (drawable is StateListDrawable) {
            drawable.state = getState(selected, enable)
        }
        drawIntoCanvas {
            drawable.draw(it.nativeCanvas)
        }
    }
}
