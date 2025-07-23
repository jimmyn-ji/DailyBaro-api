package com.project.service.Impl;

import com.project.service.FileStorageService;
import com.project.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Result<String> storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Result.fail("文件为空，无法上传。");
        }

        try {
            // 确保上传目录存在
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // 保存文件
            File dest = new File(uploadDir + File.separator + newFilename);
            file.transferTo(dest);

            // 返回文件访问路径
            String fileUrl = "/uploads/" + newFilename;
            return Result.success(fileUrl);
        } catch (IOException e) {
            return Result.fail("文件上传失败: " + e.getMessage());
        }
    }
} 