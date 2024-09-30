package com.example.yozmhealth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("/istatic/images")
    private String imgStatic;

    @Value("${server.file.upload}")
    private String imgPath;

    //에디터 이미지 및 이미지 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/","classpath:/template/","file:///" + imgPath,"classpath:/istatic/**","/**");
        registry
                .addResourceHandler(imgStatic + "**")
                .addResourceLocations("file:///" + imgPath)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        registry
                .addResourceHandler("/attachFolder/summernote/**") // 특정 경로에 대한 핸들러 설정
                .addResourceLocations("file:///" + imgPath + "summernote/") // 해당 경로에서 리소스 로드
                .setCachePeriod(0) // 캐시 비활성화
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
