package com.kangyi.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.kangyi.feign.TokenService;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(String username, String password, String grant_type, String client_id, String client_secret, String scope) {
        System.out.println(username);
        System.out.println(password);
        JSONObject j = new JSONObject();
        j.put("code",500);
        j.put("data",null);
        j.put("msg","oauth服务调用异常");
        return j.toJSONString();
    }
}
