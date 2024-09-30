package survey_system.userLogin;

import java.sql.Timestamp;

public class User
{
    public int id;
    public String userId;
    public String name;
    public UserRoles userRoles;
    public String passwordHash;
    public Timestamp timestamp;

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

