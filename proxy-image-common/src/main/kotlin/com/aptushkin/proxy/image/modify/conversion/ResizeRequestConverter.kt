package com.aptushkin.proxy.image.modify.conversion

import com.aptushkin.proxy.image.modify.config.ResizeImageConfig
import com.aptushkin.proxy.image.modify.domain.ResizeRequest
import com.aptushkin.proxy.image.modify.scalr.toScalrMethod
import com.aptushkin.proxy.image.modify.scalr.toScalrMode
import org.imgscalr.Scalr
import org.springframework.core.convert.converter.Converter
import org.springframework.web.server.ServerWebExchange

class ResizeRequestConverter(private val config: ResizeImageConfig) : Converter<ServerWebExchange, ResizeRequest> {

    override fun convert(serverWebExchange: ServerWebExchange): ResizeRequest {
        val uri = serverWebExchange.request.uri.toString()
        val params = serverWebExchange.request.queryParams

        val format: String = if (params.containsKey("format")) {
            params.getFirst("format")
        } else {
            if (uri.contains(".")) {
                uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))
            } else config.defaultFormat
        } ?: throw IllegalAccessException("Either format must exists at the request params or a the application properties as defaultFormat")

        val width: Int? = params["width"]?.firstOrNull()?.toInt() ?: config.defaultWidth
        val height: Int? = params["height"]?.firstOrNull()?.toInt() ?: config.defaultHeight

        if (width == null || height == null) throw IllegalAccessException("Either width & height must exist at the request params or at " +
                "the application properties as defaultWidth / defaultHeight")

        val mode: Scalr.Mode? = params["mode"]?.firstOrNull()?.toScalrMode() ?: config.defaultMode
        val method: Scalr.Method? = params["method"]?.firstOrNull()?.toScalrMethod() ?: config.defaultMethod
        return ResizeRequest(
                width, height, format, method, mode
        )
    }
}
