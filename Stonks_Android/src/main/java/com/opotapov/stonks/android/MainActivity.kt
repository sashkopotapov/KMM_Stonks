package com.opotapov.stonks.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.opotapov.network.Stock
import com.opotapov.network.StonksApi
import com.opotapov.stonks.Greeting

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        composeView = findViewById(R.id.compose_view)
        composeView.setContent {
            MaterialTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(
        content = {
            HomeContent()
        }
    )
}

@Composable
fun HomeContent() {
//    val stocks = listOf<Stock>(
//        Stock(149.43, "Apple Inc.", "AAPL"),
//        Stock(50.21, "Netflix", "NFLX"),
//        Stock(250.32, "McDonald`s", "MCD")
//    )
    val stocks = remember { mutableStateListOf<Stock>() }
    LaunchedEffect(Unit, block = {
        val fetchedStocks = StonksApi().fetchQuote()
        stocks.addAll(fetchedStocks.quoteResponse.result)
    })

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = stocks,
            itemContent = {
                StockListItem(stock = it)
            })
    }
}


@Composable
fun StockListItem(stock: Stock) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = stock.symbol, style = typography.body1)
            Text(text = stock.displayName, style = typography.caption)
        }
        Spacer(
            Modifier
                .weight(1f)
        )

        Text(text = "${stock.ask}$",
            style = typography.body1,
            modifier = Modifier
            .padding(4.dp))
    }
}

@Preview
@Composable
fun ComposablePreview() {
    MyApp()
}