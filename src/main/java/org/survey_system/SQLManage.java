package org.survey_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    public class SQLManage {

        Connection con;

        public SQLManage() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/survey?autoReconnect=true&useSSL=false";
            String usr = "root";
            String pass = "root";
            con = DriverManager.getConnection(url, usr, pass);
        }

        public void newUser(String name, String uname, String pass) throws SQLException {
            String str = "INSERT INTO actors(fname, uname, pass) values ('"+name+"', '"+uname+"', '"+pass+"')";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public int authUser(String uname, String pass) throws SQLException {
            String str = "SELECT * FROM actors WHERE uname = '"+uname+"'";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);
            if (!rst.next())
                return -1;
            else {
                if(rst.getString("pass").equals(pass))
                    return rst.getInt("id");
                else
                    return 0;
            }
        }

        public void newQuestion(String code, String question, String op1, String op2, String op3, String op4) throws SQLException {
            String str = "INSERT INTO questions values ('"+code+"', '"+question+"', '"+op1+"', '"+op2+"', '"+op3+"', '"+op4+"')";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public void userQuestionAdd(int id, String surveycode) throws SQLException {
            String str = "INSERT INTO userQuestions values ("+id+", '"+surveycode+"', 0)";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public void answerUpdt(String surveycode, int qno, int option) throws SQLException {
            String str = "INSERT INTO surveyquestions values ('"+surveycode+"', " + qno + ", " + option + ")";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public ResultSet getQuestions(String surveycode) throws SQLException {
            String str = "SELECT * FROM questions WHERE surveycode = '"+surveycode+"'";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);
            return rst;
        }

        public ResultSet surveys(int id, String search) throws SQLException {
            String str = "SELECT * FROM userQuestions WHERE id = "+id+" and surveycode like '%"+search+"%'";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);
            return rst;
        }

        public void addTotal() throws SQLException {
            String str = "UPDATE userQuestions SET total = total+1";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public boolean check(String search) throws SQLException {
            String str = "SELECT * FROM userQuestions WHERE surveycode = '"+search+"'";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);
            if(rst.next())
                return true;
            else
                return false;
        }

        public void removeSurvey(String surveycode) throws SQLException {
            String str = "DELETE FROM questions WHERE surveycode = '"+surveycode+"'";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
            str = "DELETE FROM surveyquestions WHERE surveycode = '"+surveycode+"'";
            stm.executeUpdate(str);
            str = "DELETE FROM userQuestions WHERE surveycode = '"+surveycode+"'";
            stm.executeUpdate(str);
        }

        public int getCount(String surveycode, int qno, int op) throws SQLException {
            String str = "SELECT count(opno) FROM surveyquestions WHERE surveycode = '"+surveycode+"' AND qno = "+(qno+1)+" AND opno = "+op;
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);
            if(rst.next())
                return rst.getInt("count(opno)");
            else
                return 0;
        }

    }

