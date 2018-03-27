package net.snowlynxsoftware.authservice.dtos;

/**
 * A generic healthcheck response object to return to
 * the client if the service is available.
 */
public class HealthCheckResponseDto {

    private String status;
    private String ip;

    public HealthCheckResponseDto(String ip) {
        this.status = "OK";
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
