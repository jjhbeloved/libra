package cd.blog.humbird.libra.service.impl;

import cd.blog.humbird.libra.common.constant.Parameter;
import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import cd.blog.humbird.libra.repository.OpLogRepository;
import cd.blog.humbird.libra.service.OpLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by david on 2018/7/18.
 */
@Service
public class OpLogServiceImpl implements OpLogService {

	@Autowired
	private OpLogRepository logRepository;

	@Override
	public PageInfo<OpLogPO> listLogs(OpLogCriteria log, Integer pageNum, Integer pageSize) {
		return logRepository.getLogs(
				log,
				Optional.ofNullable(pageNum).orElse(Parameter.DEFAULT_PAGE_NUM),
				Optional.ofNullable(pageSize).orElse(Parameter.DEFAULT_PAGE_SIZE)
		);
	}
}
