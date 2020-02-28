package cn.kinggm520.service.impl;

import cn.kinggm520.service.CheckCodeService;
import cn.kinggm520.util.IdentifyCode;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-26 17:26
 * 验证码
 */
public class CheckCodeServiceImpl implements CheckCodeService {
    /**
     * 生成验证码到页面 并返回字符串形式
     * @param outputStream
     * @return
     */
    @Override
    public String checkCodeService(OutputStream outputStream) {
        IdentifyCode identifyCode = new IdentifyCode();
        try {
            identifyCode.write(outputStream);
        } catch (IOException e) {
            //写日志
        }
        return identifyCode.getCode();
    }
}
