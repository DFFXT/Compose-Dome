package com.example.compose_dome.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.compose_dome.R
import com.example.compose_dome.skin.*

@Composable
fun Home(controller: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val selected = remember {
            mutableStateOf(true)
        }
        Text(
            text = "Home Page",
            fontSize = 40.sp,
            // 设置文本颜色
            color = R.color.test_color.color(),
            // 此处模拟使用带状态的drawable
            modifier = Modifier.drawable(R.drawable.bg_app, selected = selected.value).clickable {
                selected.value = !selected.value
            },
        )
        Spacer(modifier = Modifier.height(100.dp))
        Row {
            Text(
                text = "白天模式",
                // 设置文本颜色, 采用带状态的颜色
                color = R.color.btn_selector.stateColor(selected = !isNight()),
                modifier = Modifier.selectable(selected = true) {
                    GlobalResourcesProvider.switchTheme(isNight = false)
                },
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "黑夜模式",
                // 设置文本颜色，采用带状态的颜色
                color = R.color.btn_selector.stateColor(selected = isNight()),
                modifier = Modifier.clickable {
                    GlobalResourcesProvider.switchTheme(isNight = true)
                },
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "下一个界面",
            fontSize = 20.sp,
            color = R.color.test_color.color(),
            modifier = Modifier.clickable {
                controller.navigate("User")
            },
        )
    }
}
