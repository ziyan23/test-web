package com.ymm.model;

import com.ymm.http.HttpClient;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by gujie on 16/8/1.
 */
public class YmmAction{


    // 发送登录请求
    public String YmmGetverifycodeAction(String telephone) throws ParseException, IOException {

        String requestResult = HttpClient.toString(HttpClient.postJson("http://192.168.199.120/logistics/users/getverifycode",
                "{\"telephone\":\"" + telephone + "\"}"));
        System.out.println(requestResult);
        return requestResult;
    }
}
