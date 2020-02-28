package cn.kinggm520.service.impl;

import cn.kinggm520.dao.UserDao;
import cn.kinggm520.dao.impl.UserDaoImpl;
import cn.kinggm520.domain.PageBean;
import cn.kinggm520.domain.User;
import cn.kinggm520.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-25 22:30
 * 实现UserService
 */
public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<User> findAll() {
//        调用dao 完成查询
        return dao.findAll();

    }

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return dao.login(user);
    }

    /**
     * 添加
     * @param user
     */
    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    /**
     * 删除单个
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        dao.deleteUser(id);
    }

    /**
     * 通过id查找
     * @param id
     * @return
     */
    @Override
    public User findUserById(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }

    /**
     * 通过id更新
     * @param user
     */
    @Override
    public void updateUserById(User user) {
        dao.updateUserById(user);
    }

    /**
     * 删除选中
     * @param id
     */
    @Override
    public void delSelectedUser(String[] id) {
        for (String s : id) {
            dao.deleteUser(Integer.parseInt(s));
        }
    }


    /**
     * 分页+多条件查询
     * @param s_currentPage
     * @param s_rows
     * @param condition
     * @return  分页对象
     */
    public PageBean<User> findUserByPage(String s_currentPage, String s_rows, Map<String, String[]> condition) {

//        处理currentPage和rows : 非数字类型字符串 和 null. 比如: a  hello 问题
        int currentPage = 1; //2 a  -1
        try {
            // 处理非 数字类型 字符串 和 null. 比如: a  hello
            currentPage = Integer.parseInt(s_currentPage);
            if (currentPage < 1) {
                // 处理数字小于 1
                currentPage = 1;
            }
        } catch (NumberFormatException e) {
        }

        int rows = 5;
        try {
            // 处理非 数字类型 字符串. 比如: a  hello
            rows = Integer.parseInt(s_rows);
            if (rows < 5) {
                // 处理数字小于 5
                rows = 5;
            }
        } catch (NumberFormatException e) {
        }

        PageBean<User> pb = new PageBean<>();
//        设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

//        查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

//     查询数据 返回集合
//        计算开始索引
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start, rows, condition);
        pb.setList(list);

//        计算总页码 并设置
//        int totalPage = totalCount%rows ==0? totalCount/rows:totalCount/rows+1;

        int totalPage = (int) Math.ceil(totalCount * 1.0 / rows);
        pb.setTotalPage(totalPage);
        return pb;
    }


}
