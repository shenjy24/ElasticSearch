package com.shenjy.controller;

import com.shenjy.base.JsonResult;
import com.shenjy.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2017/12/15
 */
@RestController
@RequestMapping("/es/account")
public class AccountController extends BaseController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/query")
    public JsonResult query(String content) {
        return success(accountService.find(content));
    }

    @RequestMapping("/queryAll")
    public JsonResult queryAll(String content) {
        return success(accountService.findAll(content));
    }

    @RequestMapping("/sumAge")
    public JsonResult sumAge(String content) {
        return success(accountService.sumAge(content));
    }

    @RequestMapping("/save")
    public JsonResult save(String id, String name, String motto, Integer age) {
        return success(accountService.saveAccount(id, name, motto, age));
    }
}
