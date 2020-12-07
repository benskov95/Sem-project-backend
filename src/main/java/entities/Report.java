package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @ManyToOne
    private Meme meme;


    public Report() {
    }

    public Report(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Meme getMeme() {
        return meme;
    }

    public void setMeme(Meme meme) {
        this.meme = meme;
    }
}