package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.LibraConstants;
import cd.blog.humbird.libra.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author david
 * @since created by on 2018/7/12 00:45
 */
public class SystemSettingRepositoryIT extends BaseIT {

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Test
    public void get() {
        String value = systemSettingRepository.getVal(LibraConstants.SYSTEM_SETTING_CACHE_ENABLED);
        systemSettingRepository.getVal(LibraConstants.SYSTEM_SETTING_CACHE_ENABLED);
        assertThat(value).isNotBlank();
        System.out.println(value);
    }

    @Test
    public void getBool() {
        boolean flag = systemSettingRepository.getBool(LibraConstants.SYSTEM_SETTING_CACHE_ENABLED);
        systemSettingRepository.update(LibraConstants.SYSTEM_SETTING_CACHE_ENABLED, String.valueOf(!flag));
        assertThat(systemSettingRepository.getBool(LibraConstants.SYSTEM_SETTING_CACHE_ENABLED)).isEqualTo(!flag);
    }
}
