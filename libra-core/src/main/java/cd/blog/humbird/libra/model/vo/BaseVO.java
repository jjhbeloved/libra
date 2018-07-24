package cd.blog.humbird.libra.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by david on 2018/7/13.
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
