package com.meinil.oss.controller;

import com.meinil.commonutils.R;
import com.meinil.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Meinil
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation("上传头像")
    @PostMapping("")
    public R uploadOssFile(@ApiParam("文件对象") MultipartFile file) {
        return R.ok()
                .data("url", ossService.uploadFileAvatar(file));
    }
}
