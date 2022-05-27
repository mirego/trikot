package com.mirego.kword.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

class MainActivity : AppCompatActivity() {

    private val textProvider = TrikotApplication.instance.serviceLocator.textProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(textProvider = textProvider)
        }
    }

    @Composable
    fun Screen(textProvider: TextProvider) {
        val text: String by textProvider.text.collectAsState("")
        val buttonText: String by textProvider.buttonText.collectAsState( "")
        Scaffold(
            topBar = { TopAppBar { Text(text = "Kword Sample with flows", color = Color.White) } },
            content = { paddingValues ->  Content(paddingValues, text, buttonText) }
        )
    }

    @Composable
    fun Content(paddingValues: PaddingValues, text: String, buttonText: String) = Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text= text,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentWidth()
        )
        Button(onClick = { textProvider.toggleLanguage() }) {
            Text(text = buttonText)
        }
    }
}
