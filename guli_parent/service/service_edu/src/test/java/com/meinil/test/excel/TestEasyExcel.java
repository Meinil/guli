package com.meinil.test.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Meinil
 * @Version 1.0
 */
public class TestEasyExcel {
    // 读操作
    public static void main(String[] args) {
        String fileName = "C:/Users/47106/Desktop/demo.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener())
                 .sheet()
                 .doRead();
    }

    // 写操作
    public static void write(String[] args) {
        String fileName = "C:/Users/47106/Desktop/demo.xlsx";
        EasyExcel.write(fileName, DemoData.class)
                 .sheet("学生列表")
                 .doWrite(TestEasyExcel::getList);
    }

    private static List<DemoData> getList() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new DemoData(i, String.valueOf('A' + i)));
        }
        return list;
    }
}
