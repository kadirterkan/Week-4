package week4.com.patikadev.view;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.Patika;

import javax.swing.*;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_name;
    private JButton btn_update;
    private Patika patika;

    public UpdatePatikaGUI(final Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(300, 150);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        fld_patika_name.setText(patika.getName());
        setVisible(true);

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showErrorMessage("Lütfen alanı doldurunuz. ");
            } else {
                Patika.update(patika.getId(), fld_patika_name.getText());
                dispose();
            }
        });
    }
}
