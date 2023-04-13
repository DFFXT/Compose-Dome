package com.example.compose_dome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_dome.skin.color
import com.example.compose_dome.skin.colorPainter
import com.example.compose_dome.ui.page.Home
import com.example.compose_dome.ui.page.User
import com.example.compose_dome.ui.theme.ComposeDomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 初始化
            val controller = rememberNavController()
            ComposeDomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    // navigation 路由
                    NavHost(navController = controller, startDestination = "Home", builder = {
                        this.composable("Home") {
                            Home(controller = controller)
                        }
                        this.composable("User") {
                            User(controller = controller)
                        }
                    }, modifier = Modifier.background(R.color.app_background.color()))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDomeTheme {
        Greeting("Android")
    }
}
