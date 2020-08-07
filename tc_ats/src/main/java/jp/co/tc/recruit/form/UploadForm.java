package jp.co.tc.recruit.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadForm{
    private MultipartFile multipartFile;
}
