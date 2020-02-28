package cn.kinggm520.dao;

import cn.kinggm520.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-25 22:35
 * 用户数据库操作 DAO接口
 */
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll();

    /**
     * 验证登录
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 添加
     * @param user
     */
    public void addUser(User user);

    /**
     * 删除单个
     * @param id
     */
    public void deleteUser(int id);

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    public User findUserById(int id);

    /**
     * 通过id更改用户
     * @param user
     */
    public void updateUserById(User user);

    /**
     * 查询数据总数+多条件查询
     * @param condition
     * @return
     */
    public int findTotalCount(Map<String,String[]> condition);

    /**
     * 分页查询+多条件查询
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    public List<User> findByPage(int  start,int rows,Map<String,String[]> condition);

}
