package cd.blog.humbird.libra.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
