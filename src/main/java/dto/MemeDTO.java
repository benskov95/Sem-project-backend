package dto;

import entities.Meme;

public class MemeDTO {

    private String title;
    private String imageUrl;

    public MemeDTO() {
    }
    
    public MemeDTO(Meme meme) {
        this.title = meme.getTitle();
        this.imageUrl = meme.getImageUrl();
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

}
