package com.lt.study.archive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * User: luotao
 * Date: 14-1-2
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("user")
@Controller
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
    @Value("${cookieName}")
    private String cookieName;

    @Value("${cookieDomain}")
    private String cookieDomain;

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        //从客户端得到所有的cookies信息
        Cookie[] allCookies = request.getCookies();
        logger.info("xxxx begin to exit");
        if (null!=allCookies){
            for (Cookie cookie:allCookies){
                if(cookie.getName().trim().equals(cookieName.trim())){
                    Cookie cookie1=new Cookie(cookie.getName(),null);
                    cookie1.setMaxAge(-1);
                    cookie1.setDomain(".hi.com");
                    cookie1.setPath("/");
                    logger.info(" exit");
                    response.addCookie(cookie1);
                    break;
                }
            }
        }
        return "index";
    }
}
