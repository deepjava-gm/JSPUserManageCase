package cn.kinggm520.service;

import cn.kinggm520.domain.Manager;
import cn.kinggm520.domain.User;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-28 22:23
 * 管理员 管理业务接口
 */
public interface ManagerService {
    /**
     * 登录
     * @param manager
     * @return
     */
    public Manager login(Manager manager);

}
