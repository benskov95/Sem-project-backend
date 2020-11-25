package dto;

import entities.Meme;
import entities.User;

import java.util.List;

public class UserDTO {

    private String username;
    private List<String> roles;
    private String password;
    private String profilePicture;
    private List<Meme> upvotedMemes;
    private List<Meme> downvotedMemes;


    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = user.getRolesAsStrings();
        this.profilePicture = user.getProfilePicture();
        this.upvotedMemes = user.getUpvotedMemes();
        this.downvotedMemes = user.getDownvotedMemes();
    }

    public UserDTO(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Meme> getUpvotedMemes() {
        return upvotedMemes;
    }

    public void setUpvotedMemes(List<Meme> upvotedMemes) {
        this.upvotedMemes = upvotedMemes;
    }

    public List<Meme> getDownvotedMemes() {
        return downvotedMemes;
    }

    public void setDownvotedMemes(List<Meme> downvotedMemes) {
        this.downvotedMemes = downvotedMemes;
    }
  
}
