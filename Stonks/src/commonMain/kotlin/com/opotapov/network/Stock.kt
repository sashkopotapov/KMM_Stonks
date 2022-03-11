package com.opotapov.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json as KotlinJson

// Yahoo Finance Api
@Serializable
data class Stock(val ask: Double, val displayName: String, val symbol: String)

@Serializable
data class YFQuoteResultResponse(val result: List<Stock>)

@Serializable
data class YFQuoteResponse(val quoteResponse: YFQuoteResultResponse)

// Alpha Vantage Api
@Serializable
data class StockDetails(@SerialName("Name") val name: String, @SerialName("Description") val description: String)

class StonksApi() {
    val client: HttpClient = HttpClient() {
        install(Logging)
        install(JsonFeature) {
            serializer = KotlinxSerializer(KotlinJson { ignoreUnknownKeys = true })
        }
    }
    val baseUrlYF: String = "https://yfapi.net"
    val baseUrlAV: String = "https://www.alphavantage.co"

    suspend fun fetchQuote(): YFQuoteResponse {
        val response: YFQuoteResponse = client.get("$baseUrlYF/v6/finance/quote?region=US&lang=en&symbols=AAPL,NFLX,MCD,OXY,AMD,AAL,NIO,RIG,BAC,CCL") {
            headers {
                append("X-API-KEY", "w75MwFHp9T4pwlxUQiZCHxwvphKynif3jE8clGca")
            }
        }

        return  response
    }

    suspend fun fetchOverview(symbol: String): StockDetails {
        val response: StockDetails = client.get("$baseUrlAV/query?function=OVERVIEW&symbol=$symbol&apikey=5419EMGOMHVP64L7")
        return  response
    }
}

