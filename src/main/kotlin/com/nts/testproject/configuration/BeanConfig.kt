package com.nts.testproject.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.multipart.support.StandardServletMultipartResolver
import javax.servlet.MultipartConfigElement

@Configuration
class BeanConfig {

    @Bean
    fun multipartConfigElement() = MultipartConfigElement("")

    @Bean
    fun multipartResolver() = StandardServletMultipartResolver()
}
