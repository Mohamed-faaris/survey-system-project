package org.survey_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/*
DATABASE INIT CMD

1. Create the database
CREATE DATABASE survey;
2. Use the database
USE survey;
3. Create necessary tables
--Actor table
CREATE TABLE users(id int primary key auto_increment, fname varchar(50), uname varchar(50), pass varchar(50));

        --User Question Table
CREATE TABLE userQuestions(id int, surveycode varchar(5), total int);

        --Questions table
CREATE TABLE questions(surveycode varchar(5), question varchar(255), option1 varchar(255), option2 varchar(255), option3 varchar(255), option4 varchar(255));

        --Survey Answer table
CREATE TABLE surveyquestions(surveycode varchar(5), qno int, opno int);
 */

public class SQLManage {

        Connection con;

        public SQLManage() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/survey?autoReconnect=true&useSSL=false";
            String usr = "root";
            String pass = "root";
            con = DriverManager.getConnection(url, usr, pass);
        }

        public void newUser(String name, String uname, String pass) throws SQLException {
            String str = "INSERT INTO users(fname, uname, pass) values ('"+name+"', '"+uname+"', '"+pass+"')";
            Statement stm = con.createStatement();
            stm.executeUpdate(str);
        }

        public int authUser(String uname, String pass) throws SQLException {
            String str = "SELECT * FROM users WHERE uname = '"+uname+"'";
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
            return stm.executeQuery(str);
        }

        public ResultSet surveys(int id, String search) throws SQLException {
            String str = "SELECT * FROM userQuestions WHERE id = "+id+" and surveycode like '%"+search+"%'";
            Statement stm = con.createStatement();
            return stm.executeQuery(str);
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
            return rst.next();
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

        public List<String> getSurveyCodes() throws  SQLException{
            String str = "SELECT distinct surveycode FROM questions";
            Statement stm = con.createStatement();
            ResultSet rst = stm.executeQuery(str);

            List<String> list = new ArrayList<>();

            while(rst.next()){
                list.add(rst.getString("surveycode"));
            }

            return list;
        }

        public static void main(String[] args) throws SQLException {
            SQLManage sqlManage = new SQLManage();
            System.out.println(sqlManage.getSurveyCodes());
        }
    }

