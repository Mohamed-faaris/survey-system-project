package org.survey_system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class Login {

    int id;

    public void     loginView() throws SQLException {
        SQLManage manage = new SQLManage();

        JFrame frame = new JFrame();
        frame.setSize(450, 450);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel heading = new JLabel("SURVEY SYSTEM");
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        frame.add(heading, gbc);

        JLabel uname = new JLabel("Username:");
        uname.setHorizontalAlignment(JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to single column
        frame.add(uname, gbc);


        JTextField name = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(name, gbc);


        JLabel upass = new JLabel("Password:");
        upass.setHorizontalAlignment(JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(upass, gbc);


        JPasswordField pass = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(pass, gbc);


        JButton login = new JButton("LOGIN");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        frame.add(login, gbc);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = name.getText();
                String password = pass.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please Enter All Details!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        SQLManage manage = new SQLManage();
                        id = manage.authUser(username, password);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if (id == -1) {
                        JOptionPane.showMessageDialog(frame, "No User Found!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else if (id == 0) {
                        JOptionPane.showMessageDialog(frame, "Wrong Password!!!", "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else {
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
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(signUp, gbc);

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUp signup = new SignUp();
                signup.signUpView();
            }
        });

        // Attend Survey Button
        JButton attend = new JButton("ATTEND A SURVEY (GUEST)");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        frame.add(attend, gbc);

        attend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] codesArray = null;
                try {
                    SQLManage sqlManage = new SQLManage();
                    List<String> codes = sqlManage.getSurveyCodes();
                    codesArray = codes.toArray(String[]::new);
                } catch (Exception e3) {
                    JOptionPane.showMessageDialog(frame, "Failed to load survey codes.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                JFrame surveyFrame = new JFrame("Survey Codes");
                surveyFrame.setBounds(250, 300, 300, 200);
                surveyFrame.setLayout(new GridBagLayout()); // Use GridBagLayout
                GridBagConstraints surveyGbc = new GridBagConstraints();
                surveyGbc.insets = new Insets(10, 10, 10, 10);
                surveyGbc.fill = GridBagConstraints.HORIZONTAL;

                JComboBox<String> comboBox = new JComboBox<>(codesArray);
                surveyGbc.gridx = 0;
                surveyGbc.gridy = 0;
                surveyGbc.gridwidth = 2;
                surveyFrame.add(comboBox, surveyGbc);

                JButton submit = new JButton("Submit");
                surveyGbc.gridx = 0;
                surveyGbc.gridy = 1;
                surveyGbc.gridwidth = 2;
                surveyFrame.add(submit, surveyGbc);

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String surveyCode = (String) comboBox.getSelectedItem();
                        Guest guest = new Guest();
                        try {
                            guest.guestView(surveyCode);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(surveyFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                surveyFrame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }
}
