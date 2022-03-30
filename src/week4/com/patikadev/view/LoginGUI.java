package week4.com.patikadev.view;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.User;
import week4.com.patikadev.view.Educator.EducatorGUI;
import week4.com.patikadev.view.student.StudentGUI;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_username;
    private JPasswordField fld_user_password;
    private JButton btn_login;

    public LoginGUI() {
        add(wrapper);
        setSize(400,400);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_password) || Helper.isFieldEmpty(fld_user_username)) {
                Helper.showErrorMessage("Lütfen bütün alanları doldurunuz");
            } else {
                User user = User.getFetch(fld_user_username.getText(), fld_user_password.getText());

                if (user != null) {
                    Helper.showResultMessage("Başarıyla giriş yaptınız. ");

                    switch (user.getType()) {
                        case "operator":
                            OperatorGUI operatorGUI = new OperatorGUI(user);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI(user);
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI(user);
                            break;
                    }
                    dispose();
                } else {
                    Helper.showErrorMessage("Kullanıcı bulunamadı");
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}