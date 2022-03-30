package week4.com.patikadev.view.Educator;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.helper.Item;
import week4.com.patikadev.model.Content;
import week4.com.patikadev.model.Course;
import week4.com.patikadev.model.Quiz;
import week4.com.patikadev.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JButton btn_logout;
    private JPanel pnl_course_list;
    private JTable tbl_content_list;
    private JTextField fld_content_name;
    private JTextField fld_content_intro;
    private JTextField fld_content_url;
    private JComboBox cmb_content_course;
    private JButton btn_add_content;
    private JPanel pnl_content_list;
    private JPanel pnl_quiz_list;
    private JComboBox cmb_quiz_content;
    private JButton btn_add_quiz;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_add_question;
    private JTable tbl_quiz_list;
    private JScrollPane scrl_content_list;
    private JTable tbl_course_list;
    private JScrollPane scrl_course_list;
    private User educator;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;
    private JPopupMenu contentMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private JPopupMenu quizMenu;

    public EducatorGUI(final User user) {
        this.educator = user;
        add(wrapper);
        setSize(1000, 500);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        // Courses

        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadCourseList();

        // Content

        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        contentMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        contentMenu.add(updateMenu);
        contentMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            Content content = Content.getFetch(selectedRowId);
            if (content != null) {
                UpdateContentGUI updateContentGUI = new UpdateContentGUI(content);
                updateContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel();
                    }
                });
            } else {
                Helper.showErrorMessage("Bir sorun meydana geldi. ");
            }
        });

        deleteMenu.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            Content content = Content.getFetch(selectedRowId);
            if (content != null) {
                if (Helper.showConfirmMessage("Bu işlem geri alınamaz, emin misiniz? ")) Content.delete(selectedRowId);
                loadQuizModel();
                loadContentModel();
            } else {
                Helper.showErrorMessage("Bir sorun meydana geldi");
            }
        });

        Object[] col_content_list = {"ID", "Başlık", "Açıklama", "Youtube Linki", "Ders Adı"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.setComponentPopupMenu(contentMenu);

        btn_add_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_name) || Helper.isFieldEmpty(fld_content_intro) || Helper.isFieldEmpty(fld_content_url)) {
                Helper.showErrorMessage("Lütfen alanları doldurunuz");
            } else {
                Item courseItem = (Item) cmb_content_course.getSelectedItem();
                Content.add(fld_content_name.getText(), fld_content_intro.getText(), fld_content_url.getText(), courseItem.getKey());
                loadContentModel();
            }
        });

        loadCourseCombo();
        loadContentModel();

        // Quiz

        mdl_quiz_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        quizMenu = new JPopupMenu();
        JMenuItem deleteQuiz = new JMenuItem("Sil");
        quizMenu.add(deleteQuiz);

        deleteQuiz.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            Quiz quiz = Quiz.getFetch(selectedRowId);
            if (quiz != null) {
                if (Helper.showConfirmMessage("Bu işlem geri alınamaz, emin misiniz? ")) {
                    Quiz.delete(quiz.getId());
                }
                loadQuizModel();
            } else {
                Helper.showErrorMessage("Böyle bir quiz bulunamadı. ");
            }
        });

        Object[] col_quiz_list = {"ID", "İçerik Başlığı", "Sorular"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];

        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.setComponentPopupMenu(quizMenu);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);

        loadQuizModel();
        loadContentCombo();

        btn_logout.addActionListener(e -> {
            dispose();
        });
        btn_add_quiz.addActionListener(e -> {
            if (cmb_quiz_content.getSelectedItem() != null) {
                AddQuizQuestionsGUI addQuizQuestionsGUI = new AddQuizQuestionsGUI(((Item) cmb_quiz_content.getSelectedItem()).getKey());
                addQuizQuestionsGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadQuizModel();
                    }
                });
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        User user = new User(9, "Test", "Test", "1234", "educator");

        EducatorGUI educatorGUI = new EducatorGUI(user);
    }

    private void loadCourseList() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_course_list.getModel();
        clearTable.setRowCount(0);

        for (Course course : Course.getList()) {
            if (course.getEducator().getId().equals(educator.getId())) {
                toRowItem(course);
            }
        }
    }

    private void loadContentCombo() {
        cmb_quiz_content.removeAllItems();

        for (Content content : Content.getList()) {
            cmb_quiz_content.addItem(new Item(content.getId(), content.getBaslik()));
        }
    }

    private void loadCourseCombo() {
        cmb_content_course.removeAllItems();

        for (Course course : Course.getList()) {
            if (course.getEducator().getId().equals(educator.getId())) {
                cmb_content_course.addItem(new Item(course.getId(), course.getName()));
            }
        }
    }

    private void loadContentModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_content_list.getModel();
        clearTable.setRowCount(0);

        for (Content content : Content.getList()) {
            toRowItem(content);
        }

        tbl_content_list.setModel(mdl_content_list);

        loadContentCombo();
    }

    private void loadQuizModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_quiz_list.getModel();
        clearTable.setRowCount(0);

        for (Quiz quiz : Quiz.getList()) {
            if (quiz.getContent().getDers().getEducator().getId().equals(educator.getId())) {
                toRowItem(quiz);
            }
        }

        tbl_quiz_list.setModel(mdl_quiz_list);
    }

    private void toRowItem(Course course) {
        int i = 0;
        row_course_list[i++] = course.getId();
        row_course_list[i++] = course.getName();
        row_course_list[i++] = course.getLang();
        row_course_list[i++] = course.getPatika().getName();
        row_course_list[i++] = course.getEducator().getName();
        mdl_course_list.addRow(row_course_list);
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

    private void toRowItem(Quiz quiz) {
        int i = 0;
        row_quiz_list[i++] = quiz.getId();
        row_quiz_list[i++] = quiz.getContent().getAciklama();
        row_quiz_list[i++] = quiz.getQuestions();
        mdl_quiz_list.addRow(row_quiz_list);
    }
}
