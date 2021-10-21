package com.itheima.reggie_take_out.service;

import com.itheima.reggie_take_out.common.CommonReturn;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author UMP90
 * @date 2021/10/15
 */

public interface CommonService {
    CommonReturn<?> upload(MultipartFile multipartFile);

    void download(HttpServletResponse httpServletResponse, String name) throws IOException;
}
