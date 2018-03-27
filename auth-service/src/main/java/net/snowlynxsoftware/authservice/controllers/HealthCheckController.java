package net.snowlynxsoftware.authservice.controllers;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.dtos.HealthCheckResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(AppConstants.ROUTE_API_PREFIX_V1)
public class HealthCheckController {

    /**
     * Checks the health status of the application.
     * @return
     */
    @RequestMapping(value = "/health-check", method = RequestMethod.GET)
    public ResponseEntity<?> healthCheck(HttpServletRequest request) {
        return new ResponseEntity<>(new HealthCheckResponseDto(request.getRemoteAddr()), HttpStatus.OK);
    }
}
