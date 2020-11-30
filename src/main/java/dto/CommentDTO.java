package dto;

import entities.Comment;
import entities.Meme;
import entities.User;

import java.util.Date;

public class CommentDTO {

    private String username;
    private String comment;
    private Date dateOfPost;
    private int meme_id;

    public CommentDTO(Comment comment) {
        this.username = comment.getAuthor().getUsername();
        this.comment = comment.getComment();
        this.dateOfPost = comment.getDateOfPost();
        this.meme_id = comment.getMeme().getId();
    }


    public CommentDTO() {
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


    public int getMeme_id() {
        return meme_id;
    }

    public void setMeme_id(int meme_id) {
        this.meme_id = meme_id;
    }
}
