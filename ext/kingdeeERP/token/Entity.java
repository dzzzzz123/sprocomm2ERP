package ext.kingdeeERP.token;


public class Entity {
    private String param1;
    private String username;
    private String password;
    private String param2;

    public Entity() {
    }

    public Entity(String param1, String username, String password, String param2) {
        this.param1 = param1;
        this.username = username;
        this.password = password;
        this.param2 = param2;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "param1='" + param1 + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", param2='" + param2 + '\'' +
                '}';
    }
}
