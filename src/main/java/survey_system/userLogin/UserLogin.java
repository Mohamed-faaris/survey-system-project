package survey_system.userLogin;

import survey_system.SQL.SQL_Connection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class UserLogin {
    public User user = null;

    public boolean isLogged() {
        return (user != null);
    }

    static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashByes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashByes) {
                String hexByte = Integer.toHexString(0xff & b);
                if (hexByte.length() == 1) hexString.append('0');
                hexString.append(hexByte);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

     public static User retrieveUserDetailsByUserId(String userId) {
        User temp = new User();
        try(SQL_Connection connection = new SQL_Connection()){
            String query = "SELECT * FROM users WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                temp.id = rs.getInt("id");
                temp.userId = userId;
                temp.name = rs.getString("name");
                temp.userRoles = UserRoles.setUserRole(rs.getInt("id"));
                temp.passwordHash = rs.getString("passwordHash");
                temp.timestamp = rs.getTimestamp("timestamp");
                return temp;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean userLogin(String userId,String password){
        User temp = retrieveUserDetailsByUserId(userId);
        if(temp == null){
            System.out.println("UserId not found");
        }
        else {
            if(temp.passwordHash.equals(hashString(password))){
                System.out.println("logged in successfully as "+temp.name+" has "+temp.userRoles.accessDescription);
                this.user = temp;
            }
            else{
                System.out.println("Password is incorrect");
            }
        }
        return this.isLogged();
    }

    public boolean userRegister() {
        Scanner s = new Scanner(System.in);
        User newUser = new User();
        try (SQL_Connection connection = new SQL_Connection()) {
            String userId = "" ;
            boolean userIdExists = true;

            while (userIdExists) {
                System.out.print("userId: ");
                userId = s.nextLine();
                String query = "SELECT * FROM users WHERE userId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userId);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (!rs.next()) {
                        userIdExists = false;
                    } else {
                        System.out.println("UserId already exists, try again.");
                    }
                }
            }
            newUser.userId = userId;
            System.out.print("name:");
            newUser.name = s.nextLine();
            System.out.println("role:USER");
            newUser.userRoles = UserRoles.USER;
            System.out.print("password:");
            newUser.passwordHash = hashString(s.nextLine());
            String query = "INSERT INTO users (userId, name, userRoles, passwordHash) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newUser.userId);
            preparedStatement.setString(2, newUser.name);
            preparedStatement.setInt(3, UserRoles.toInt(newUser.userRoles)); // Assuming role is stored as a string
            preparedStatement.setString(4, newUser.passwordHash);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
                System.out.println("\n\nsigned in successfully as "+newUser.name+" has "+newUser.userRoles.accessDescription);
                this.user = newUser;
                return true;
            } else {
                System.out.println("Failed to register the user.");
                return false;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}