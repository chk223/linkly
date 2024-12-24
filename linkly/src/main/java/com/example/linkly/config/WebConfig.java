
package com.example.linkly.config;

import com.example.linkly.filter.JwtFilter;
import com.example.linkly.interceptor.RoleInterceptor;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleInterceptor roleInterceptor;
    private final JwtFilter jwtFilter;
    @Autowired
    public WebConfig(RoleInterceptor roleInterceptor, JwtFilter jwtFilter) {
        this.roleInterceptor = roleInterceptor;
        this.jwtFilter = jwtFilter;
    }


    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(roleInterceptor)
//                .addPathPatterns("/**") // 모든 경로에 적용
//                .excludePathPatterns("/auth/**"); // 인증 경로는 제외
    }

    @Bean
    public FilterRegistrationBean<Filter> customFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // Filter 등록
        filterRegistrationBean.setFilter(jwtFilter);
        // Filter 순서 설정
        filterRegistrationBean.setOrder(1);
        // 전체 URL에 Filter 적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

}

