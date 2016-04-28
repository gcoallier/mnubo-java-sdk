package com.mnubo.java.sdk.client.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.models.result.Result.ResultStates;

public class ResultTest {

    @Test
    public void whenCreatingResult_assertValueOK() {
        Result sut = new Result("idTest", ResultStates.Success, "message Test");
        assertTrue(sut.getId().equals("idTest"));
        assertTrue(sut.getResult().equals(ResultStates.Success));
        assertTrue(sut.getMessage().equals("message Test"));
    }
}
