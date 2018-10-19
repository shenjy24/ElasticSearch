package com.shenjy.controllers;

import com.shenjy.base.CodeStatus;
import com.shenjy.base.JsonResult;
import com.shenjy.base.SystemCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ResponseBody
public class BaseController {

    @ExceptionHandler(Exception.class)
    public JsonResult handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return response(SystemCode.HANDLE_EXCEPTION, null);
    }

    protected JsonResult success(Object data) {
        return new JsonResult(SystemCode.SUCCESS, data);
    }

    protected JsonResult error(CodeStatus codeStatus) {
        return new JsonResult(codeStatus, null);
    }

    protected JsonResult error(String code, String message) {
        return new JsonResult(code, message, null);
    }

    protected JsonResult response(CodeStatus codeStatus, Object data) {
        return new JsonResult(codeStatus, data);
    }
}
