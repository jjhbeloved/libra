package cd.blog.humbird.libra.cli.config;

/**
 * @author david
 * @since created by on 18/7/25 00:36
 */
public abstract class AbstractConfigLoader implements ConfigLoader {

    private ConfigListener configListener;

    @Override
    public void addConfigListener(ConfigListener configListener) {
        this.configListener = configListener;
    }

    @Override
    public void removeConfigListener(ConfigListener configListener) {
        configListener = null;
    }

    public ConfigListener getConfigListener() {
        return configListener;
    }
}
