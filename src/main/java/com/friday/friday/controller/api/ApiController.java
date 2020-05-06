package com.friday.friday.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"${api-url}"})
public class ApiController{

    @RequestMapping(value = {"/getPage"})
    @ApiOperation(value = "返回具体页面",notes = "根据url返回具体页面")
    @ApiImplicitParams({@ApiImplicitParam(name = "modelAndView",value = "传入一个modelAndView对象",required = false)
                        ,@ApiImplicitParam(name = "pageName",value = "具体页面名称",required = false)})
    public ModelAndView getPage(ModelAndView modelAndView,String pageName){
        modelAndView.setViewName(pageName);
        return modelAndView;

    }
}
