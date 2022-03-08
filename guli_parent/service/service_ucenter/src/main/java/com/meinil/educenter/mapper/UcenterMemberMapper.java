package com.meinil.educenter.mapper;

import com.meinil.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Meinil
 * @since 2022-03-03
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 查询某一天的注册人数
     */
    Integer countRegister(String day);
}
