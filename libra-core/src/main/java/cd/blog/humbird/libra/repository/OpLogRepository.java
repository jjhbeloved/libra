package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.mapper.OpLogMapper;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by david on 2018/7/13.
 */
@Repository
public class OpLogRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpLogRepository.class);

    @Autowired
    private OpLogMapper opLogMapper;

    public void insert(OpLog opLog) {
        try {
            opLogMapper.insert(opLog);
        } catch (Exception e) {
            LOGGER.warn("log operate info error.{}", opLog.toString());
        }
    }

    public PageInfo<OpLog> getLogs(OpLogCriteria criteria, PageInfo<OpLog> page) {
        long totalCount = opLogMapper.count(criteria, page);
        List<OpLog> opLogs = opLogMapper.findLogs(criteria, page);
        page.setTotal(totalCount);
        page.setList(opLogs);
        return page;
    }

    public String getLogKey(long id, String keyName) {
        return opLogMapper.findLogKey(id, keyName);
    }
}
