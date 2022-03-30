package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {

    private Integer id;
    private String baslik;
    private String aciklama;
    private String youtubeUrl;
    private Integer quizId;
    private Integer dersId;

    private Course ders;
    private Quiz quiz;

    public Content(Integer id, String baslik, String aciklama, String youtubeUrl, Integer quizId, Integer dersId) {
        this.id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.youtubeUrl = youtubeUrl;
        this.quizId = quizId;
        this.dersId = dersId;
        this.ders = Course.getFetch(dersId);
        this.quiz = Quiz.getFetch(quizId);
    }

    public Content(Integer id, String baslik, String aciklama, String youtubeUrl, Integer dersId) {
        this.id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.youtubeUrl = youtubeUrl;
        this.dersId = dersId;
        this.ders = Course.getFetch(dersId);
    }

    public static void add(String baslik, String aciklama, String youtubeUrl, Integer dersId) {
        String query = "INSERT INTO icerik (baslik, aciklama, youtube_url, ders_id) VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, baslik);
            pr.setString(2, aciklama);
            pr.setString(3, youtubeUrl);
            pr.setInt(4, dersId);

            Integer result = pr.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Bir hata meydana geldi. ");
            } else {
                Helper.showResultMessage("İşlem başarıyla gerçekleşti. ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Content> getList() {
        ArrayList<Content> contentList = new ArrayList<>();
        String query = "SELECT * FROM icerik";
        Content content = null;

        try {
            Statement st = DBConnector.getInstance().createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                content = new Content(
                        rs.getInt("ID"),
                        rs.getString("baslik"),
                        rs.getString("aciklama"),
                        rs.getString("youtube_url"),
                        rs.getInt("quiz_id"),
                        rs.getInt("ders_id")
                );
                contentList.add(content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contentList;
    }

    public static ArrayList<Content> getList(final Integer courseId) {
        ArrayList<Content> contentList = new ArrayList<>();
        String query = "SELECT * FROM icerik WHERE ders_id = ?";
        Content content = null;

        try {
            PreparedStatement st = DBConnector.getInstance().prepareStatement(query);
            st.setInt(1, courseId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                content = new Content(
                        rs.getInt("ID"),
                        rs.getString("baslik"),
                        rs.getString("aciklama"),
                        rs.getString("youtube_url"),
                        rs.getInt("quiz_id"),
                        rs.getInt("ders_id")
                );
                contentList.add(content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contentList;
    }

    public static void update(Integer id, String baslik, String aciklama, String youtubeUrl, Integer dersId) {
        String query = "UPDATE icerik SET baslik = ?, aciklama = ?, youtube_url = ?, ders_id = ? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, baslik);
            pr.setString(2, aciklama);
            pr.setString(3, youtubeUrl);
            pr.setInt(4, dersId);
            pr.setInt(5, id);

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

    public static Content getFetch(int contentId) {
        String query = "SELECT * FROM icerik WHERE id = ? ;";
        Content content = null;

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, contentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                content = new Content(rs.getInt("ID"),
                        rs.getString("baslik"),
                        rs.getString("aciklama"),
                        rs.getString("youtube_url"),
                        rs.getInt("ders_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static void delete(int selectedRowId) {
        String query = "DELETE FROM icerik WHERE ID = ? ;";

        Quiz.deleteAll(selectedRowId);

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, selectedRowId);

            Integer result = ps.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Bir hata meydana geldi. ");
            } else {
                Helper.showResultMessage("Başarıyla işlem gerçekleşti.");
            }
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

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getDersId() {
        return dersId;
    }

    public void setDersId(Integer dersId) {
        this.dersId = dersId;
    }

    public Course getDers() {
        return ders;
    }

    public void setDers(Course ders) {
        this.ders = ders;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
