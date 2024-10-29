package com.icia.recipe.controller.mainRestController;

import com.icia.recipe.dto.mainDto.Member;
import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.service.mainService.MemberService;
import com.icia.recipe.service.mainService.PasswordRecoveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class PasswordRecoveryController {
    @Autowired
    MemberService mSer;
    private final PasswordRecoveryService passwordRecoveryService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/recover")
    public String recoverPassword(MemberDto mem) {
        String suc = passwordRecoveryService.PasswordRecovery(mem);
        log.info(">>>>>>>>>>>>" + suc);
        if (suc.equals("이메일 전송 완료")) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/checkcode")
    public boolean checkCode(Member member) {
        boolean checkCode = passwordRecoveryService.checkCode(member);
        log.info("checkCode:" + checkCode);
        return checkCode;
    }

}
