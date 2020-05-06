package com.friday.friday.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DruidStatController {


    @GetMapping("/druid/stat")
    @ApiOperation(value = "数据源监测配置",notes = "通过Druid查看数据源相关配置")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataList
        // 该方法可以获取所有数据源的监控数据，
        // 除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}