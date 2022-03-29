package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private Integer id;
    private String name;

    public Patika(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
