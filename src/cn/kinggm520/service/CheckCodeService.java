package cn.kinggm520.service;

import java.io.OutputStream;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-26 17:26
 * 验证码
 */
public interface CheckCodeService {

    /**
     * 生成验证码并返回
     * @param outputStream
     * @return
     */
    public String checkCodeService(OutputStream outputStream);


}
