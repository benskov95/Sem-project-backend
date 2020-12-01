package dto;

import entities.Meme;
import entities.User;
import java.util.List;

public class MemeDTO {

    private String title;
    private String imageUrl;
    private int upvotes;
    private int downvotes;
    private int meme_id;

    public MemeDTO() {
    }
    
    public MemeDTO(Meme meme) {
        this.title = meme.getTitle();
        this.imageUrl = meme.getImageUrl();
        this.upvotes = meme.getUpvoters().size();
        this.downvotes = meme.getDownvoters().size();
        this.meme_id = meme.getId();
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

    public int getMeme_id() {
        return meme_id;
    }

    public void setMeme_id(int meme_id) {
        this.meme_id = meme_id;
    }

}
