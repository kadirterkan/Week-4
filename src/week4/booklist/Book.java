package week4.booklist;

import java.util.Date;

public class Book {
    private String name;
    private Integer pageNumber;
    private String authorName;
    private Date publish;

    public Book(String name, Integer pageNumber, String authorName, Date publish) {
        this.name = name;
        this.pageNumber = pageNumber;
        this.authorName = authorName;
        this.publish = publish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getPublish() {
        return publish;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", pageNumber=" + pageNumber +
                ", authorName='" + authorName + '\'' +
                ", publish=" + publish +
                '}';
    }
}
