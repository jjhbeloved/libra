package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import com.github.pagehelper.PageInfo;

/**
 * @author david
 * @since created by on 18/7/31 00:13
 */
public interface OpLogService {

	/**
	 * @param log      条件对象
	 * @param pageNum  第n页
	 * @param pageSize 每页面n条
	 * @return
	 */
	PageInfo<OpLogPO> listLogs(OpLogCriteria log, Integer pageNum, Integer pageSize);
}
