package survey_system.surveySystem;

import survey_system.SQL.SQL_Connection;
import survey_system.userLogin.User;
import survey_system.userLogin.UserLogin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SurveyLogger {
    public static void storeInSql(User user, String question_Set, int[] result){
        try(SQL_Connection connection = new SQL_Connection()){
            PreparedStatement preparedStatement = connection.prepareStatement(QueryGenerator.generateInsertCommand(user.userId,result));
            int rowsAffected= preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("response by "+user.name+" recorded successfully");
            } else {
                System.out.println("Failed to record response");
            }
        } catch (SQLException e) {
            System.out.println(user.name+" is already responded");
        }
    }

}
class QueryGenerator {

    public static String generateInsertCommand(String userId, int[] results) {

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
        if (results == null || results.length == 0) {
            throw new IllegalArgumentException("results array cannot be null or empty");
        }

        StringBuilder query = new StringBuilder("INSERT INTO test (userId, ");

        for (int i = 1; i <= results.length; i++) {
            query.append("q").append(i);
            if (i < results.length) {
                query.append(", ");
            }
        }

        query.append(") VALUES ('").append(userId).append("', ");

        // Add the values for q1 to qn
        for (int i = 0; i < results.length; i++) {
            query.append(results[i]);
            if (i < results.length - 1) {
                query.append(", ");
            }
        }

        query.append(");");

        return query.toString();
    }
}

