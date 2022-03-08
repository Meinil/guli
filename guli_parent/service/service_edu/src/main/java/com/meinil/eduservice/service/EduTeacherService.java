package com.meinil.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-23
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> page);
}
