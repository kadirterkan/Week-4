package week4.com.patikadev.view;

import week4.com.patikadev.helper.Config;
import week4.com.patikadev.helper.Helper;
import week4.com.patikadev.helper.Item;
import week4.com.patikadev.model.Course;
import week4.com.patikadev.model.Operator;
import week4.com.patikadev.model.Patika;
import week4.com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class OperatorGUI extends JFrame {

    private final User operator;
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JButton btn_exit;
    private JTable tbl_user_list;
    private JScrollPane scrl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JTextField fld_user_password;
    private JComboBox cmb_user_usertype;
    private JButton btn_user_register;
    private JTextField fld_deleteuser_userid;
    private JButton btn_deleteuser;
    private JTextField fld_search_name;
    private JTextField fld_search_username;
    private JButton btn_search;
    private JComboBox cmb_search_usertype;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JTextField fld_patika_name;
    private JPanel pnl_patika_add;
    private JButton btn_add_patika;
    private JPanel pnl_user_list;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl__course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_prog_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private JPopupMenu courseMenu;

    public OperatorGUI(final User operator) {
        this.operator = operator;
        add(wrapper);
        setSize(1000, 500);
        int[] center = Helper.getScreenCenterLocation(getSize());
        setLocation(center[0], center[1]);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        this.lbl_welcome.setText("Hoşgeldiniz " + operator.getName());

        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_usertype = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                User.update(user_id, user_name, user_username, user_password, user_usertype);

                loadUserModel();
            }
        });

        btn_user_register.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)) {
                Helper.showErrorMessage(Config.INFO_FILL_MESSAGE);
            } else {
                String name = fld_user_name.getText();
                String username = fld_user_username.getText();
                String password = fld_user_password.getText();
                String type = cmb_user_usertype.getSelectedItem().toString();

                if (User.add(name, username, password, type)) {
                    Helper.showResultMessage("İşlem başarılı");
                } else {
                    Helper.showErrorMessage("Bir hata oluştu.");
                }
            }
            loadUserModel();
        });

        btn_deleteuser.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_deleteuser_userid)) {
                Helper.showErrorMessage("Kullanıcı ID'si alanı boş bırakılamaz");
            } else {
                if (Helper.showConfirmMessage("Bu işlem geri alınamaz, emin misiniz? ")) {
                    User.deleteUserByUserId(Integer.valueOf(fld_deleteuser_userid.getText()));
                    loadUserModel();
                }
            }
        });

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_deleteuser_userid.setText(selected_user_id);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        btn_search.addActionListener(e -> {
            String username = fld_search_username.getText();
            String name = fld_search_name.getText();
            String type = Objects.requireNonNull(cmb_search_usertype.getSelectedItem()).toString();
            ArrayList<User> userList = User.searchUserList(username, name, type);

            loadUserModel(userList);
        });


        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selected_row_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            Patika patika = Patika.fetchPatika(selected_row_id);
            if (patika != null) {
                UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(patika);
                updatePatikaGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPatikaModel();
                    }
                });
            } else {
                Helper.showErrorMessage("Bir sorun meydana geldi. ");
            }
        });

        deleteMenu.addActionListener(e -> {
            int selected_row_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            Patika patika = Patika.fetchPatika(selected_row_id);
            if (patika != null) {
                if (Helper.showConfirmMessage("Bu işlem geri alınamaz, kabul ediyor musunuz? ")) {
                    Patika.delete(selected_row_id);
                }
            } else {
                Helper.showErrorMessage("Bir sorun meydana geldi. ");
            }
            loadPatikaModel();
        });

        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        btn_exit.addActionListener(e -> {
            dispose();
        });
        btn_add_patika.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showErrorMessage("Tüm değerleri doldurunuz.");
            } else {
                Patika.add(fld_patika_name.getText());
            }
            loadPatikaModel();
        });

        // CourseList

        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadCourseList();
        loadPatikaModel();
        loadUserModel();
        btn_course_add.addActionListener(e -> {
            Item patikateItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_prog_lang)) {
                Helper.showErrorMessage("Lütfen alanları doldurunuz");
            } else {
                String courseName = fld_course_name.getText();
                String progLag = fld_course_prog_lang.getText();
                Course.add(courseName, progLag, patikateItem.getKey(), userItem.getKey());
                loadCourseList();
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Mustafa Çetindağ");
        op.setPassword("1234");
        op.setType("operator");

        OperatorGUI opGUI = new OperatorGUI(op);
    }

    private void loadPatikaCombo() {
        cmb_course_patika.removeAllItems();
        for (Patika patika : Patika.getList()) {
            cmb_course_patika.addItem(new Item(patika.getId(), patika.getName()));
        }
    }

    private void loadUserCombo() {
        cmb_course_user.removeAllItems();
        for (User user : User.getList()) {
            if (user.getType().equals("educator")) {
                cmb_course_user.addItem(new Item(user.getId(), user.getName()));
            }
        }
    }

    private void loadCourseList() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_course_list.getModel();
        clearTable.setRowCount(0);

        for (Course course : Course.getList()) {
            toRowItem(course);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_patika_list.getModel();
        clearTable.setRowCount(0);

        for (Patika patika : Patika.getList()) {
            toRowItem(patika);
        }

        loadPatikaCombo();
    }

    private void loadUserModel() {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_user_list.getModel();
        clearTable.setRowCount(0);

        for (User user : User.getList()) {
            toRowItem(user);
        }

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        loadUserCombo();
    }

    private void loadUserModel(ArrayList<User> userList) {
        DefaultTableModel clearTable = (DefaultTableModel) tbl_user_list.getModel();
        clearTable.setRowCount(0);

        for (User user : userList) {
            toRowItem(user);
        }

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
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

    private void toRowItem(Patika patika) {
        int i = 0;
        row_patika_list[i++] = patika.getId();
        row_patika_list[i++] = patika.getName();
        mdl_patika_list.addRow(row_patika_list);
    }

    private void toRowItem(User user) {
        int i = 0;
        row_user_list[i++] = user.getId();
        row_user_list[i++] = user.getName();
        row_user_list[i++] = user.getUsername();
        row_user_list[i++] = user.getPassword();
        row_user_list[i++] = user.getType();
        mdl_user_list.addRow(row_user_list);
    }
}
