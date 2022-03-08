package com.meinil.educenter.service;

import com.meinil.educenter.entity.UcenterMember;
import com.meinil.educenter.entity.vo.LoginVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meinil.educenter.entity.vo.RegisterVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Meinil
 * @since 2022-03-03
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @return token
     */
    String login(LoginVo loginVo);

    /**
     * 注册
     */
    void register(RegisterVo registerVo);

    /**
     * 根据id查询用户信息
     */
    UcenterMember getUserInfo(String memberId);

    /**
     * wechat授权登录
     * 根据请求码获取access_token,再获取用户信息
     * @return 重定向地址
     */
    String getWechatRequestAddress(String code);

    /**
     * github授权登录
     * @return 重定向地址
     */
    String getGithubRequestAddress(String code);

    /**
     * 查询某一天的注册人数
     */
    Integer countRegister(@Param("day") String day);
}
