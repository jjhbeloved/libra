package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.model.vo.OpLogCriteria;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by david on 2018/7/12.
 */
public interface OpLogMapper {

    void insert(OpLog opLog);

    List<OpLog> findLogs(@Param("criteria") OpLogCriteria criteria);

    /**
     * 指定查询的keyName
     *
     * @param id
     * @param keyName
     * @return
     */
    String findLogKey(@Param("id") long id, @Param("keyName") String keyName);

}
