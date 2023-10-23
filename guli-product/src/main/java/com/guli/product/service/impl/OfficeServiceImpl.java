package com.guli.product.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.product.dao.SpuInfoDao;
import com.guli.product.entity.SpuInfoEntity;
import com.guli.product.service.OfficeService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Ryan_Wuyx
 * @Date: 2023/10/12 10:25
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements OfficeService {

    @Override
    public void uploadSpuInfoByExcel() {
        // 默认是按行读取，每行按下标次序封装为一个list；ExcelReader的ignoreEmptyRow默认为true，即不读取空行
        // 如果不指定读取的sheet，默认读取第一个sheet
        long start = System.currentTimeMillis();
        ExcelReader excelReader = ExcelUtil.getReader(FileUtil.file("template/spu_info.xls"), 1);
        List<List<Object>> list = excelReader.read();
        System.out.println("用时 >>>>>>> " + (System.currentTimeMillis() - start));
        System.out.println("Excel行数 >>>>>> " + list.size());

        List<SpuInfoEntity> readeAll = excelReader.readAll(SpuInfoEntity.class);
        if(readeAll.size() > 0) {
            for (SpuInfoEntity entity : readeAll) {
                entity.setCreateTime(new Date());
                entity.setUpdateTime(new Date());
            }
            this.saveBatch(readeAll);
        }
    }

    @Override
    public void downloadSpu(HttpServletResponse response) {

        // 1.创建writer对象
        ExcelWriter writer = ExcelUtil.getWriter();

        // 2.通过bean写入Excel
        writer.passCurrentRow(); // 跳过第一行，也可不跳
        // 2.1创建Excel标题行，第一个参数是合并到哪一列，从0开始；第二个参数是合并后单元格存放的内容
        writer.merge(5, "商品属性列表");
        // 2.2自定义单元格别名，通过bean写入时会按照自定义别名顺序排列
        writer.addHeaderAlias("id", "属性编号"); // bigint
        writer.addHeaderAlias("spuName", "属性名称"); // cn+en string
        writer.addHeaderAlias("spuDescription", "属性描述"); // 转义字符
        writer.addHeaderAlias("weight", "重量"); // decimal float
        writer.addHeaderAlias("publishStatus", "上架状态");
        writer.addHeaderAlias("createTime", "创建日期"); // datetime null
        writer.setOnlyAlias(true); // true表示只输出设置了alias的字段
        // 2.3所有行都必须封装在list内写入，mybatis中使用limit时一般通过Mapper的SQL去实现
        List<SpuInfoEntity> list = this.list();

        // 3.写出Excel(内存中的Workbook对象)
        writer.write(list, true);

        // 4.关闭writer对象（写出到文件），header中文件名暂不支持中文名，中文需要自行编码
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test1.xls");
        System.out.println("response header >>>>>>>> " + response.getHeaderNames());
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            writer.close();
            IoUtil.close(out);
        }
    }

}
