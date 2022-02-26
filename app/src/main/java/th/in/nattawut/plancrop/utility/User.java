package th.in.nattawut.plancrop.utility;

public class User {



    private Integer success;

    public User(Integer success) {
        this.success = success;
    }

    public Integer getSuccess() {
        return success;
    }
    public String isLogin;

    private int id;
    private String email, name, type;

    public User(int id, String email, String name, String school) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
