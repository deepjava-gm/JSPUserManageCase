package cn.kinggm520.dao;

import cn.kinggm520.domain.Manager;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-28 22:16
 * 管理员数据库操作 DAO接口
 */
public interface ManagerDao {
    /**
     * 验证登录
     * @param user
     * @return
     */
    public Manager login(Manager user);
}
