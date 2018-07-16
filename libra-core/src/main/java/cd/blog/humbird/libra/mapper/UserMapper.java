package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by david on 2018/7/16.
 */
public interface UserMapper {

    List<User> findAll();

    long count(User criteria, PageInfo<User> page);

    User findById(long id);

    User findByName(String name);

    User findByNameOrLoginNameLike(String name, boolean includeAdmin);

    List<User> findUsers(User criteria, PageInfo<User> page);

    void insert(User user);

    void update(User user);

    void delete(long id);
}
