package org.iq47.exception;

public class TicketSaveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public TicketSaveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
