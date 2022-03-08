package com.meinil.eduservice.service;

import com.meinil.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meinil.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     */
    void saveSubject(MultipartFile file);

    /**
     * 返回所有的分类
     */
    List<OneSubject> getAllSubject();
}
