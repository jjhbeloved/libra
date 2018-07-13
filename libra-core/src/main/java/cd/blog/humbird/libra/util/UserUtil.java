package cd.blog.humbird.libra.util;

import cd.blog.humbird.libra.model.vo.User;

/**
 * Created by david on 2018/7/13.
 */
public class UserUtil {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    // TODO 接入用户中心的时候需要调整
    public static User getUser() {
//        User user = users.get();
//        return user.getName();
        User user = new User();
        user.setId(67011);
        user.setName("david");
        user.setFrIp("127.0.0.1");
        return user;
    }

    public static void setUser(User user) {
        users.set(user);
    }

    public static void clear() {
        users.remove();
    }
}
