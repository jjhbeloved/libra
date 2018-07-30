package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.model.po.TeamPO;

import java.util.List;

/**
 * Created by david on 2018/7/18.
 */
public interface TeamMapper {

    List<TeamPO> listAll();

    List<TeamPO> listTeams(String name);

    TeamPO getById(long id);

    TeamPO getByName(String name);

    void insert(TeamPO teamPO);

    void update(TeamPO teamPO);

    void delete(long id);
}
