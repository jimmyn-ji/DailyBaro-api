package com.project.hanfu.controller;

import com.project.hanfu.config.Constant;
import com.project.hanfu.config.HttpMsg;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.model.dto.AccountDTO;
import com.project.hanfu.model.dto.InsertUserDTO;
import com.project.hanfu.model.dto.UpdateUserInfoDTO;
import com.project.hanfu.model.vo.UserInfoVO;
import com.project.hanfu.result.ResultBase;
import com.project.hanfu.model.User;
import com.project.hanfu.result.ResultData;
import com.project.hanfu.service.UserService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户控制层
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/queryInfoByAccount")
    ResultData<UserInfoVO> queryInfoByAccount(@RequestParam("account") String account) {
//        从 URL 查询字符串中接收数据并转化为 JSON
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccount(account);
        return userService.queryInfoByAccount(accountDTO);
    }

    @RequestMapping("/find")
    ResultBase find(@RequestParam("page") int page, @RequestParam("searchKey") String searchKey) {
        ResultBase resultBase = new ResultBase();
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.find(searchKey);
        if (users == null) {
            return resultBase.setCode(StatusCode.SUCCESS);
        }
        List<User> items = users.size() >= page * Constant.PAGE_SIZE ?
                users.subList((page - 1) * Constant.PAGE_SIZE, page * Constant.PAGE_SIZE)
                : users.subList((page - 1) * Constant.PAGE_SIZE, users.size());
        int len = users.size() % Constant.PAGE_SIZE == 0 ? users.size() / Constant.PAGE_SIZE
                : (users.size() / Constant.PAGE_SIZE + 1);
        map.put("items", items);
        map.put("len", len);
        return resultBase.setCode(StatusCode.SUCCESS).setData(map);
    }

    /**
     * 用户注册接口
     * @param insertUserDTO
     * @return
     */
    @RequestMapping("/create")
    ResultData<UserInfoVO> register(@RequestBody InsertUserDTO insertUserDTO) {
        return userService.register(insertUserDTO);
    }


    /**
     * 更新用户信息
     * @param updateUserInfoDTO
     * @return
     */
    @RequestMapping("/update")
    public ResultData<UserInfoVO> update(@RequestBody UpdateUserInfoDTO updateUserInfoDTO){
        return userService.update(updateUserInfoDTO);
    }


    @DeleteMapping("/delete")
    ResultBase delete(@RequestParam("id") int id) {
        ResultBase resultBase = new ResultBase();
        int ans = userService.delete(id);
        if (ans == 1) {
            return resultBase.setCode(StatusCode.SUCCESS).setMessage(HttpMsg.DELETE_USER_OK);
        }
        return resultBase.setCode(StatusCode.ERROR).setMessage(HttpMsg.DELETE_USER_FAILED);
    }

}

