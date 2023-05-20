package com.guli.coupon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.coupon.ao.SeckillQueryAO;
import com.guli.coupon.entity.SeckillSessionEntity;
import com.guli.coupon.vo.SeckillSkuVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @describe：
 * @author: Ryan_Wu
 * @Date: 2022/3/15 15:40
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    Boolean insertSeckillSession(List<SeckillSessionEntity> list);

    Boolean deleteSeckillSession(List<SeckillSessionEntity> list);

    Page<SeckillSessionEntity> querySeckillSessionInfo(SeckillQueryAO ao);

    Page<SeckillSessionEntity> querySeckillSessionInfoWithAnnotation(SeckillQueryAO ao);

    List<SeckillSkuVO> querySeckillDetail(String startTime, String endTime);

    List<SeckillSkuVO> querySeckillDetailTo(String startTime, String endTime);

    SeckillSessionEntity getone();
}
