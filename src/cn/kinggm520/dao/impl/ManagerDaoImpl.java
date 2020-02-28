package cn.kinggm520.dao.impl;

import cn.kinggm520.dao.ManagerDao;
import cn.kinggm520.domain.Manager;
import cn.kinggm520.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-28 22:17
 * 管理员数据库操作实现类
 */
public class ManagerDaoImpl implements ManagerDao {

    //获取JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Manager login(Manager manager) {
        Manager currentManager = null;
        String sql = "select * from t_manager where username=? and password=?";

        try {
            currentManager = template.queryForObject(sql, new BeanPropertyRowMapper<Manager>(Manager.class), manager.getUsername(), manager.getPassword());

        } catch (Exception e) {
//            打印日志
            e.printStackTrace();
        }
        return currentManager;

    }
}
