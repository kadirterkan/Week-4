package week4.com.patikadev.view.Educator;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.helper.Item;
import week4.com.patikadev.model.Content;
import week4.com.patikadev.model.Course;

import javax.swing.*;
import java.util.Objects;

public class UpdateContentGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_content_name;
    private JTextField fld_content_intro;
    private JTextField fld_content_url;
    private JComboBox cmb_content_course;
    private JButton btn_update_content;
    private Content content;

    public UpdateContentGUI(final Content content) {
        add(wrapper);
        setSize(300, 750);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        this.content = content;

        fld_content_name.setText(content.getBaslik());
        fld_content_intro.setText(content.getAciklama());
        fld_content_url.setText(content.getYoutubeUrl());

        loadCourseCombo();

        btn_update_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_name) || Helper.isFieldEmpty(fld_content_intro) || Helper.isFieldEmpty(fld_content_url)) {
                Helper.showErrorMessage("Lütfen bütün alanları doldurunuz");
            } else {
                Content.update(content.getId(), fld_content_name.getText(), fld_content_intro.getText(), fld_content_url.getText(), ((Item) cmb_content_course.getSelectedItem()).getKey());
                dispose();
            }
        });
    }

    private void loadCourseCombo() {
        cmb_content_course.removeAllItems();

        for (Course course : Course.getList()) {
            Item item = new Item(course.getId(), course.getName());
            cmb_content_course.addItem(item);
            if (Objects.equals(item.getKey(), content.getDers().getId())) {
                cmb_content_course.setSelectedItem(item);
            }
        }
    }
}
