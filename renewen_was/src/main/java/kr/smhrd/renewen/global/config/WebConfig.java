package kr.smhrd.renewen.global.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    @Autowired
    private WebConfigInitializer configInitializer;
    
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	String uploadImgPath = configInitializer.getUploadImgPath();
    	logger.info("uploadImgPath {}" , uploadImgPath);
    	
    	// 1) 업로드 된 이미지 경로
    	registry
    		.addResourceHandler("/imgs/**") // 웹에서 접근할 url
    		.addResourceLocations("file:///" + uploadImgPath + "/"); // 실제 업로드 경로 
        
    }
}
