package com.errorHandlers;

public class ErrorMessage {
    private String message;
    private String error;

    public ErrorMessage() {}

    public ErrorMessage(String message) {
        this.error = "error";
        this.message = message;
    }

    public ErrorMessage(String message, String error) {
        this.message = message;
        this.error = error;
    }
    
    public void setMessage(String message) { this.message = message; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public String getError() { return error; }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ErrorMessage other = (ErrorMessage) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (error == null) {
            if (other.error != null)
                return false;
        } else if (!error.equals(other.error))
            return false;
        return true;
    }
}
