package week4.com.patikadev.view.student;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.Content;
import week4.com.patikadev.model.User;

import javax.swing.*;

public class ShowContentGUI extends JFrame {
    private final Content content;
    private final User student;
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JButton btn_exit;
    private JTabbedPane pnl_show_content_tab;
    private JPanel pnl_show_content;
    private JTextArea txt_content_intro;
    private JTextArea txt_content_youtube_url;
    private JPanel pnl_content_review;
    private JComboBox comboBox1;
    private JTextArea txt_content_comment;
    private JButton btn_add_review;
    private JLabel fld_content_header;
    private JPanel pnl_content_quiz;

    public ShowContentGUI(final Content content, final User student) {
        this.content = content;
        this.student = student;
        add(wrapper);
        setSize(350, 800);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        // Content

        txt_content_intro.setText(content.getAciklama());
        txt_content_intro.setEditable(false);
        fld_content_header.setText(content.getBaslik());
        txt_content_youtube_url.setText(content.getYoutubeUrl());
        txt_content_youtube_url.setEditable(false);

        btn_add_review.addActionListener(e -> {
            // Review.add();
        });

        btn_exit.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        User user = User.getFetch(10);

        Content content = Content.getFetch(3);

        ShowContentGUI showContentGUI = new ShowContentGUI(content, user);
    }
}
