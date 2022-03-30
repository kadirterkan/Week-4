package week4.com.patikadev.view.Educator;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AddQuizQuestionsGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JButton btn_add_quiz;
    private JScrollPane scrl_quiz_questions;
    private JTable tbl_question_list;
    private JTextField fld_question;
    private JButton btn_add_question;
    private DefaultTableModel mdl_question_list;
    private Object[] row_question_list;
    private JPopupMenu questionMenu;
    private ArrayList<String> strings;

    public AddQuizQuestionsGUI(final Integer courseId) {
        add(wrapper);
        setSize(1000, 500);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        strings = new ArrayList<>();

        mdl_question_list = new DefaultTableModel();

        Object[] col_question_list = {"Soru"};
        mdl_question_list.setColumnIdentifiers(col_question_list);
        row_question_list = new Object[col_question_list.length];

        btn_add_question.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_question)) {
                Helper.showErrorMessage("Lütfen soru giriniz. ");
            } else {
                addQuestion(fld_question.getText());
                strings.add(fld_question.getText());
                tbl_question_list.setModel(mdl_question_list);
                tbl_question_list.getTableHeader().setReorderingAllowed(false);
            }
        });

        btn_add_quiz.addActionListener(e -> {
            if (mdl_question_list.getRowCount() == 0) {
                Helper.showErrorMessage("En az bir soru giriniz. ");
            } else {
                Quiz.add(strings.toArray(new String[strings.size()]), courseId);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
    }

    private void addQuestion(String question) {
        int i = 0;
        row_question_list[i++] = question;
        mdl_question_list.addRow(row_question_list);
    }
}
