package survey_system;

import java.sql.Timestamp;

public class User {
    public int id;
    public String userId;
    public String name;
    public UserRoles userRoles;
    public String passwordHash;
    public Timestamp timestamp;

    public User() {

    }

    public User(int id, String userId, String name, UserRoles userRoles, String passwordHash, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.userRoles = userRoles;
        this.passwordHash = passwordHash;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", userRoles=" + userRoles +
                ", passwordHash='" + passwordHash + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

