package com.meinil.educenter.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.meinil.educenter.utils.GithubConstant;

/**
 * @Author Meinil
 * @Version 1.0
 */
public class Github {
    @JSONField(name = "client_id")
    private static String clientId;

    @JSONField(name = "client_secret")
    private static String clientSecret;

    private String code;

    public Github() {
    }

    public Github(String code) {
        this.code = code;
        clientId = GithubConstant.GITHUB_CLIENT_ID;
        clientSecret = GithubConstant.CLIENT_SECRET;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setClientId(String clientId) {
        Github.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        Github.clientSecret = clientSecret;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
