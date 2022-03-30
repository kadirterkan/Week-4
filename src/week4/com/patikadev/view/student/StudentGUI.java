package week4.com.patikadev.view.student;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.model.Course;
import week4.com.patikadev.model.Patika;
import week4.com.patikadev.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame {
    private final User student;
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JButton btn_logout;
    private JTabbedPane pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JTable tbl_mycourses_list;
    private JTable tbl_mypatika_list;
    private JPanel pnl_allpatika_list;
    private JPanel pnl_mypatika_list;
    private JScrollPane scrl_mypatika_list;
    private JPanel pnl_mycourses_list;
    private JScrollPane scrl_mycourses_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_mypatika_list;
    private Object[] row_mypatika_list;
    private JPopupMenu myPatikaMenu;
    private DefaultTableModel mdl_mycourses_list;
    private Object[] row_mycourses_list;
    private JPopupMenu myCoursesMenu;

    public StudentGUI(final User student) {
        this.student = student;
        add(wrapper);
        setSize(1000, 500);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        // Patika

        mdl_patika_list = Helper.initTableModel();

        patikaMenu = new JPopupMenu();
        JMenuItem joinMenu = new JMenuItem("Katıl");
        patikaMenu.add(joinMenu);

        joinMenu.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            Patika patika = Patika.fetchPatika(selectedRowId);
            if (patika != null) {
                if (!patika.getStudentIds().contains(student.getId())) {
                    patika.getStudentIds().add(student.getId());
                    Patika.update(patika.getId(), patika.getStudentIds());
                }
            }
            loadPatikaModel();
            loadMyPatikaModel();
            loadLessonList();
        });

        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        loadPatikaModel();

        // My Patikas

        mdl_mypatika_list = Helper.initTableModel();

        myPatikaMenu = new JPopupMenu();
        JMenuItem leaveMenu = new JMenuItem("Bırak");
        myPatikaMenu.add(leaveMenu);

        leaveMenu.addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_mypatika_list.getValueAt(tbl_mypatika_list.getSelectedRow(), 0).toString());
            Patika patika = Patika.fetchPatika(selectedRowId);
            if (patika != null) {
                if (patika.getStudentIds().contains(student.getId())) {
                    patika.getStudentIds().remove(student.getId());
                    Patika.update(patika.getId(), patika.getStudentIds());
                }
            }
            loadPatikaModel();
            loadMyPatikaModel();
            loadLessonList();
        });

        Object[] col_mypatika_list = {"ID", "Patika Adı"};
        mdl_mypatika_list.setColumnIdentifiers(col_mypatika_list);
        row_mypatika_list = new Object[col_mypatika_list.length];

        tbl_mypatika_list.setModel(mdl_mypatika_list);
        tbl_mypatika_list.getTableHeader().setReorderingAllowed(false);
        tbl_mypatika_list.setComponentPopupMenu(myPatikaMenu);
        tbl_mypatika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        loadMyPatikaModel();

        // My Courses

        mdl_mycourses_list = Helper.initTableModel();

        myCoursesMenu = new JPopupMenu();
        JMenuItem showContents = new JMenuItem("İçerikleri Göster");
        myCoursesMenu.add(showContents);

        showContents.addActionListener(e -> {

        });

        Object[] col_mycourses_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_mycourses_list.setColumnIdentifiers(col_mycourses_list);
        row_mycourses_list = new Object[col_mycourses_list.length];

        tbl_mycourses_list.setModel(mdl_mycourses_list);
        tbl_mycourses_list.setComponentPopupMenu(myCoursesMenu);
        tbl_mycourses_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_mycourses_list.getTableHeader().setReorderingAllowed(false);

        loadLessonList();

        btn_logout.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        User user = User.getFetch(10);

        StudentGUI studentGUI = new StudentGUI(user);
    }

    private void loadLessonList() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_mycourses_list.getModel();
        clearTable.setRowCount(0);

        for (Course course : Course.getList()) {
            if (course.getPatika().getStudentIds().contains(student.getId())) {
                toRowItem(course);
            }
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_patika_list.getModel();
        clearTable.setRowCount(0);

        for (Patika patika : Patika.getListWithStudentIds()) {
            toRowItem(patika);
        }

        tbl_patika_list.setModel(mdl_patika_list);
    }

    private void loadMyPatikaModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_mypatika_list.getModel();
        clearTable.setRowCount(0);

        for (Patika patika : Patika.getListWithStudentIds()) {
            if (patika.getStudentIds().contains(student.getId())) {
                toRowItemMyPatika(patika);
            }
        }

        tbl_mypatika_list.setModel(mdl_mypatika_list);
    }

    private void toRowItemMyPatika(Patika patika) {
        int i = 0;
        row_mypatika_list[i++] = patika.getId();
        row_mypatika_list[i++] = patika.getName();
        mdl_mypatika_list.addRow(row_mypatika_list);
    }

    private void toRowItem(Patika patika) {
        int i = 0;
        row_patika_list[i++] = patika.getId();
        row_patika_list[i++] = patika.getName();
        mdl_patika_list.addRow(row_patika_list);
    }

    private void toRowItem(Course course) {
        int i = 0;
        row_mycourses_list[i++] = course.getId();
        row_mycourses_list[i++] = course.getName();
        row_mycourses_list[i++] = course.getLang();
        row_mycourses_list[i++] = course.getPatika().getName();
        row_mycourses_list[i++] = course.getEducator().getName();
        mdl_mycourses_list.addRow(row_mycourses_list);
    }
}
