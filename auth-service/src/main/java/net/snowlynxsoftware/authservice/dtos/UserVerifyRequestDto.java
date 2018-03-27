package net.snowlynxsoftware.authservice.dtos;

/**
 * Sent by the client to verify a new account.
 */
public class UserVerifyRequestDto extends UserRegisterRequestDto {

    private int authCode;

    public UserVerifyRequestDto() {
        super();
    }

    public int getAuthCode() {
        return authCode;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }
}
