package cd.blog.humbird.libra.repository.mapper;

import cd.blog.humbird.libra.entity.Environment;

import java.util.List;

/**
 * Created by david on 2018/7/11.
 */
public interface EnviornmentMapper {

    List<Environment> findAll();

    Environment findByID(long id);

    Environment findByName(String name);

    void create(Environment environment);

    void update(Environment environment);

    void delete(long id);
}
