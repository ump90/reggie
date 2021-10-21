package com.itheima.reggie_take_out.service.impl;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.service.CommonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Value("${reggie.path}")
    private String storagePath;

    @Override
    public CommonReturn<?> upload(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;
        try {
            multipartFile.transferTo(new File(storagePath + newFileName));
        } catch (IOException e) {
            return CommonReturn.error("服务器图片不能写入");
        }
        return CommonReturn.success(newFileName);

    }

    @Override
    public void download(HttpServletResponse httpServletResponse, String name) throws IOException {
        String filePath = storagePath + name;
        String extension = name.substring(name.lastIndexOf("."));
        File file = new File(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("image//" + extension);
        byte[] bytes = new byte[8196];
        int len = 0;
        while ((len = bufferedInputStream.read(bytes)) > 0) {
            servletOutputStream.write(bytes, 0, len);
        }
        servletOutputStream.close();
        bufferedInputStream.close();
    }
}
