package com.mnubo.java.sdk.client.models.result;

import static com.mnubo.java.sdk.client.Constants.PRINT_OBJECT_NULL;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {

    public enum ResultStates {

        Success("success"), 
        Error("error");

        private final String resultState;

        private ResultStates(String state) {
            resultState = state;
        }

        public boolean equalsResultState(String otherState) {
            return (otherState == null) ? false : resultState.equals(otherState);
        }

        @Override
        public String toString() {
            return this.resultState;
        }
    }

    public static final String ID = "id";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";

    private String id;
    private ResultStates result;
    private String message;

    public Result(String id, ResultStates result, String message) {
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

    public ResultStates getResult() {
        return result;
    }

    public void setResult(ResultStates result) {
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
        this.result = ResultStates.Success;
    }

    @JsonIgnore
    public void setResultSuccess(String id) {
        this.id = id;
        this.result = ResultStates.Success;
    }

    @JsonIgnore
    public void setResultError(String id, String message) {
        this.id = id;
        this.result = ResultStates.Error;
        this.message = message;
    }
    @JsonIgnore
    public void setResultError(String message) {
        this.result = ResultStates.Error;
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
