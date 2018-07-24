package cd.blog.humbird.libra.cli.config;

/**
 * @author david
 * @since created by on 18/7/25 00:34
 */
public interface ConfigListener {

    /**
     * 配置信息发送变化
     *
     * @param event 事件信息
     */
    void changed(ConfigEvent event);

}
