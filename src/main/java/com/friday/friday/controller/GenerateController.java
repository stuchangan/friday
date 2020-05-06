package com.friday.friday.controller;

import com.friday.friday.dto.GenerateDetail;
import com.friday.friday.service.GenerateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/generate"})
public class GenerateController {

    @Autowired
    private GenerateService generateService;

    @GetMapping(params = {"tableName"})
    @ApiOperation("根据表名显示表信息")
    public GenerateDetail generateByTableName(String tabelName){
        GenerateDetail generateDetail = new GenerateDetail();
        //generateDetail.setBeanName();
        return generateDetail;

    }
}
