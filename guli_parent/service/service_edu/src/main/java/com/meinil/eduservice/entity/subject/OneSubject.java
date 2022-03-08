package com.meinil.eduservice.entity.subject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 * @Author Meinil
 * @Version 1.0
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TwoSubject> children;

    public void addChildren(TwoSubject twoSubject) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(twoSubject);
    }
}
