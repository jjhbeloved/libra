package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.model.po.EnvironmentPO;

import java.util.List;

/**
 * Created by david on 2018/7/11.
 */
public interface EnvironmentMapper {

    List<EnvironmentPO> findAll();

    EnvironmentPO findById(long id);

    EnvironmentPO findByName(String name);

    void insert(EnvironmentPO environmentPO);

    void update(EnvironmentPO environmentPO);

    void delete(long id);
}
