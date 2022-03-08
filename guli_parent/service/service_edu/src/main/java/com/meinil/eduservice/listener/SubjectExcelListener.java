package com.meinil.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meinil.commonutils.ResultCode;
import com.meinil.eduservice.entity.EduSubject;
import com.meinil.eduservice.entity.excel.SubjectData;
import com.meinil.eduservice.service.EduSubjectService;
import com.meinil.servicebase.exceptionhandler.GuliException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author Meinil
 * @Version 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService eduSubjectService;

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new GuliException(ResultCode.ERROR, "文件数据为空");
        }

        // 判断一级分类是否存在
        EduSubject eduOneSubject = existOneSubject(data.getOneSubjectName());
        if (eduOneSubject == null) {
            eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(data.getOneSubjectName());
            eduSubjectService.save(eduOneSubject);
        }

        // 判断二级分类是否存在
        EduSubject eduTwoSubject = existTwoSubject(data.getTwoSubjectName(), eduOneSubject.getId());
        if (eduTwoSubject == null) {
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(eduOneSubject.getId());
            eduTwoSubject.setTitle(data.getTwoSubjectName());
            eduSubjectService.save(eduTwoSubject);
        }
    }

    /**
     * 判断一级分类是否存在
     */
    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return eduSubjectService.getOne(wrapper);
    }

    /**
     * 判断二级分类是否存在
     */
    private EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return eduSubjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
