package week4.com.patikadev.view.student;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.Content;
import week4.com.patikadev.model.Course;
import week4.com.patikadev.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShowContentsGUI extends JFrame {
    private final User student;
    private final Course course;
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JButton btn_exit;
    private JTabbedPane tabbedPane1;
    private JTable tbl_content_list;
    private JPanel pnl_content_list;
    private JScrollPane scrl_content_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private JPopupMenu contentMenu;

    public ShowContentsGUI(final User student, final Course course) {
        this.student = student;
        this.course = course;
        add(wrapper);
        setSize(1000, 500);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        // Content

        mdl_content_list = Helper.initTableModel();

        contentMenu = new JPopupMenu();
        JMenuItem show = new JMenuItem("İçerik linkini aç");
        JMenuItem solveQuiz = new JMenuItem("Quizi çöz");
        contentMenu.add(show);
        contentMenu.add(solveQuiz);

        show.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            Content content = Content.getFetch(selectedRowId);
            if (content != null) {
                Helper.youtubeUrlMessageShow(content.getYoutubeUrl());
            }
        });

        solveQuiz.addActionListener(e -> {

        });

        Object[] col_content_list = {"ID", "Başlık", "Açıklama", "Youtube Linki", "Ders Adı"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.setComponentPopupMenu(contentMenu);

        loadContentModel();
    }

    public static void main(String[] args) {
        Helper.setLayout();
        User user = User.getFetch(10);

        Course course = Course.getFetch(1);

        ShowContentsGUI showContentsGUI = new ShowContentsGUI(user, course);
    }

    private void loadContentModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_content_list.getModel();
        clearTable.setRowCount(0);

        for (Content content : Content.getList(course.getId())) {
            toRowItem(content);
        }
    }

    private void toRowItem(Content content) {
        int i = 0;
        row_content_list[i++] = content.getId();
        row_content_list[i++] = content.getBaslik();
        row_content_list[i++] = content.getAciklama();
        row_content_list[i++] = content.getYoutubeUrl();
        row_content_list[i++] = content.getDers().getName();
        mdl_content_list.addRow(row_content_list);
    }
}
