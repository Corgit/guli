package com.guli.seckill.controller;

import com.guli.common.utils.R;
import com.guli.seckill.ao.SeckillAO;
import com.guli.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/17 13:41
 */
@Api(tags = "秒杀服务API")
@RequestMapping("/seckill")
@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("测试redis连接")
    @GetMapping("/get")
    public R testConnection() {
        redisTemplate.opsForValue().set("a", "b");
        Object res = redisTemplate.opsForValue().get("a");
        return R.ok().put("res", res);
    }

    @ApiOperation("登录接口-测试session")
    @GetMapping("/user/login")
    public R login(HttpServletRequest request) {
        // session
        HttpSession session = request.getSession();
        System.out.println("session >>>>>>>>>>" + session);
        if(session != null) {
            System.out.println(session.getAttribute("sessionId"));
        } else {
            session.setAttribute("sessionId", "sssss");
        }

        // cookies
        Cookie[] cookie = request.getCookies();
        System.out.println("cookie >>>>>>>>>>" + Arrays.toString(cookie));
        if(cookie != null) {
            for(int i = 0; i < cookie.length; i++) {
                cookie[i].setDomain("domain_" + i);
                cookie[i].setValue("value_" + i);
            }
        }
        return R.ok();
    }

    @ApiOperation("上架最近天的秒杀活动到redis")
    @GetMapping("/session/upload")
    public void uploadSeckillDetailB3days() {
        seckillService.upload3daySessions();
    }

    @ApiOperation("秒杀接口")
    @PostMapping("/kill")
    public R kill(@RequestBody SeckillAO ao) {
        if(ao != null) {
            HashMap<String, String> map = seckillService.kill(ao);
            if(map != null && map.get("result").equals("0")) {
                return R.ok().put("msg", map.get("msg"));
            }
        }
        return R.error().put("msg", "参数错误");
    }

}
