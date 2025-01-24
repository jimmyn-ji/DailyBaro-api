package com.project.hanfu.util;


import com.project.hanfu.model.LoginForm;
import tk.mybatis.mapper.util.StringUtil;

public class VerifyUtil {

    public static boolean verifyLoginForm(LoginForm form) {
        if (form==null || StringUtil.isEmpty(form.getAccount())|| StringUtil.isEmpty(form.getPassword())|| StringUtil.isEmpty(form.getRole())) {
            return false;
        }
        if (form.getRole().equals("user") || form.getRole().equals("admin")){
            return true;
        }
        return false;
    }


}
