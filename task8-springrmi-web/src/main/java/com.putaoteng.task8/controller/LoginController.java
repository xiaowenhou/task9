package com.putaoteng.task8.controller;

import com.putaoteng.task8.model.User;
import com.putaoteng.task8.server.UserDaoServiceRemote;
import com.putaoteng.task8.server.VerificationDaoServiceRemote;
import com.putaoteng.task8.utils.authentication.AutoAuthenticate;
import com.putaoteng.task8.utils.authentication.CookieUtils;
import com.putaoteng.task8.utils.authentication.MD5Encryption;
import com.putaoteng.task8.utils.other.SendMailContent;
import com.putaoteng.task8.utils.other.SendMailTemplate;
import com.putaoteng.task8.utils.other.SendShortMessage;
import com.putaoteng.task8.utils.remote.CallService;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class.getName());

    private CallService callService = new CallService();
    //登陆用户
    private UserDaoServiceRemote userDaoServiceRemote;
    //阿里云短信
    @Resource(name = "aliSMS")
    private SendShortMessage aliSMS;
    //邮件模版
    @Resource(name = "sendMailTemplate")
    private SendMailTemplate sendMailTemplate;
    //邮件自定义内容
    @Resource(name = "sendMailContent")
    private SendMailContent sendMailContent;

    @RequestMapping(value = "/login/0", method = RequestMethod.POST)
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model, String username,
                            String password) throws NoSuchAlgorithmException, ServletException, IOException {
        /*try{
            this.userDaoServiceRemote = callService.getUserDaoServiceRemote();

        } catch(Exception  e){
            callService.getBeanAgain();
            this.userDaoServiceRemote = callService.getUserDaoServiceRemote();
        }*/

        callService.getBeanAgain();
        this.userDaoServiceRemote = callService.getUserDaoServiceRemote();

        // 根据用户名在数据库中查找数据
        User user = (User) userDaoServiceRemote.findByUsername(MD5Encryption.EncoderByMd5(username));
        // 如果不为空,再判断输入的密码和数据库中的密码是否相同
        if (user != null) {
            // 如果匹配则判断密码是否相同,相同则跳回到原来的页面,否则就再判断一次
            if (user.getPassword().equals(MD5Encryption.EncoderByMd5(password))) {
                // 更新登录时间
                user.setLoginAt(System.currentTimeMillis());
                userDaoServiceRemote.update(user);
                AutoAuthenticate.cookieAuthenticate(response, username, user.getLoginAt() + "");

                return "forward:" + "/index";
            }
            // 提示密码不正确
            request.getSession().setAttribute("info", "密码错误,请重新输入");
            request.getSession().setMaxInactiveInterval(1);
            return "forward:" + "/login";
        }
        // 提示未注册
        request.getSession().setAttribute("info", "账户不存在,请先注册");
        request.getSession().setMaxInactiveInterval(1);
        return "forward:" + "/login";
    }

    @RequestMapping(value = "/login/1", method = RequestMethod.GET)
    public String registrationPage(HttpServletRequest request) {
        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/u/loginout", method = RequestMethod.GET)
    public String logoutTest(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 删除cookie
        CookieUtils.deleteCookieByName(request, response, "token");

        return "bye";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationTest(HttpServletRequest request, HttpServletResponse response, Model model, User user,
                                   String codeNumber) throws NoSuchAlgorithmException, ServletException, IOException {
       /* try{
            this.userDaoServiceRemote = callService.getUserDaoServiceRemote();
        } catch(Exception  e){

        }*/

        callService.getBeanAgain();
        this.userDaoServiceRemote = callService.getUserDaoServiceRemote();

        String encryUsername = MD5Encryption.EncoderByMd5(user.getUsername());
        String encryPassword = MD5Encryption.EncoderByMd5(user.getPassword());
        User userTest = (User) userDaoServiceRemote.findByUsername(encryUsername);
        if (userTest != null) {
            model.addAttribute("info", "该用户已存在,请重新注册...");
            return "registration";
        }

        String code = (String) request.getSession().getAttribute("code");
        String emailVerification = (String) request.getSession().getAttribute("emailVerification");
        if (emailVerification == null || !emailVerification.equals("Yes")) {
            model.addAttribute("info", "请先验证邮箱...");
            return "registration";
        }

        if (code == null || !codeNumber.equals(code)) {
            model.addAttribute("info", "验证码错误,请重试...");
            return "registration";
        }

        user.setUsername(encryUsername);
        user.setPassword(encryPassword);
        user.setCreateAt(System.currentTimeMillis());
        user.setLoginAt(System.currentTimeMillis());
        userDaoServiceRemote.save(user);

        // 注册成功添加自动认证
        AutoAuthenticate.cookieAuthenticate(response, user.getUsername(), user.getLoginAt() + "");
        // sendMailTemplate.sendTemplate(user.getEmail(), temp);

        return "welcome";
    }

    @RequestMapping(value = "/SMS")
    public String sendSMS(HttpServletRequest request, String phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(String.valueOf(random.nextInt(10)));
        }
        String code = stringBuilder.toString();

        if (phoneNumber != null) {
            // 发送短信
            boolean result = aliSMS.sendSMS(phoneNumber, code);
            if (!result) {
                return "failed";
            }
            request.getSession().setAttribute("code", code);
        }

        return "success";
    }

    @RequestMapping(value = "/email")
    public String sendMail(HttpServletRequest request, String email)
            throws UnsupportedEncodingException, MessagingException {

        if (email != null) {
            // 发送email
            try {
                boolean result = sendMailContent.sendCommon(email);
                if (!result) {
                    return "failed";
                }
            } catch (ClientProtocolException e) {
                logger.error("LoginController.sendMail(),客户端协议异常: " + e.getMessage());
                return "failed";
            } catch (IOException e) {
                logger.error("LoginController.sendMail(),输入输出异常: " + e.getMessage());
                return "failed";
            }

            request.getSession().setAttribute("emailVerification", "No");

        }
        return "success";
    }

    @RequestMapping(value = "/verification")
    public String verification(HttpServletRequest request) {
        String emailVerification = (String) request.getSession().getAttribute("emailVerification");
        if (emailVerification != null && emailVerification.equals("No")) {
            request.getSession().setAttribute("emailVerification", "Yes");
            return "verification";
        } else {
            return "failed";
        }
    }
}
