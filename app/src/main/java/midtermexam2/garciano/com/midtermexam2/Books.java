package midtermexam2.garciano.com.midtermexam2;

/**
 * Created by admin on 2/23/2016.
 */
public class Books {
    private String id;
    private String title;
    private String genre;
    private String author;
    private Boolean isread;

    public Books(String id, String title, String genre, String author, Boolean isread) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.isread = isread;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }
}
