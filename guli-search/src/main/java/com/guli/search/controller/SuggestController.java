package com.guli.search.controller;

import com.guli.common.utils.R;
import com.guli.search.service.SuggestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/5 16:53
 */
@Slf4j
@Api(tags = "自动补全接口")
@RequestMapping("/search/suggest")
@RestController
public class SuggestController {

    @Autowired
    private SuggestService suggestService;

    @GetMapping("/history")
    public R historyUpdate() {
        try{
            suggestService.historyBatchUpdate();
        }catch(Exception e){
            log.info(e.toString());
        }

        return null;
    }


}
