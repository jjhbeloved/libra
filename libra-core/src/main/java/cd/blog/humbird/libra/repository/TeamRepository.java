package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.entity.Team;
import cd.blog.humbird.libra.mapper.TeamMapper;
import cd.blog.humbird.libra.model.vo.User;
import cd.blog.humbird.libra.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by david on 2018/7/18.
 */
@Repository
public class TeamRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
    private static final String CACHE_TEAM_ = "cache_team_";

    @Resource(name = "caffeineClusterCache")
    private Cache cache;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    public Team findById(long id) {
        return teamMapper.findById(id);
    }

    public Team findByName(String name) {
        return teamMapper.findByName(name);
    }

    public long create(Team team) {
        try {
            User u = UserUtil.getUser();
            team.setCreator(u.getName());
            team.setModifier(u.getName());
            teamMapper.insert(team);
            long id = team.getId();
            String content = String.format("创建业务团队[%s]",
                    team.getName()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Team_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            cache.evict(CACHE_TEAM_ + "list");
        }
    }

    public void update(Team team) {
        try {
            long id = team.getId();
            Team existsTeam = findById(id);
            if (existsTeam != null) {
                User u = UserUtil.getUser();
                team.setModifier(u.getName());
                teamMapper.update(team);
                String content = String.format("编辑%s用户,[团队:%s]->[团队:%s]",
                        existsTeam.getId(), existsTeam.getName(), team.getName()
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.Team_Edit.getValue(), u.getId(), content));
            }
        } finally {
            cache.evict(CACHE_TEAM_ + "list");
        }
    }

    public void delete(long id) {
        try {
            Team team = findById(id);
            if (team != null) {
                cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
                teamMapper.delete(id);
                String content = String.format("删除%s团队",
                        team.getName()
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.Team_Delete.getValue(), u.getId(), content));
            }
        } finally {
            cache.evict(CACHE_TEAM_ + "list");
        }
    }
}
