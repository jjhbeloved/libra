package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.Environment;

import java.util.List;

/**
 * Created by david on 2018/7/11.
 */
public interface EnvironmentMapper {

    List<Environment> findAll();

    Environment findByID(long id);

    Environment findByName(String name);

    void insert(Environment environment);

    void update(Environment environment);

    void delete(long id);
}
