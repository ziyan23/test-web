package com.ymm.model;

import com.ymm.auth.URLEnum;
import com.ymm.core.BaseURL;
import com.ymm.core.JSONDecoder;
import com.ymm.db.YmmDataBase;
import com.ymm.http.HttpClient;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by gujie on 16/8/1.
 */
public class YmmAction extends BaseURL {

    private String baseUrl = "";

    public YmmAction() {
        this.baseUrl = getBaseUrl();
    }

    public String getBaseUrl() {
        if (this.env.equals("alpha")) {
            return URLEnum.ALPHASHOPCENTER.url;
        } else if (this.env.equals("prod")) {
            return URLEnum.PRODSHOPCENTER.url;
        } else {
            return URLEnum.BETASHOPCENTER.url;
        }
    }
    // 发送登录请求
    public String YmmGetverifycodeAction(String telephoneNun) throws ParseException, IOException {

        this.baseUrl = getBaseUrl();
        //登录大后台
        String loginResult = HttpClient.toString(HttpClient.postJson(this.baseUrl + "new_boss/permission/login",
                "{\"telephone\":\"13311111111\",\"password\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\",\"version\":0,\"loginFrom\":0}"));
        //获取验证码
        String requestResult = HttpClient.toString(HttpClient.postJson(this.baseUrl + "new_boss/users/getverifycode",
                "{\"telephone\":\""+telephoneNun+"\"}"));

        JSONDecoder jsonDecoder = new JSONDecoder();
        String codeResult = jsonDecoder.toJsonObj(requestResult).get("info").getJsonString();

        return codeResult;
    }

    // 更新用户余额
    public int YmmUpdateBalanceAction(String telephoneNun,int accoutBalance) throws ParseException, IOException {

        YmmDataBase ymmDataBase = getYmmDataBase();
        ymmDataBase.execute("update accounts a inner join users u on a.user_id = u.user_id set a.account_balance = "+accoutBalance+" where u.telephone = "+telephoneNun+";");
        Map<String, Object> queryList = ymmDataBase.query("select a.account_balance from accounts a inner join users u on a.user_id = u.user_id where u.telephone = "+telephoneNun+";");
        int accountBalance = Integer.parseInt(queryList.get("account_balance").toString());
        return accountBalance;
    }

    public static YmmDataBase getYmmDataBase() {
        return new YmmDataBase("logistics");
    }
}
