package week4.com.patikadev.helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public static boolean showConfirmMessage(String message) {
        optionPageTR();
        return JOptionPane.showConfirmDialog(null, message, Config.INFO_CONFIRM_TITLE, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void resultMessage(Integer result) {
        if (result.equals(-1)) {
            Helper.showErrorMessage("Bir hata meydana geldi");
        } else if(result.equals(0)) {
            Helper.showResultMessage("Bir değişiklik yapılamadı");
        } else {
            Helper.showResultMessage("İşlem başarıyla gerçekleşti");
        }
    }

    public static void youtubeUrlMessageShow(String youtubeUrl) {
        JOptionPane.showMessageDialog(null, youtubeUrl, "İçeriğin Youtube Linki", JOptionPane.PLAIN_MESSAGE);
    }

    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }

    public static DefaultTableModel initTableModel() {
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
    }
}
