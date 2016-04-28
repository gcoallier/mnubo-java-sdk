package com.mnubo.java.sdk.client.models.result;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {
    public static final String ID = "id";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String id;
    private String result;
    private String message;

    public Result(String id, String result, String message) {
        this.id = id;
        this.result = result;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonIgnore
    public void setResultSuccess() {
        this.result = SUCCESS;
    }

    @JsonIgnore
    public void setResultSuccess(String id) {
        this.id = id;
        this.result = SUCCESS;
    }

    @JsonIgnore
    public void setResultError(String id, String message) {
        this.id = id;
        this.result = ERROR;
        this.message = message;
    }
    @JsonIgnore
    public void setResultError(String message) {
        this.result = ERROR;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("{\n");
        toPrint.append(line2String("id", id));
        toPrint.append(line2String("result", result));
        toPrint.append(line2String("message", message));
        toPrint.append("}\n");
        return toPrint.toString();
    }

    private String line2String(String name, Object value) {
        StringBuilder build = new StringBuilder();
        if (name != null) {
            build.append("     " + name + " : ");
            if (value != null) {
                build.append(value);
            }
            else {
                build.append(PRINT_OBJECT_NULL);
            }
            build.append("\n");
        }
        return build.toString();
    }
}
