package com.meinil.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.eduservice.entity.EduSubject;
import com.meinil.eduservice.entity.excel.SubjectData;
import com.meinil.eduservice.entity.subject.OneSubject;
import com.meinil.eduservice.entity.subject.TwoSubject;
import com.meinil.eduservice.listener.SubjectExcelListener;
import com.meinil.eduservice.mapper.EduSubjectMapper;
import com.meinil.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 使用excel表格导入课程分类
     */
    @Override
    public void saveSubject(MultipartFile file) {
        try(InputStream in = file.getInputStream()) {
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回课程的树形结构
     */
    @Override
    public List<OneSubject> getAllSubject() {

        // 查询一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);

        // 查询二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

        List<OneSubject> finalSubject = new ArrayList<>();
        for (EduSubject oneItem : oneSubjects) {
            OneSubject subject = new OneSubject();
            BeanUtils.copyProperties(oneItem, subject);
            for (EduSubject twoItem : twoSubjects) {
                if (twoItem.getParentId().equals(oneItem.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoItem, twoSubject);
                    subject.addChildren(twoSubject);
                }
            }
            finalSubject.add(subject);
        }

        return finalSubject;
    }
}
