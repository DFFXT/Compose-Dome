package com.example.compose_dome

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_dome.ui.theme.ComposeDomeTheme

lateinit var resourceProvider: MutableState<ResourceProvider>

class ResourceProvider(private val res: Resources, isNight: Boolean) {
    init {
        res.applyNight(isNight)
    }

    fun getColorById(id: Int): androidx.compose.ui.graphics.Color {
        return Color(res.getColor(id))
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            resourceProvider = remember {
                mutableStateOf(ResourceProvider(resources, false))
            }
            val controller = rememberNavController()
            ComposeDomeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    NavHost(navController = controller, startDestination = "Home", builder = {
                        this.composable("Home") {
                            Column {
                                Text(
                                    text = "Home Page goto UserPage||",
                                    color = resourceProvider.value.getColorById(R.color.test_color),
                                    modifier = Modifier.clickable {
                                        controller.navigate("User")
                                    }
                                )
                                Text(
                                    text = "makeDay||",
                                    modifier = Modifier.clickable {
                                        resourceProvider.value = ResourceProvider(resources, false)
                                    },
                                )
                                Text(
                                    text = "makeNight",
                                    modifier = Modifier.clickable {
                                        resourceProvider.value = ResourceProvider(resources, true)
                                    },
                                )
                            }

                        }
                        this.composable("User") {
                            Text(
                                text = "User Page goto Home Page",
                                color = resourceProvider.value.getColorById(R.color.test_color),
                                modifier = Modifier.clickable {
                                    controller.popBackStack()
                                }
                            )
                        }
                    })
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
