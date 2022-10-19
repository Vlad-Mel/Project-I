package com.ErrorHandlers;

public class MessageResponse {
    private String type;
    private String message;

    public MessageResponse() {}

    public MessageResponse(String message) {
        this.type = "error";
        this.message = message;
    }

    public MessageResponse(String message, String type) {
        this.message = message;
        this.type = type;
    }
    
    public void setMessage(String message) { this.message = message; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public String getType() { return type; }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        MessageResponse other = (MessageResponse) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
