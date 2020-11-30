package dto;

import entities.Comment;
import entities.Meme;
import entities.User;

import java.util.Date;

public class CommentDTO {

    private String username;
    private String comment;
    private Date dateOfPost;
    private String memeId;

    public CommentDTO(Comment comment, User user, Meme meme) {
        this.username = user.getUsername();
        this.comment = comment.getComment();
        this.dateOfPost = comment.getDateOfPost();
        this.memeId = meme.getImage();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(Date dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public String getMemeId() {
        return memeId;
    }

    public void setMemeId(String memeId) {
        this.memeId = memeId;
    }
}
