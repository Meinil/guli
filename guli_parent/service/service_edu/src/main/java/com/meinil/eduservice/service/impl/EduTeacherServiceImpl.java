package com.meinil.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meinil.eduservice.entity.EduTeacher;
import com.meinil.eduservice.mapper.EduTeacherMapper;
import com.meinil.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-23
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> page) {
        Map<String, Object> data = new HashMap<>();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");

        baseMapper.selectPage(page, wrapper);
        data.put("items", page.getRecords());
        data.put("current", page.getCurrent());
        data.put("pages", page.getPages());
        data.put("size", page.getSize());
        data.put("hasNext", page.hasNext());
        data.put("hasPrevious", page.hasPrevious());
        data.put("total", page.getTotal());
        return data;
    }
}
