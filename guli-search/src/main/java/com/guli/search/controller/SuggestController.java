package com.guli.search.controller;

import com.guli.common.utils.R;
import com.guli.search.service.SuggestService;
import com.guli.search.vo.EsAddrVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("将ES中地址数据一次性存入MySQL")
    @GetMapping("/history")
    public R historyUpdate() {
        try{
            suggestService.historyBatchUpdate();
        }catch(Exception e){
            log.info(e.toString());
        }
        return null;
    }

    @ApiOperation("将MySQL中地址数据一次性存入ES")
    @PostMapping("/es/to/batch")
    public R toEsBatch(@RequestBody List<EsAddrVo> vos) {
        if(vos != null && vos.size() > 0) {
            suggestService.batchToEs(vos);
        }
        return null;
    }

}