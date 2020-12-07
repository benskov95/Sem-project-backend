package dto;

public class ReportDTO {

    private String description;
    private int meme_id;

    public ReportDTO() {
    }

    public ReportDTO(String description, int meme_id) {
        this.description = description;
        this.meme_id = meme_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMeme_id() {
        return meme_id;
    }

    public void setMeme_id(int meme_id) {
        this.meme_id = meme_id;
    }
}
