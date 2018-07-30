package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.model.po.TeamPO;
import cd.blog.humbird.libra.mapper.TeamMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.domain.UserDO;
import cd.blog.humbird.libra.util.UserUtils;
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
    public TeamPO getById(long id) {
        return teamMapper.getById(id);
    }

    @Cacheable(value = "teamLocalCache", key = "'name-' + #name", unless = "#result == null")
    public TeamPO getByName(String name) {
        return teamMapper.getByName(name);
    }

    public long create(TeamPO teamPO) {
        try {
            UserDO u = UserUtils.getUser();
            teamPO.setCreator(u.getName());
            teamPO.setModifier(u.getName());
            teamMapper.insert(teamPO);
            long id = teamPO.getId();
            String content = String.format("创建业务团队[%s]",
                    teamPO.getName()
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Team_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
        }
    }

    public void update(TeamPO prvTeamPO, TeamPO teamPO) {
        if(prvTeamPO == null) {
            return;
        }
        long id = teamPO.getId();
        try {
            UserDO u = UserUtils.getUser();
            teamPO.setModifier(u.getName());
            teamMapper.update(teamPO);
            String content = String.format("编辑%s用户,[团队:%s]->[团队:%s]",
                    prvTeamPO.getId(), prvTeamPO.getName(), teamPO.getName()
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Team_Edit.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
            cache.evict("id-" + id);
            cache.evict("name-" + prvTeamPO.getName());
        }
    }

    public void delete(TeamPO teamPO) {
        if (teamPO == null) {
            return;
        }
        long id = teamPO.getId();
        try {
            UserDO u = UserUtils.getUser();
            teamMapper.delete(id);
            String content = String.format("删除%s团队",
                    teamPO.getName()
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Team_Delete.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(TeamLocalCache.getCode());
            cache.evict("lists");
            cache.evict("id-" + id);
            cache.evict("name-" + teamPO.getName());
        }
    }
}
