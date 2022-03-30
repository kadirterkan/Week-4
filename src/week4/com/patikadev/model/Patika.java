package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class Patika {
    private Integer id;
    private String name;
    private ArrayList<Integer> studentIds;

    private ArrayList<User> studentList;

    public Patika(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.studentIds = new ArrayList<>();
        this.studentList = new ArrayList<>();
    }

    public Patika(Integer id, String name, ArrayList<Integer> studentIds) {
        this.id = id;
        this.name = name;
        this.studentIds = studentIds;
        this.studentList = new ArrayList<>();
    }

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika");

            while (rs.next()) {
                obj = new Patika(rs.getInt("ID"), rs.getString("name"));
                patikaList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patikaList;
    }

    public static ArrayList<Patika> getListWithStudentIds() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika patika = null;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika");

            while (rs.next()) {
                Integer[] studentIds = (Integer[]) rs.getArray("students").getArray();
                ArrayList<Integer> studentIdlist = new ArrayList<>();
                Collections.addAll(studentIdlist, studentIds);
                patika = new Patika(rs.getInt("ID"), rs.getString("name"), studentIdlist);
                patikaList.add(patika);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patikaList;
    }

    public static void add(String name) {
        String query = "INSERT INTO patika (name) VALUES (?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);

            Integer result = pr.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Hata");
            } else {
                Helper.showResultMessage("Patika başarıyla eklendi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Integer id, String name) {
        String query = "UPDATE patika SET name=? WHERE id=?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, id);

            Integer result = pr.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Hata meydana geldi");
            } else {
                Helper.showResultMessage("İşlem başarıyla tamamlandı");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Patika fetchPatika(Integer id) {
        Patika patika = null;
        String query = "SELECT * FROM patika WHERE ID=?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Integer[] studentIds = (Integer[]) rs.getArray("students").getArray();
                ArrayList<Integer> studentIdlist = new ArrayList<>();
                Collections.addAll(studentIdlist, studentIds);
                patika = new Patika(rs.getInt("ID"), rs.getString("name"), studentIdlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patika;
    }

    public static void delete(Integer id) {
        String sql = "DELETE FROM patika WHERE ID = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            Integer test = preparedStatement.executeUpdate();

            if (test == -1) {
                Helper.showErrorMessage("Patika silinemedi");
            } else if (test == 0) {
                Helper.showErrorMessage("Patika bulunamadı");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Integer id, ArrayList<Integer> studentIds) {
        String query = "UPDATE patika SET students = ? WHERE id = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            Array students = DBConnector.getInstance().createArrayOf("integer", studentIds.toArray());
            ps.setArray(1, students);
            ps.setInt(2, id);

            Helper.resultMessage(ps.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<User> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(ArrayList<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
