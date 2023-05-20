package com.guli.seckill.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/24 16:11
 */
@Component
public class SeckillInterceptor implements HandlerInterceptor {

    private String session;

    public String getSession() {
        return this.session;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 提取cookie
        Cookie[] cookie = request.getCookies();
        System.out.println("cookies >>>>>" + Arrays.toString(cookie));
        if(cookie != null && cookie.length > 0) {
            for(int i = 0;i <= cookie.length; i++) {
                String str = cookie[0].getName();
                System.out.println("cookie" + "_" + i + ":" + "Name >>>>>>>>>>" + str);
                String val = cookie[0].getValue();
                System.out.println("cookie" + "_" + i + ":" + "Value >>>>>>>>>>" + val);
                this.session = val;
                String domain = cookie[0].getDomain();
                System.out.println("cookie" + "_" + i + ":" + "Domain >>>>>>>>>>" + domain);
            }

            // 校验cookie
            if(!cookie[0].getName().equals("GULI")) {
                // 响应：
                // 如果cookie校验失败，则会重定向，重定向的页面仍然会发起一次HTTP，
                // 如果配置重定向的页面也被拦截，则会陷入重定向循环，直到重定向次数达到上限，
                // 因此重定向的页面应该配置不要被拦截
                response.sendRedirect("/seckill/user/login");
                return false;
            }
            return true;
        }

        response.sendRedirect("/seckill/user/login");
        return false;
    }

}
