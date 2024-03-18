package com.yang.olojbackendgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

/**
 * @author GuoYang
 * @version 1.0
 */
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter(){
        CorsConfiguration config =new CorsConfiguration();
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        //TODO 实际改为线上真实地址 本地域名
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",config);
        return new CorsWebFilter((CorsConfigurationSource) source);


    }
}
