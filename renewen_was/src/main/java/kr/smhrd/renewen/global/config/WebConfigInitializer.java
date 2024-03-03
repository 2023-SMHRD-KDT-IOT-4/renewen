package kr.smhrd.renewen.global.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class WebConfigInitializer {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.img}")
    private String imgDir;

    private String uploadImgPath;

    @PostConstruct
    public void init() {
        uploadImgPath = Paths.get(uploadPath, imgDir).toString();
    }

    // "\\" => "/"로 치환
    public String getUploadImgPath() {
        return uploadImgPath.replace("\\", "/");
    }
}
