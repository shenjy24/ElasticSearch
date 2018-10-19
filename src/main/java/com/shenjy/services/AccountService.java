package com.shenjy.services;

import com.shenjy.entities.Account;

import java.util.List;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2017/12/15
 */
public interface AccountService {
    /**
     * 查询(使用repository查询)
     * @param content
     * @return
     */
    List<Account> find(String content);

    /**
     * 模板查询
     * @param content
     * @return
     */
    List<Account> findAll(String content);

    /**
     * 年龄求和
     * @param content
     * @return
     */
    Double sumAge(String content);
}

