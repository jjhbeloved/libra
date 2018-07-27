package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.cli.model.ConfigEvent;

/**
 * @author david
 * @since created by on 18/7/25 22:16
 */
public interface Callback {

    /**
     * 回调
     *
     * @param event 信息
     */
    void call(ConfigEvent event);
}
