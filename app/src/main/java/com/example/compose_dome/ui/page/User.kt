package com.example.compose_dome.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose_dome.R
import com.example.compose_dome.skin.bitmapPainter
import com.example.compose_dome.skin.color

/**
 * User界面
 */
val tintColorId = listOf(R.color.user_icon_tint_1, R.color.user_icon_tint_2, R.color.user_icon_tint_3)
val items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)

@Composable
fun User(controller: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            // 显示图片
            painter = R.mipmap.core_ui_icon_common_back_56_n.bitmapPainter(),
            contentDescription = "back",
            modifier = Modifier.clickable {
                controller.popBackStack()
            },
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = R.mipmap.icon_user_main_default.bitmapPainter(),
                        contentDescription = "",
                        // 添加colorFilter使颜色更明显
                        // colorFilter = ColorFilter.tint(R.color.user_icon_tint.color()),
                    )
                    Text(
                        text = "My Name",
                        // 设置文本颜色
                        color = R.color.test_color.color(),
                    )
                    LazyColumn(content = {
                        itemsIndexed<Int>(items = items) { index, item ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = R.mipmap.icon_user_main_default.bitmapPainter(),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp, 30.dp),
                                    colorFilter = ColorFilter.tint(tintColorId[index % tintColorId.size].color()),
                                )
                                Text(text = "item $item", color = R.color.test_color.color(), modifier = Modifier.padding(10.dp))
                            }
                        }
                    }, modifier = Modifier.height(300.dp).fillMaxWidth().padding(start = 100.dp, top = 20.dp))
                }
            }
        }
    }
}
