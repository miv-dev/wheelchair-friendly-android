package miv.dev.wheelchair.friendly.di

import android.content.Context
import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import miv.dev.wheelchair.friendly.data.local.TokenPair
import miv.dev.wheelchair.friendly.data.local.TokenService
import miv.dev.wheelchair.friendly.data.repositories.AuthenticationRepositoryImpl
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository


@Module
interface DataModule {
	
	@Binds
	@ApplicationScope
	fun bindAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
	
	companion object {
		@Provides
		@ApplicationScope
		fun providesTokenService(context: Context): TokenService {
			return TokenService(context)
		}
		
		@Provides
		@ApplicationScope
		fun providesHttpClient(tokenService: TokenService): HttpClient {
			val client = HttpClient(Android) {
				defaultRequest {
					url("http://192.168.0.36:8080/")
					contentType(ContentType.Application.Json)
					accept(ContentType.Application.Json)
				}
				
				install(Logging) {
					level = LogLevel.ALL
					logger = object : Logger {
						override fun log(message: String) {
							Log.i("HttpClient", message)
						}
					}
				}
				install(ContentNegotiation) {
					json(Json {
						ignoreUnknownKeys = true
						prettyPrint = true
					})
				}
				install(Auth) {
					bearer {
						loadTokens {
							val bearer = tokenService.getBearerToken()
							val refresh = tokenService.getRefreshToken()
							
							BearerTokens(bearer ?: "", refresh ?: "")
						}
						refreshTokens {
							val token = client.post {
								markAsRefreshTokenRequest()
								url("auth/refresh")
								setBody(hashMapOf("refreshToken" to tokenService.getRefreshToken()))
							}.body<TokenPair>()
							BearerTokens(token.accessToken, token.refreshToken)
						}
						sendWithoutRequest { true }
					}
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
