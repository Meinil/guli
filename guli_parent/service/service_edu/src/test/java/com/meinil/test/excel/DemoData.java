package com.meinil.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Meinil
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoData {
    @ExcelProperty(value = "学生的编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生的姓名", index = 1)
    private String sname;

    @Override
    public String toString() {
        return "编号: " + sno + "\t姓名: " + sname;
    }
}
