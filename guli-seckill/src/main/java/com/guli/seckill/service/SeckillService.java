package com.guli.seckill.service;

import com.guli.seckill.ao.SeckillAO;

import java.util.HashMap;
import java.util.Map;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/20 14:35
 */
public interface SeckillService {

    void upload3daySessions();

    HashMap<String, String> kill(SeckillAO ao);
}
