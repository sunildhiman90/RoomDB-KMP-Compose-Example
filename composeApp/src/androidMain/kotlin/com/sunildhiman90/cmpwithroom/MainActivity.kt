package com.sunildhiman90.cmpwithroom

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import appDatabase.DBFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = DBFactory(applicationContext).createDatabase()
            App(db)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}