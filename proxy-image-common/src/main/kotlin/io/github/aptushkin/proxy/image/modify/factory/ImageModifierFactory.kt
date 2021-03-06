package io.github.aptushkin.proxy.image.modify.factory

import io.github.aptushkin.proxy.image.modify.ByteArrayModifier
import io.github.aptushkin.proxy.image.modify.predicate.ImageModifierPredicate
import org.springframework.web.server.ServerWebExchange

interface ImageModifierFactory<in C> {
    fun imageModifier(config: C, serverWebExchange: ServerWebExchange): ByteArrayModifier

    fun imageModifierPredicate(config: C, serverWebExchange: ServerWebExchange): ImageModifierPredicate
}
