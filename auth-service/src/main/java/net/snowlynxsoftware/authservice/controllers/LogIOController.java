package net.snowlynxsoftware.authservice.controllers;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.dtos.AuthorizedDto;
import net.snowlynxsoftware.authservice.dtos.UserLoginRequestDto;
import net.snowlynxsoftware.authservice.managers.UserManager;
import net.snowlynxsoftware.authservice.repositories.AuthTokenRepository;
import net.snowlynxsoftware.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(AppConstants.ROUTE_API_PREFIX_V1)
public class LogIOController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Autowired
    AuthConfigProperties authConfigProperties;

    /**
     * Logs a user into the system.
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto loginRequestDto, HttpServletRequest request) {

        // Attempt to authenticate the user request
        AuthorizedDto authorized = UserManager.loginUser(loginRequestDto, userRepository, authTokenRepository, request.getRemoteAddr(), authConfigProperties);

        ResponseEntity res;

        if (authorized != null) {
            res = new ResponseEntity<>(authorized, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return res;

    }

    /**
     * Logs a user out of the system.
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(@RequestHeader(AppConstants.HEADER_AUTH) String authToken) {

            // Attempt a logout.
            boolean isSuccess = UserManager.logoutUser(authToken, authTokenRepository);

            if (isSuccess) {
                return new ResponseEntity<>("LOGOUT_SUCCESS", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE, HttpStatus.BAD_REQUEST);
            }

    }
}
