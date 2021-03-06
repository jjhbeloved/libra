package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.Team;

import java.util.List;

/**
 * Created by david on 2018/7/18.
 */
public interface TeamMapper {

    List<Team> findAll();

    List<Team> findTeams(String name);

    Team findById(long id);

    Team findByName(String name);

    void insert(Team team);

    void update(Team team);

    void delete(long id);
}
