package dto;

import entities.Meme;
import entities.User;
import java.util.List;

public class MemeDTO {

    private String title;
    private String imageUrl;
    private int upvotes;
    private int downvotes;


    public MemeDTO() {
    }
    
    public MemeDTO(Meme meme) {
        this.title = meme.getTitle();
        this.imageUrl = meme.getImage();
        this.upvotes = meme.getUpvoters().size();
        this.downvotes = meme.getDownvoters().size();
    }

    public MemeDTO(FunnyDTO funny) {
        this.title = funny.getTitle();
        this.imageUrl = funny.getUrl();
    }

    public MemeDTO(CatDTO cat) {
        this.title = cat.getId();
        this.imageUrl = cat.getUrl();
    }
    public MemeDTO(YesOrNoDTO yesOrNo){
        this.imageUrl = yesOrNo.getImage();
    }

    public MemeDTO(DogDTO dog) {
        this.imageUrl = dog.getMessage();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
