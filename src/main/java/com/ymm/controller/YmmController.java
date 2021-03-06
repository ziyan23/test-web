package com.ymm.controller;

/**
 * Created by gujie on 16/7/29.
 */

import com.ymm.model.YmmAction;
import com.ymm.request.GetverifycodeRequest;
import com.ymm.request.UpdateBalanceRequest;
import com.ymm.response.GetverifycodeResponse;
import com.ymm.response.UpdateBalanceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by gujie on 16/8/1.
 */

@Controller
@RequestMapping(value = "ymm")
public class YmmController {

    @RequestMapping(value = "/getverifycode", method = RequestMethod.POST)
    public @ResponseBody String YmmGetverifycodeController(GetverifycodeRequest getverifycodeRequest) throws IOException, ParseException {
        YmmAction ymmAction = new YmmAction();
        GetverifycodeResponse getverifycodeResponse = new GetverifycodeResponse();
        getverifycodeResponse.setVerifyCode(ymmAction.YmmGetverifycodeAction(getverifycodeRequest.getTelephoneNum()));
        String verifycode = getverifycodeResponse.getVerifyCode();
        return verifycode;
    }

//    @RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
//    public ModelAndView YmmUpdateBalanceController(UpdateBalanceRequest updateBalanceRequest) throws IOException, ParseException {
//        YmmAction ymmAction = new YmmAction();
//        UpdateBalanceResponse updateBalanceResponse = new UpdateBalanceResponse();
//        updateBalanceResponse.setAccoutBalance(ymmAction.YmmUpdateBalanceAction(updateBalanceRequest.getTelephoneNum(),updateBalanceRequest.getAccountBalance()));
//
//        ModelAndView mad = new ModelAndView("ymm");
//        mad.addObject("accoutBalance",updateBalanceResponse.getAccoutBalance());
//        return mad;
//    }

    @RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
    public @ResponseBody int YmmUpdateBalanceController(UpdateBalanceRequest updateBalanceRequest) throws IOException, ParseException {
        YmmAction ymmAction = new YmmAction();
        UpdateBalanceResponse updateBalanceResponse = new UpdateBalanceResponse();
        updateBalanceResponse.setAccountBalance(ymmAction.YmmUpdateBalanceAction(updateBalanceRequest.getTelephoneNum(), updateBalanceRequest.getAccountBalance()));
        int accountBalance = updateBalanceResponse.getAccountBalance();
        return accountBalance;
    }
}