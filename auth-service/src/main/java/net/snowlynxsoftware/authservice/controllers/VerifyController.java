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
public class VerifyController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthConfigProperties authConfigProperties;

    /**
     * Verify a new user in the system.
     * @param verifyRequestDto
     * @return
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity<?> verifyNewUser(@RequestBody UserVerifyRequestDto verifyRequestDto) {

        boolean isSuccess = false;
        ResponseErrorDto mes;

        try {
            isSuccess = UserManager.verifyNewUser(verifyRequestDto, userRepository, authConfigProperties);
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
