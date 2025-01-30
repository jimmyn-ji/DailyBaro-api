package com.project.hanfu.config;

import com.project.hanfu.jackson.JacksonMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 不需要 Token 拦截器和 OpenApi 拦截器
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置Swagger相关路径
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置所有路径支持跨域访问
        registry.addMapping("/**")
//                .allowedOrigins("*") // 允许所有来源
                .allowedOrigins("http://localhost:8080")  // 允许来自 http://localhost:8084 的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的 HTTP 方法
                .allowedHeaders("*")  // 允许所有的请求头
                .allowCredentials(true)  // 是否允许带有凭证（例如 cookies）
                .maxAge(3600);  // 预检请求的有效期，单位秒
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 配置自定义的 JacksonMapper
        converters.add(new MappingJackson2HttpMessageConverter(new JacksonMapper()));
    }
}