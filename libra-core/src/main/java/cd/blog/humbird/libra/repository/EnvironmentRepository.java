package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by david on 2018/7/11.
 */
@Repository
public class EnvironmentRepository {

    @Autowired
    private EnvironmentMapper environmentMapper;

    public Environment findById(long id) {
        return environmentMapper.findByID(id);
    }
}
