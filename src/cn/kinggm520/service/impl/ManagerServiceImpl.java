package cn.kinggm520.service.impl;

import cn.kinggm520.dao.ManagerDao;
import cn.kinggm520.dao.impl.ManagerDaoImpl;
import cn.kinggm520.domain.Manager;
import cn.kinggm520.service.ManagerService;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-28 22:25
 * 用户数据库操作实现类
 */
public class ManagerServiceImpl implements ManagerService {
    private ManagerDao managerDao = new ManagerDaoImpl();

    @Override
    public Manager login(Manager manager) {

        return managerDao.login(manager);
    }
}
