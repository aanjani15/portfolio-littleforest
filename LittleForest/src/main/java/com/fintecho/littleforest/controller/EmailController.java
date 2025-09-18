package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.service.EmailService;
import com.fintecho.littleforest.vo.UserVO;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sign-up/emailCheck")
    @ResponseBody
    public String emailCheck(@RequestBody UserVO userVO) throws Exception {
        return emailService.sendEmail(userVO.getEmail());
    }

}
