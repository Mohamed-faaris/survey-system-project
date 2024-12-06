package org.survey_system;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)  {
        Login login = new Login();
        try {
            login.loginView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}