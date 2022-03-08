package com.meinil.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.meinil.oss.service.OssService;
import com.meinil.oss.utils.OssConstantUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                OssConstantUtils.END_POINT,
                OssConstantUtils.KEY_ID,
                OssConstantUtils.KEY_SECRET);

        try(InputStream inputStream = file.getInputStream();) {
            // 生成文件名 以日期进行分类管理
            String fileName = new DateTime().toString("yyyy/MM/dd")
                    +"/"
                    + UUID.randomUUID().toString().replace("-", "")
                    + file.getOriginalFilename();

            // Bucket名称
            // 文件路径和名称
            // 输入流
            ossClient.putObject(
                    OssConstantUtils.BUCKET_NAME,
                    fileName,
                    inputStream);

            return String.format(
                    "https://%s.%s/%s",
                    OssConstantUtils.BUCKET_NAME,
                    OssConstantUtils.END_POINT,
                    fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return null;
    }
}
