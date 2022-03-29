package week4.com.patikadev.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int[] getScreenCenterLocation(Dimension size) {
        return new int[]{((Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2),
                ((Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2)};
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showResultMessage(String message) {
        optionPageTR();
        JOptionPane.showMessageDialog(null, message, Config.INFO_RESULT_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String message) {
        optionPageTR();
        JOptionPane.showMessageDialog(null, message, Config.INFO_ERROR_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
}
