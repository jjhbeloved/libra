package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.Team;
import cd.blog.humbird.libra.mapper.TeamMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.vo.User;
import cd.blog.humbird.libra.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static cd.blog.humbird.libra.model.em.CacheEnum.TeamLocalCache;

/**
 * Created by david on 2018/7/18.
 */
@Repository
public class TeamRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
    private static final String LISTS = "lists";
    private static final String ID_ = "id-";
    private static final String NAME_ = "name-";

    @Resource(name = "localCacheManager")
    private CacheManager cacheManager;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Cacheable(value = "teamLocalCache", key = "'id-' + #id", unless = "#result == null")
    public Team findById(long id) {
        return teamMapper.findById(id);
    }

    @Cacheable(value = "teamLocalCache", key = "'name-' + #name", unless = "#result == null")
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
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
        }
    }

    public void update(Team team) {
        long id = team.getId();
        Team existsTeam = findById(id);
        if (existsTeam == null) {
            return;
        }
        try {
            User u = UserUtil.getUser();
            team.setModifier(u.getName());
            teamMapper.update(team);
            String content = String.format("编辑%s用户,[团队:%s]->[团队:%s]",
                    existsTeam.getId(), existsTeam.getName(), team.getName()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Team_Edit.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
            cache.evict("id-" + id);
            cache.evict("name-" + existsTeam.getName());
        }
    }

    public void delete(long id) {
        Team team = findById(id);
        if (team == null) {
            return;
        }
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            teamMapper.delete(id);
            String content = String.format("删除%s团队",
                    team.getName()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Team_Delete.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
            cache.evict("id-" + id);
            cache.evict("name-" + team.getName());
        }
    }
}
