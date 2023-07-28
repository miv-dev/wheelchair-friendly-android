package miv.dev.wheelchair.friendly.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import miv.dev.wheelchair.friendly.data.repositories.AuthenticationRepositoryImpl
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import java.net.InetSocketAddress
import java.net.Proxy


@Module
interface DataModule {
	
	@Binds
	@ApplicationScope
	fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
	
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
