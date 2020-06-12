package com.jsonschang.service.service;

import com.jsonschang.common.exception.UserExistException;
import com.jsonschang.common.util.Pbkdf2Encryption;
import com.jsonschang.common.util.UUIDUtil;
import com.jsonschang.dao.dao.MenuDao;
import com.jsonschang.dao.dao.RoleDao;
import com.jsonschang.dao.dao.UserDao;
import com.jsonschang.model.entity.Menu;
import com.jsonschang.model.entity.Role;
import com.jsonschang.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * user(User)表服务实现类
 *
 * @author JsonsChang
 * @since 2020-05-27 14:09:45
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private MenuDao menuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    @Override
    public User queryById(String uid) {
        User user = userDao.queryById(uid);
        List<Role> userRole = roleDao.getUserRole(user.getEmpId());
        user.setRoles(userRole);
        return user;
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User insert(User user) {
        //查询用户是否已经存在
        User userByEmpId = userDao.getUserByEmpId(user.getEmpId());
        if(userByEmpId!=null){
            throw new UserExistException("用户已经存在");
        }
        String getuuid = UUIDUtil.getuuid();
        String password = user.getPassword();
        String sha1 = Pbkdf2Encryption.sha1(password, "zhangqiyang");
        user.setPassword(sha1);
        user.setUid(getuuid);
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getUid());
    }

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(String uid) {
        return this.userDao.deleteById(uid) > 0;
    }



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByEmpId(s);
        List<Role> userRole = roleDao.getUserRole(s);
        List<Menu> userMenu = menuDao.getUserMenu(s);
        user.setMenus(userMenu);
        user.setRoles(userRole);
        return user;
    }
}