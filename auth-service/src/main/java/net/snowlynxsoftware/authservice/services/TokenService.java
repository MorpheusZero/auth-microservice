package net.snowlynxsoftware.authservice.services;

import io.jsonwebtoken.*;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.models.TokenType;

import java.util.Calendar;

/**
 * Used to handle creation and authenticated JSON Web Tokens
 */
public class TokenService {

    /**
     * Creates a new token of the specified type.
     *
     * @param email
     * @param ipAddress
     * @param tokenType
     * @return
     */
    public static String generateNewToken(String email, String ipAddress, TokenType tokenType, AuthConfigProperties config) {

        // Setup Expire Time
        Calendar cal = Calendar.getInstance();
        switch (tokenType) {
            case AUTH_TOKEN:
                cal.add(Calendar.HOUR_OF_DAY, config.getAuthTokenExpireInHours());
                break;
            case REFRESH_TOKEN:
                cal.add(Calendar.HOUR_OF_DAY, config.getRefreshTokenExpireInHours());
                break;
            default:
                cal.add(Calendar.MINUTE, config.getDefaultTokenMinutes());
                break;
        }

        // Build Token
        String iToken = Jwts.builder()
                .setIssuer(config.getJwtIssuer())
                .setSubject(email)
                .claim(config.getClaimIpAddress(), ipAddress)
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, config.getJwtSecretKey())
                .compact();

        return iToken;
    }

    /**
     * Determines if this token is valid or not.
     * @param token
     * @param requestIpAddress
     * @return
     */
    //TODO: make sure we use the secret key to authenticate a token
    public static boolean isTokenAuthentic(String token, String requestIpAddress, AuthConfigProperties config) {
        try {

            Jwts.parser()
                    .require(config.getClaimIpAddress(), requestIpAddress)
                    .setSigningKey(config.getJwtSecretKey())
                    .parseClaimsJws(token);

            return true;

        } catch (MissingClaimException e) {
            System.out.printf(e.getMessage());
            return false;
        } catch (IncorrectClaimException e) {
            System.out.printf(e.getMessage());
            return true;
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the email address associated with this token.
     * @param token
     * @return
     */
    public static String getTokenSubject(String token, AuthConfigProperties config) {
        try {

            return Jwts.parser()
                    .setSigningKey(config.getJwtSecretKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (MissingClaimException e) {
            System.out.printf(e.getMessage());
            return null;
        } catch (IncorrectClaimException e) {
            System.out.printf(e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return null;
        }
    }
}




