package com.jsonschang.dao.dao;

import com.jsonschang.model.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * user(User)表数据库访问层
 *
 * @author JsonsChang
 * @since 2020-05-27 14:09:24
 */
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    User queryById(String uid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 影响行数
     */
    int deleteById(String uid);

    /**获取用户信息
     * @param empId 用户编号
     * @return
     */
    User getUserByEmpId(String empId);


}