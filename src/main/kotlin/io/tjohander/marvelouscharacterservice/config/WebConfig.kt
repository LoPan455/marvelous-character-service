package io.tjohander.marvelouscharacterservice.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig {

    @Bean("client")
    fun client(): OkHttpClient = OkHttpClient()

    @Bean("mapper")
    fun mapper(): ObjectMapper = ObjectMapper()
        .findAndRegisterModules()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
}