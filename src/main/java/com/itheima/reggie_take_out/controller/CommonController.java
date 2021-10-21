package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author UMP90
 * @date 2021/10/14
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    public CommonReturn<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        return commonService.upload(multipartFile);
    }

    @GetMapping("/download")
    public void download(HttpServletResponse httpServletResponse, @RequestParam String name) throws IOException {
        commonService.download(httpServletResponse, name);
    }
}
