package week4.com.patikadev.model;

import week4.com.patikadev.helper.DBConnector;
import week4.com.patikadev.helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Quiz {

    private Integer id;
    private String[] questions;
    private Integer course_id;

    private Content content;

    public Quiz(Integer id, String[] questions, Integer contentId) {
        this.id = id;
        this.questions = questions;
        this.course_id = contentId;
        this.content = Content.getFetch(contentId);
    }

    public static void add(String[] questions, final Integer courseId) {
        String query = "INSERT INTO quiz (questions, course_id) VALUES (?, ?);";

        try {
            Array arrayQuestion = DBConnector.getInstance().createArrayOf("text", questions);
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setArray(1, arrayQuestion);
            pr.setInt(2, courseId);

            Integer result = pr.executeUpdate();

            if (result.equals(-1)) {
                Helper.showErrorMessage("Bir hata meydana geldi");
            } else {
                Helper.showResultMessage("İşlem başarıyla gerçekleşti");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Quiz> getList() {
        ArrayList<Quiz> quizList = new ArrayList<>();
        String query = "SELECT * FROM quiz;";
        Quiz quiz = null;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String[] questionsArray = (String[]) rs.getArray("questions").getArray();
                Integer id = rs.getInt("id");
                Integer courseId = rs.getInt("course_id");
                quiz = new Quiz(id, questionsArray, courseId);
                quizList.add(quiz);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizList;
    }

    public static Quiz getFetch(Integer quizId) {
        String query = "SELECT * FROM quiz where ID = ? ;";
        Quiz quiz = null;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, quizId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                String[] questionsArray = (String[]) rs.getArray("questions").getArray();
                Integer id = rs.getInt("id");
                Integer courseId = rs.getInt("course_id");
                quiz = new Quiz(id, questionsArray, courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public static void delete(Integer quizId) {
        String query = "DELETE FROM quiz WHERE ID = ? ;";
        Quiz quiz = null;

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, quizId);

            Helper.resultMessage(ps.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll(Integer contentId) {
        String query = "DELETE FROM quiz WHERE course_id = ? ;";
        Quiz quiz = null;

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1, contentId);

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

    public String getQuestions() {
        String str = "[";

        for (String question : this.questions) {
            str += question;
            str += " ";
        }
        str += "]";
        return str;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
