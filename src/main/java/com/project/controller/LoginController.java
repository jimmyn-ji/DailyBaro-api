package com.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.mapper.UserMapper;
import com.project.model.User;
import com.project.model.dto.UserPwDTO;
import com.project.model.dto.UserRegisterDTO;
import com.project.model.dto.WxLoginDTO;
import com.project.model.vo.UserInfoVO;
import com.project.util.Result;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;


    @PostMapping("/doLogin")
    public Result<UserInfoVO> doLogin(@RequestBody UserPwDTO userPwDTO) {
        User user = userMapper.selectByAccount(userPwDTO.getAccount());

        UserInfoVO userInfo = new UserInfoVO();

        if (user == null) {
            return Result.fail("账号不存在");
        }
        if (user.getIsdelete() != null && user.getIsdelete() == 1) {
            return Result.fail("该账号已被注销，无法登录");
        }
        if (user != null && passwordEncoder.matches(userPwDTO.getPassword(), user.getPassword())) {
            userInfo.setUid(user.getUid());
            userInfo.setAccount(user.getAccount());

        }
        return Result.success(userInfo);
    }

    @PostMapping("/doRegister")
    public Result<UserInfoVO> doRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        User existingUser = userMapper.selectByAccount(userRegisterDTO.getAccount());
        if (existingUser != null) {
            return Result.fail("账号已存在");
        }

        User newUser = new User();
        newUser.setAccount(userRegisterDTO.getAccount());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        newUser.setStatus(0);
        newUser.setIsdelete(0);

        userMapper.insertUser(newUser);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setUid(newUser.getUid());
        userInfo.setAccount(newUser.getAccount());
        return Result.success(userInfo);
    }

    @PostMapping("/wxLogin")
    public Result<UserInfoVO> wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        // 1. 创建HTTP客户端
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 2. 调用微信API获取session_key
            String url = String.format(
                    "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    appId, appSecret, wxLoginDTO.getCode()
            );

            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject sessionObj = JSON.parseObject(responseBody);

                // 3. 验证微信接口返回
                if (!sessionObj.containsKey("session_key") || !sessionObj.containsKey("openid")) {
                    return Result.fail("微信登录失败: " + sessionObj.getString("errmsg"));
                }

                String sessionKey = sessionObj.getString("session_key");
                String openid = sessionObj.getString("openid");

                // 4. 解密手机号
                JSONObject phoneObj = decryptPhoneNumber(
                        wxLoginDTO.getEncryptedData(),
                        wxLoginDTO.getIv(),
                        sessionKey
                );
                String phoneNumber = phoneObj.getString("purePhoneNumber");

                // 5. 处理用户信息
                User user = userMapper.selectByWxOpenid(openid);
                if (user == null) {
                    user = new User();
                    user.setWxOpenid(openid);
                    user.setPhone(phoneNumber);
                    user.setStatus(0);
                    user.setIsdelete(0);
                    userMapper.insertUser(user);
                }

                // 6. 返回用户信息
                UserInfoVO userInfo = new UserInfoVO();
                userInfo.setUid(user.getUid());
                userInfo.setAccount(user.getPhone());
                return Result.success(userInfo);
            }
        } catch (Exception e) {
            return Result.fail("微信登录失败: " + e.getMessage());
        }
    }

    private JSONObject decryptPhoneNumber(String encryptedData, String iv, String sessionKey) throws Exception {
        byte[] data = Base64.getDecoder().decode(encryptedData);
        byte[] key = Base64.getDecoder().decode(sessionKey);
        byte[] ivData = Base64.getDecoder().decode(iv);

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivData));

        byte[] original = cipher.doFinal(data);
        return JSON.parseObject(new String(original, StandardCharsets.UTF_8));
    }
}