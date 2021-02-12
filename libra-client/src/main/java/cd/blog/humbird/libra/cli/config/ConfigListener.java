package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.model.ConfigEvent;

/**
 * @author david
 * @since created by on 18/7/25 00:34
 */
public interface ConfigListener {

    /**
     * 配置信息需要刷新
     *
     * @param event 事件信息
     */
    void refresh(ConfigEvent event);

}
