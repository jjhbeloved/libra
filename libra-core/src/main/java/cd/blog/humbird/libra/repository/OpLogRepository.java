package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.mapper.OpLogMapper;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import com.github.pagehelper.PageHelper;
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

    public void insert(OpLogPO opLogPO) {
        try {
            opLogMapper.insert(opLogPO);
        } catch (Exception e) {
            LOGGER.warn("log operate info error.{}", opLogPO.toString());
        }
    }

    public PageInfo<OpLogPO> getLogs(OpLogCriteria criteria, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OpLogPO> opLogPOS = opLogMapper.findLogs(criteria);
        return new PageInfo<>(opLogPOS);
    }

    public String getLogKey(long id, String keyName) {
        return opLogMapper.findLogKey(id, keyName);
    }
}
