package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private Integer id;
    private Integer user_id;
    private Integer patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;

    public Course(Integer id, Integer user_id, Integer patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika = Patika.fetchPatika(patika_id);
        this.educator = User.getFetch(user_id);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList = new ArrayList<>();
        Course course = null;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");

            while (rs.next()) {
                course = new Course(rs.getInt("ID"),
                        rs.getInt("user_id"),
                        rs.getInt("patika_id"),
                        rs.getString("name"),
                        rs.getString("prog_lang"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }

    public static void add(String courseName, String progLag, Integer patikaId, Integer userId) {
        String query = "INSERT INTO course (name, prog_lang, user_id, patika_id) VALUES (?,?,?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, courseName);
            pr.setString(2, progLag);
            pr.setInt(3, userId);
            pr.setInt(4, patikaId);

            Integer result = pr.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Bir hata meydana geldi. ");
            } else {
                Helper.showResultMessage("İşlem başarıyla gerçekleşti.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Course getFetch(Integer id) {
        String query = "SELECT * FROM course WHERE ID = ? ;";
        Course course = null;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                course = new Course(rs.getInt("ID"),
                        rs.getInt("user_id"),
                        rs.getInt("patika_id"),
                        rs.getString("name"),
                        rs.getString("prog_lang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(Integer patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
