package unibuc.twj.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class User {

    private Integer userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
   @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
   @NotBlank
    private String tip;

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(String username, String password, String email, String tip) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.tip = tip;
    }

    public User(Integer userId, @NotBlank String username, @NotBlank String password, String email, String tip) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tip = tip;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
