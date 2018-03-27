package net.snowlynxsoftware.authservice.controllers;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.dtos.AuthorizedDto;
import net.snowlynxsoftware.authservice.managers.TokenManager;
import net.snowlynxsoftware.authservice.repositories.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(AppConstants.ROUTE_API_PREFIX_V1)
public class AuthenticateController {

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    AuthConfigProperties authConfigProperties;

    /**
     * Returns whether or not the token is authentic.
     * @return
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public ResponseEntity<?> authenticate(
            @RequestHeader(AppConstants.HEADER_AUTH) String authToken,
            @RequestHeader(AppConstants.HEADER_REFRESH) String refreshToken,
            HttpServletRequest request) {

        // Ensure tokens were passed as headers.
        if(authToken == null || authToken.isEmpty()){
            return new ResponseEntity<>(AppConstants.STATUS_CODE_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        if(refreshToken == null || refreshToken.isEmpty()){
            return new ResponseEntity<>(AppConstants.STATUS_CODE_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        // Attempt to authenticate the tokens.
        AuthorizedDto attempt = TokenManager.validateToken(authToken, refreshToken, request.getRemoteAddr(), authTokenRepository, authConfigProperties);

        if (attempt != null) {
            // Request Is Authentic
            return new ResponseEntity<>(attempt, HttpStatus.OK);
        } else {
            // The tokens could not be authenticated.
            return new ResponseEntity<>(AppConstants.STATUS_CODE_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }
}
