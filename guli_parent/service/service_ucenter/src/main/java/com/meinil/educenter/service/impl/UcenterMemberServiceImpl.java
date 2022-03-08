package com.meinil.educenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.meinil.commonutils.JwtUtils;
import com.meinil.commonutils.MD5;
import com.meinil.commonutils.ResultCode;
import com.meinil.educenter.entity.Github;
import com.meinil.educenter.entity.UcenterMember;
import com.meinil.educenter.entity.vo.LoginVo;
import com.meinil.educenter.mapper.UcenterMemberMapper;
import com.meinil.educenter.service.UcenterMemberService;
import com.meinil.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meinil.educenter.utils.GithubConstant;
import com.meinil.educenter.utils.HttpClientUtils;
import com.meinil.educenter.utils.WechatConstant;
import com.meinil.servicebase.exceptionhandler.GuliException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Meinil
 * @since 2022-03-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${front.homepage}")
    private String homepage;

    @Autowired
    private OkHttpClient client;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCode.ERROR, "密码或账号不能为空");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);

        if (member == null) {
            throw new GuliException(ResultCode.ERROR, "账号不正确");
        }

        // 数据库中的密码已经加密
        if (!member.getPassword().equals(MD5.encrypt(password))) {
            throw new GuliException(ResultCode.ERROR, "密码不正确");
        }

        if (member.getIsDisabled()) {
            throw new GuliException(ResultCode.ERROR, "账号已被封禁");
        }

        // 登录成功返回token
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // 非空判断
        if (mobile == null || code == null || nickname == null || password == null) {
            throw new GuliException(ResultCode.ERROR, "注册信息不能为空");
        }

        // 验证码判断
        String cacheCode = redisTemplate.opsForValue().get(mobile);
        if (cacheCode == null) {
            throw new GuliException(ResultCode.ERROR, "验证码已过期,请重新申请");
        }
        if (!cacheCode.equals(code)) {
            throw new GuliException(ResultCode.ERROR, "请填写正确的验证码");
        }

        // 判断账号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(ResultCode.ERROR, "账号已经存在,请登录");
        }

        // 插入数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://meinil-edu.oss-cn-shanghai.aliyuncs.com/2022/02/25/386520050.jpg");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getUserInfo(String memberId) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.select("id", "mobile", "nickname", "avatar");
        wrapper.eq("id", memberId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public String getWechatRequestAddress(String code)  {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                WechatConstant.WECHAT_APP_ID,
                WechatConstant.WECHAT_APP_SECRET,
                code);
        try {
            // 根据token获取 access_token和openid
            String accessTokenInfo = HttpClientUtils.get(url);
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String)map.get("access_token");
            String openid = (String) map.get("openid");

            // 根据accessToken和openid获取用户信息
            url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", accessToken, openid);
            String userinfo = HttpClientUtils.get(url);
            map = gson.fromJson(userinfo, HashMap.class);
            String nickname = (String) map.get("nickname");
            String avatar = (String) map.get("headimgurl");

            // 保存到数据库
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("openid", openid);
            UcenterMember member = baseMapper.selectOne(wrapper);
            if (member == null) {
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setAvatar(avatar);
                member.setOpenid(openid);
                baseMapper.insert(member);
            } else {
                member.setNickname(nickname);
                member.setAvatar(avatar);
                baseMapper.update(member, wrapper);
            }
            return String.format("redirect:%s?token=%s", homepage, JwtUtils.getJwtToken(member.getId(), nickname));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }

    @Override
    public String getGithubRequestAddress(String code) {
        Github github = new Github(code);
        RequestBody body = RequestBody.create(JSON.toJSONString(github), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            String accessToken = jsonObject.getObject("access_token", String.class);

            Request authorization = new Request.Builder()
                    .url("https://api.github.com/user")
                    .addHeader("Authorization", "token " + accessToken)
                    .build();
            try(Response authorizationResponse = client.newCall(authorization).execute()) {
                jsonObject = JSON.parseObject(authorizationResponse.body().string());
                String nickname = jsonObject.getObject("login", String.class);
                String avatar = jsonObject.getObject("avatar_url", String.class);
                String githubId = jsonObject.getObject("id", String.class);

                QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
                wrapper.eq("github_id", githubId);
                UcenterMember member = baseMapper.selectOne(wrapper);
                if (member == null) {
                    member = new UcenterMember();
                    member.setAvatar(avatar);
                    member.setNickname(nickname);

                    member.setGithubId(githubId);
                    baseMapper.insert(member);
                } else {
                    member.setAvatar(avatar);
                    member.setNickname(nickname);
                    baseMapper.update(member, wrapper);
                }

                return String.format("redirect:%s?token=%s", homepage, JwtUtils.getJwtToken(member.getId(), nickname));
            }
        } catch (IOException e) {
            throw new GuliException(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 查询某一天的注册人数
     */
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
