package week4.com.patikadev.model;

public class Review {

    private Integer id;
    private Integer score;
    private String comment;

    public Review(Integer id, Integer score, String comment) {
        this.id = id;
        this.score = score;
        this.comment = comment;
    }

    public Review(Integer score, String comment) {
        this.score = score;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
