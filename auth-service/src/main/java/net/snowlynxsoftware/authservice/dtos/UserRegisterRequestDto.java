package net.snowlynxsoftware.authservice.dtos;

/**
 * Request to create a new user.
 */
public class UserRegisterRequestDto {

    private String email;
    private String password;

    public UserRegisterRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
