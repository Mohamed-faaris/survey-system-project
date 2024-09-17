package survey_system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserLogin {
    public User user = new User();
    public static String hashString(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashByes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b:hashByes){
                String hexByte = Integer.toHexString(0xff&b);
                if(hexByte.length()==1)  hexString.append('0');
                hexString.append(hexByte);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    void retrieveUserDetailsByUserId(String userId)
    {
        String url = "jdbc:mysql://localhost:3306/surveysystem";
        String userDB = "root";
        String passwordDB = "";
        try {
            Connection con = DriverManager.getConnection(url, userDB,passwordDB);
            String query = "SELECT * FROM users WHERE userId = '"+userId+"'";
            System.out.println("hello: "+query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                this.user.id = rs.getInt("id");
                this.user.userId = userId;
                this.user.name = rs.getString("name");
                this.user.userRoles = UserRoles.setUserRole(rs.getInt("id"));
                this.user.passwordHash = rs.getString("passwordHash");
                this.user.timestamp = rs.getTimestamp("timestamp");
            }
            System.out.println(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(hashString("hello"));
        new UserLogin().retrieveUserDetailsByUserId("Faaris");
    }
}

