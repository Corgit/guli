package com.guli.orders.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guli.orders.entity.OrderSettingEntity;
import com.guli.orders.service.OrderSettingService;
import com.guli.common.utils.PageUtils;
import com.guli.common.utils.R;



/**
 * 订单配置信息
 *
 * @author Ryan
 * @email wx_unicorn@163.com
 * @date 2021-09-08 12:01:44
 */
@RestController
@RequestMapping("orders/ordersetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("orders:ordersetting:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderSettingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("orders:ordersetting:info")
    public R info(@PathVariable("id") Long id){
		OrderSettingEntity orderSetting = orderSettingService.getById(id);

        return R.ok().put("orderSetting", orderSetting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("orders:ordersetting:save")
    public R save(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.save(orderSetting);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("orders:ordersetting:update")
    public R update(@RequestBody OrderSettingEntity orderSetting){
		orderSettingService.updateById(orderSetting);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("orders:ordersetting:delete")
    public R delete(@RequestBody Long[] ids){
		orderSettingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
