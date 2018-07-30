package cd.blog.humbird.libra.util;

import cd.blog.humbird.libra.model.domain.UserDO;

/**
 * Created by david on 2018/7/13.
 */
public class UserUtils {

	private static ThreadLocal<UserDO> users = new ThreadLocal<>();

	// TODO 接入用户中心的时候需要调整
	public static UserDO getUser() {
//        User user = users.get();
//        return user.getName();
		UserDO userDO = new UserDO();
		userDO.setId(67011);
		userDO.setName("david");
		userDO.setFrIp("127.0.0.1");
		return userDO;
	}

	public static void setUser(UserDO userDO) {
		users.set(userDO);
	}

	public static void clear() {
		users.remove();
	}
}
