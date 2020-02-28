package cn.kinggm520.dao.impl;

import cn.kinggm520.dao.UserDao;
import cn.kinggm520.domain.User;
import cn.kinggm520.util.JDBCUtils;
import com.alibaba.druid.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-25 22:38
 * 用户数据库操作实现类
 */
public class UserDaoImpl implements UserDao {

    //获取JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<User> findAll() {
//        使用JDBC操作数据库
        String sql = "select * from t_user";

        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class)); //加上<User>向下兼容

        return users;
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User currentUser = null;
        String sql = "select * from t_user where username=? and password=?";

        try {
            currentUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());

        } catch (Exception e) {
//            打印日志
            e.printStackTrace();
        }
        return currentUser;

    }

    /**
     * 添加
     *
     * @param user
     */
    @Override
    public void addUser(User user) {
        String sql = "insert into t_user (name,gender,age,address,email) values (?,?,?,?,?)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getEmail());

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        String sql = "delete from t_user where id=?";
        template.update(sql, id);
    }

    /**
     * 通过id查找用户
     *
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        User currentUser = null;
        String sql = "select * from t_user where id=?";
        try {
            currentUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        } catch (Exception e) {
//            写日志
        }

        return currentUser;
    }

    /**
     * 通过id修改用户
     *
     * @param user
     */
    @Override
    public void updateUserById(User user) {
        String sql = "update t_user set  name=? , gender=? , age=? ,address=?, email=? where id=?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getEmail(), user.getId());

    }

    /**
     * 查询所有数据总数+多条件查询
     *
     * @param condition
     * @return
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        int totalCount = 0;
        String sql = "select count(*) from t_user where 1=1";
        StringBuilder sb = new StringBuilder(sql);
//        遍历map
        Set<String> set = condition.keySet();
        ArrayList<Object> params = new ArrayList<>();
        for (String s : set) {
//            过滤掉分页数据
            if ("currentPage".equals(s) || "rows".equals(s)) {
                continue;
            }

//            获取查询条件值
            String value = condition.get(s)[0];
            if (!StringUtils.isEmpty(value)) {
                sb.append(" and " + s + " like ? ");
                params.add("%" + value + "%"); //模糊查询条件

            }

        }


        try {
            totalCount = template.queryForObject(sb.toString(), Integer.class, params.toArray());
        } catch (Exception e) {

        }
        return totalCount;
    }

    /**
     * 分页查询+多条件查询
     *
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        List<User> list = null;
        String sql = "select * from t_user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

//        遍历map
        Set<String> set = condition.keySet();
        ArrayList<Object> params = new ArrayList<>();
        for (String s : set) {
            if ("currentPage".equals(s) || "rows".equals(s)) {
                continue;
            }

//            获取查询条件值
            String value = condition.get(s)[0];
            if (!StringUtils.isEmpty(value)) {
                sb.append(" and " + s + " like ? ");
                params.add("%" + value + "%"); //模糊查询条件

            }

        }
//   添加分页条件
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);

        sql = sb.toString();

        try {
            list = template.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());
        } catch (Exception e) {

        }

        return list;
    }

}
