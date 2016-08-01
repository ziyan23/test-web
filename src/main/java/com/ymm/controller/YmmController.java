package com.ymm.controller;

/**
 * Created by gujie on 16/7/29.
 */
import com.ymm.model.YmmAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by gujie on 16/8/1.
 */
@Controller
public class YmmController {

    @RequestMapping(value = "/getverifycode", method = RequestMethod.POST)
    public String YmmGetverifycodeController() throws IOException, ParseException {
        YmmAction ymmAction = new YmmAction();
        String response = ymmAction.YmmGetverifycodeAction("13111111113");
        return response;
    }
}