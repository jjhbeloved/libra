package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.model.po.UserPO;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by david on 2018/7/16.
 */
public interface UserMapper {

    List<UserPO> findAll();

    List<UserPO> findUsers(@Param("criteria") UserCriteria criteria);

    UserPO findById(long id);

    UserPO findByName(String name);

    List<UserPO> findByNameOrLoginNameLike(@Param("name") String name, @Param("includeAdmin") boolean includeAdmin);

    void insert(UserPO userPO);

    void update(UserPO userPO);

    void delete(long id);
}
