package cd.blog.humbird.libra.service.impl;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import cd.blog.humbird.libra.repository.OpLogRepository;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by david on 2018/7/18.
 */
@Service
public class OpLogService {

    @Autowired
    private OpLogRepository logRepository;

    public PageInfo<OpLog> findLogs(OpLogCriteria log, int pageNum, int pageSize) {
        return logRepository.getLogs(log, pageNum, pageSize);
    }
}
