package net.snowlynxsoftware.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-local.properties")
@ConfigurationProperties(prefix = "auth")
public class AuthConfigProperties {

    private String appDisplayName;
    private String appVerifyRedirect;
    private String appChangePasswordRedirect;
    private int authTokenExpireInHours;
    private int refreshTokenExpireInHours;
    private int defaultTokenMinutes;
    private String claimIpAddress;
    private String jwtSecretKey;
    private String jwtIssuer;
    private String sendgridApiKey;
    private String sendgridContentType;
    private String sendgridMailSendEndpoint;
    private String sendgridFromEmail;

    public AuthConfigProperties(){}

    public String getAppDisplayName() {
        return appDisplayName;
    }

    public void setAppDisplayName(String appDisplayName) {
        this.appDisplayName = appDisplayName;
    }

    public String getAppVerifyRedirect() {
        return appVerifyRedirect;
    }

    public void setAppVerifyRedirect(String appVerifyRedirect) {
        this.appVerifyRedirect = appVerifyRedirect;
    }

    public String getAppChangePasswordRedirect() {
        return appChangePasswordRedirect;
    }

    public void setAppChangePasswordRedirect(String appChangePasswordRedirect) {
        this.appChangePasswordRedirect = appChangePasswordRedirect;
    }

    public int getAuthTokenExpireInHours() {
        return authTokenExpireInHours;
    }

    public void setAuthTokenExpireInHours(int authTokenExpireInHours) {
        this.authTokenExpireInHours = authTokenExpireInHours;
    }

    public int getRefreshTokenExpireInHours() {
        return refreshTokenExpireInHours;
    }

    public void setRefreshTokenExpireInHours(int refreshTokenExpireInHours) {
        this.refreshTokenExpireInHours = refreshTokenExpireInHours;
    }

    public int getDefaultTokenMinutes() {
        return defaultTokenMinutes;
    }

    public void setDefaultTokenMinutes(int defaultTokenMinutes) {
        this.defaultTokenMinutes = defaultTokenMinutes;
    }

    public String getClaimIpAddress() {
        return claimIpAddress;
    }

    public void setClaimIpAddress(String claimIpAddress) {
        this.claimIpAddress = claimIpAddress;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public void setJwtIssuer(String jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

    public String getSendgridApiKey() {
        return sendgridApiKey;
    }

    public void setSendgridApiKey(String sendgridApiKey) {
        this.sendgridApiKey = sendgridApiKey;
    }

    public String getSendgridContentType() {
        return sendgridContentType;
    }

    public void setSendgridContentType(String sendgridContentType) {
        this.sendgridContentType = sendgridContentType;
    }

    public String getSendgridMailSendEndpoint() {
        return sendgridMailSendEndpoint;
    }

    public void setSendgridMailSendEndpoint(String sendgridMailSendEndpoint) {
        this.sendgridMailSendEndpoint = sendgridMailSendEndpoint;
    }

    public String getSendgridFromEmail() {
        return sendgridFromEmail;
    }

    public void setSendgridFromEmail(String sendgridFromEmail) {
        this.sendgridFromEmail = sendgridFromEmail;
    }
}
