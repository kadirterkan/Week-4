package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {

    private Integer id;
    private String name;
    private String username;
    private String password;
    private String type;

    public User() {
    }

    public User(Integer id, String name, String username, String password, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public static ArrayList<User> getList() {
        String query = "SELECT * FROM \"USER\"";

        return getListWithQuery(query);
    }

    public static boolean add(String name, String username, String password, String type) {
        String query = "INSERT INTO \"USER\"(name, username, password, user_type)" +
                "VALUES (?,?,?,?::user_types);";
        User findUser = getFetch(username);
        if (findUser != null) {
            Helper.showErrorMessage("Bu kullanıcı adı daha önceden kulanılmış, başka bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void update(int id, String name, String username, String password, String type) {
        String query = "UPDATE \"USER\" SET name=?,username=?,password=?,user_type=?::user_types WHERE id=?";
        User findUser = getFetch(username);
        if (findUser != null && findUser.getId() != id) {
            Helper.showErrorMessage("Bu kullanıcı adı daha önceden kulanılmış, başka bir kullanıcı adı giriniz.");
        } else {
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1, name);
                pr.setString(2, username);
                pr.setString(3, password);
                pr.setString(4, type);
                pr.setInt(5, id);

                int result = pr.executeUpdate();

                if (result == -1) {
                    Helper.showErrorMessage("İşlem gerçekleştirilirken hata meydana geldi.");
                } else if (result == 1) {
                    Helper.showResultMessage("İşlem başarıyla gerçekleştirildi.");
                } else {
                    Helper.showErrorMessage("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static User getFetch(String username) {
        User user = null;
        String sql = "SELECT * FROM \"USER\" WHERE USERNAME = ? ;";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = convertResultDataToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getFetch(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM \"USER\" WHERE USERNAME = ? AND PASSWORD = ?;";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = convertResultDataToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getFetch(Integer id) {
        User user = null;
        String sql = "SELECT * FROM \"USER\" WHERE ID = ? ;";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = convertResultDataToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void deleteUserByUserId(Integer id) {
        String sql = "DELETE FROM \"USER\" WHERE ID = ?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            Integer test = preparedStatement.executeUpdate();

            if (test == -1) {
                Helper.showErrorMessage("Kullanıcı silinemedi");
            } else if (test == 0) {
                Helper.showErrorMessage("Kullanıcı bulunamadı");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<User> searchUserList(String username, String name, String type) {
        String query = getSearchQuery(username, name, type);

        return getListWithQuery(query);
    }

    private static ArrayList<User> getListWithQuery(String query) {
        ArrayList<User> userArrayList = new ArrayList<>();
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                userArrayList.add(convertResultDataToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    private static User convertResultDataToUser(ResultSet resultSet) {
        try {
            return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("username"),
                    resultSet.getString("password"), resultSet.getString("user_type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSearchQuery(String username, String name, String type) {
        String query = "SELECT * FROM \"USER\" WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%' AND user_type='{{user_type}}'";
        query = query.replace("{{username}}", username);
        query = query.replace("{{name}}", name);
        query = query.replace("{{user_type}}", type);
        return query;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
