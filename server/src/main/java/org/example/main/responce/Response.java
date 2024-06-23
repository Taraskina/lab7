package org.example.main.responce;

import org.example.main.Message;

public class Response extends Message {
    private boolean success;
    private boolean flag;

    public Response(String commandName, String message) {

    }
    public Response(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
