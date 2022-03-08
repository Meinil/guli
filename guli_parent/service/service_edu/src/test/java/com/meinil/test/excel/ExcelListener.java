package com.meinil.test.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Author Meinil
 * @Version 1.0
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    // 一行一行读取内容
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(data);
    }

    // 读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头: " + headMap);
    }

    // 读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
