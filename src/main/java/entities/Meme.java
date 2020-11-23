package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery (name = "Meme.deleteAllRows", query = "DELETE FROM Meme")
@Table(name = "meme")
public class Meme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "meme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "image_url")
    private String image;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "upvotes")
    private int upvotes;
    
    @Column(name = "downvotes")
    private int downvotes;
    
    @OneToMany(mappedBy = "meme", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    public Meme() {
    }

    public Meme(String image, String title) {
        this.image = image;
        this.title = title;
        this.upvotes = 0;
        this.downvotes = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
    
}
