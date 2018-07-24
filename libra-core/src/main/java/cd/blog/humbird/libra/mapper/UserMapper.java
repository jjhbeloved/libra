package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by david on 2018/7/16.
 */
public interface UserMapper {

    List<User> findAll();

    List<User> findUsers(@Param("criteria") UserCriteria criteria);

    User findById(long id);

    User findByName(String name);

    List<User> findByNameOrLoginNameLike(@Param("name") String name, @Param("includeAdmin") boolean includeAdmin);

    void insert(User user);

    void update(User user);

    void delete(long id);
}
