package com.xy.composedemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainContent(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = {
            navController.navigate("second_page")
        }) {
            Text(text = "默认下拉刷新")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            navController.navigate("third_page")
        }) {
            Text(text = "Lottie动画下拉刷新")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            navController.navigate("fourth_page")
        }) {
            Text(text = "下拉刷新与上拉加载")
        }
    }
}