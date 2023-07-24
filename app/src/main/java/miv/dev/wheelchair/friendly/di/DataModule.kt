package miv.dev.wheelchair.friendly.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.net.InetSocketAddress
import java.net.Proxy


@Module
interface DataModule {
	
	
	companion object {
		@Provides
		@ApplicationScope
		fun providesHttpClient(): HttpClient {
			val client = HttpClient(Android) {
				install(ContentNegotiation){
					json(Json {
						ignoreUnknownKeys = true
						prettyPrint = true
					})
				}
				engine {
					connectTimeout = 100_000
					socketTimeout = 100_000
				}
			}
			return client
		}
	}
}
