package cn.kinggm520.service;

import cn.kinggm520.domain.PageBean;
import cn.kinggm520.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-25 22:27
 * 用户管理业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 登录
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
     * 删除单个用户
     * @param id
     */
    public void deleteUser(int id);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    public User findUserById(String id);

    /**
     * 更新
     * @param user
     */
    public void updateUserById(User user);

    /**
     * 删除选中
     * @param id
     */
    public void delSelectedUser(String id[]);

    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String,String[]
            > condition);

}
