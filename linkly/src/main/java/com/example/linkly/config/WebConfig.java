//package com.example.linkly.config;
//
//import com.example.linkly.filter.LoginFilter;
//import jakarta.servlet.Filter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig {
//
//    @Bean
//    public FilterRegistrationBean loginFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        // Filter 등록 (로그인 필터)
//        filterRegistrationBean.setFilter(new LoginFilter());
//        // Filter 순서 결정
//        filterRegistrationBean.setOrder(1);
//        // 전체 URL에 Filter 적용
//        filterRegistrationBean.addUrlPatterns("/*");
//
//        return filterRegistrationBean;
//    }
//}
