package net.snowlynxsoftware.authservice.dtos;

/**
 * Serialize an exception message or user-defined
 * message to return to the client.
 */
public class ResponseErrorDto {

    private String message;

    public ResponseErrorDto()
    {
    }

    public ResponseErrorDto(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

