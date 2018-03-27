package net.snowlynxsoftware.authservice.controllers;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.dtos.ResponseErrorDto;
import net.snowlynxsoftware.authservice.dtos.UserVerifyRequestDto;
import net.snowlynxsoftware.authservice.managers.UserManager;
import net.snowlynxsoftware.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.ROUTE_API_PREFIX_V1)
public class PasswordController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthConfigProperties authConfigProperties;

    /**
     * Request to reset a users password.
     * @return
     */
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        if (email != null && email != "") {
            boolean isSuccess = UserManager.forgotPasswordRequest(email, userRepository, authConfigProperties);

            if (isSuccess) {
                // Request Is Authentic
                return new ResponseEntity<>(isSuccess, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Request to change the users password.
     * @return
     */
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody UserVerifyRequestDto requestDto) {

        boolean isSuccess = false;
        ResponseErrorDto mes;

        try {
            isSuccess = UserManager.changeUserPassword(requestDto, userRepository, authConfigProperties);
            if (isSuccess) {
                mes = new ResponseErrorDto(AppConstants.STATUS_CODE_OK_MESSAGE);
            } else {
                mes = new ResponseErrorDto(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE);
            }
        } catch(Exception ex) {
            mes = new ResponseErrorDto(AppConstants.STATUS_CODE_BAD_REQUEST_MESSAGE);
            System.out.printf(ex.getMessage());
        }

        ResponseEntity res;

        if (isSuccess) {
            res = new ResponseEntity<>(mes, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(mes, HttpStatus.BAD_REQUEST);
        }

        return res;
    }
}
