package io.github.aptushkin.proxy.image.modify.predicate

import io.github.aptushkin.proxy.image.modify.config.DefaultImageConfig
import org.springframework.web.server.ServerWebExchange

class DefaultImageModifierPredicate(private val config: DefaultImageConfig): ImageModifierPredicate {
    override fun test(exchange: ServerWebExchange): Boolean {
        val values = (exchange.response.headers).getOrDefault(config.responseHeaderName, emptyList<String>())
        if (values.isEmpty()) return config.onNotExistedHeader
        val params = exchange.request.queryParams
        return (params.isNotEmpty() || config.isDefaultsAvailable()) && values
                .stream()
                .anyMatch { value -> value.matches(config.regexp.toRegex()) }
    }
}