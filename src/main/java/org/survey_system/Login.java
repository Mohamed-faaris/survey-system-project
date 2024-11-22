package org.survey_system;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {

    int id;

    public void loginView() throws SQLException {
        SQLManage manage = new SQLManage();
        JFrame frame = new JFrame();
        frame.setSize(450, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel heading = new JLabel("SURVEY SYSTEM");
        heading.setBounds(0, 50, 450, 50);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 40));
        frame.add(heading);

        JLabel uname = new JLabel("Username : ");
        uname.setBounds(50, 130, 150, 50);
        frame.add(uname);

        JTextField name = new JTextField();
        name.setBounds(50, 170, 350, 30);
        frame.add(name);

        JLabel upass = new JLabel("Password : ");
        upass.setBounds(50, 200, 150, 50);
        frame.add(upass);

        JPasswordField pass = new JPasswordField();
        pass.setBounds(50, 240, 350, 30);
        frame.add(pass);

        JButton login = new JButton("LOGIN");
        login.setBounds(100, 300, 100, 40);
        frame.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = name.getText();
                String password = pass.getText();
                if(username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please Enter All Details!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    try {
                        SQLManage manage= new SQLManage();
                        id = manage.authUser(username, password);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (id == -1) {
                        JOptionPane.showMessageDialog(frame, "No User Found!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                    else if(id == 0) {
                        JOptionPane.showMessageDialog(frame, "Wrong Password!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        MainPage mainPage = new MainPage();
                        try {
                            mainPage.mainPageView(id);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frame.dispose();
                    }
                }
            }
        });

        JButton signUp = new JButton("SIGNUP");
        signUp.setBounds(250, 300, 100, 40);
        frame.add(signUp);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp signup = new SignUp();
                signup.signUpView();
            }
        });

        JButton attend = new JButton("ATTEND A SURVEY (GUEST)");
        attend.setBounds(100, 350, 250, 40);
        frame.add(attend);
        attend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String surveyCode = JOptionPane.showInputDialog("Enter the Survey Code : ");
                try {
                    if(!surveyCode.isEmpty() && surveyCode.length() == 5) {
                        if(manage.check(surveyCode)) {
                            Guest guest = new Guest();
                            guest.guestView(surveyCode);
                        }
                        else {
                            JOptionPane.showMessageDialog(frame, "No Survey Available!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                catch(Exception e2) {

                }
            }
        });

        frame.setVisible(true);
    }
}

