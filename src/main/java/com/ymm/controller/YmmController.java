package com.ymm.controller;

/**
 * Created by gujie on 16/7/29.
 */

import com.ymm.model.YmmAction;
import com.ymm.request.GetverifycodeRequest;
import com.ymm.response.GetverifycodeResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by gujie on 16/8/1.
 */

@Controller
@RequestMapping(value = "/ymm")
public class YmmController {

    @RequestMapping(value = "/getverifycode", method = RequestMethod.POST)
    public ModelAndView YmmGetverifycodeController(GetverifycodeRequest getverifycodeRequest) throws IOException, ParseException {
        YmmAction ymmAction = new YmmAction();
        GetverifycodeResponse getverifycodeResponse = new GetverifycodeResponse();
        getverifycodeResponse.setVerifyCode(ymmAction.YmmGetverifycodeAction(getverifycodeRequest.getTelephoneNum()));

        ModelAndView mad = new ModelAndView("ymm");
        mad.addObject("verifycode",getverifycodeResponse.getVerifyCode());
        return mad;
    }
}