package cd.blog.humbird.libra.entity;

/**
 * Created by david on 2018/7/16.
 */
public class User extends BaseEntity {

    private String loginName;

    private String password;

    private String name;

    private String email;

    private int admin;

    private int locked;

    private int status;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
