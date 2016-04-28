package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.services.EventsSDKServices.EVENT_PATH;
import static com.mnubo.java.sdk.client.services.EventsSDKServices.EVENT_PATH_SEGMENT;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.spi.EventsSDK;

public class EventsSDKServicesTest extends AbstractServiceTest {
    private EventsSDK objectClient;

    @Before
    public void eventSetup() {
        objectClient = getClient().getEventClient();

        List<Result> resultsMockSetup = new ArrayList<>();
        resultsMockSetup.add(new Result("idEventTest1", "sucess", ""));
        resultsMockSetup.add(new Result("idEventResult2", "error", "Object for the Event doesn't exist"));
        resultsMockSetup.add(new Result("idEventTest3", "error", "Other error for test"));
        resultsMockSetup.add(new Result("idEventTest4", "sucess", ""));

        // Mock Call to POST Events
        when(restTemplate.postForObject(any(String.class), any(Object.class), eq(List.class)))
                         .thenReturn(resultsMockSetup);
    }

    @Test
    public void postListEventThenOk() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());

        String url = getClient().getSdkService().getIngestionBaseUri().path(EVENT_PATH).build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/events",HOST))));

        List<Result> results = objectClient.send(events);

        validateResult(results);
    }


    @Test
    public void postListEventListNullThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(null);
    }

    @Test
    public void postListEventListEmptyThenFail() {

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(new ArrayList<Event>());
    }

    @Test
    public void postListEventWithDeviceIdThenOk() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());

        String deviceId = "deviceId";

        String url = getClient().getSdkService().getIngestionBaseUri().path(OBJECT_PATH).pathSegment(deviceId, EVENT_PATH_SEGMENT)
                .build().toString();

        assertThat(url, is(equalTo(format("https://%s:443/api/v3/objects/%s/events",HOST, deviceId))));

        List<Result> results = objectClient.send(deviceId, events);

        validateResult(results);
    }

    @Test
    public void postListEventNullThenFail() {

        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(deviceId, null);
    }

    @Test
    public void postListEventEmptyThenFail() {

        String deviceId = "deviceId";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Event list cannot be null or empty.");
        objectClient.send(deviceId, new ArrayList<Event>());
    }

    @Test
    public void postListEventWithDeviceIdNullThenFail() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());
        String deviceId = null;

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.send(deviceId, events);
    }

    @Test
    public void postListEventWithDeviceIdEmptyThenFail() {

        List<Event> events = new ArrayList<>();

        events.add(Event.builder().withEventType("type").build());
        String deviceId = "";

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("device_Id cannot be blank.");
        objectClient.send(deviceId, events);
    }

    private void validateResult(List<Result> results) {
        assertThat(results.size(), equalTo(4));

        assertThat(results.get(0).getId(), equalTo("idEventTest1"));
        assertThat(results.get(1).getId(), equalTo("idEventResult2"));
        assertThat(results.get(2).getId(), equalTo("idEventTest3"));
        assertThat(results.get(3).getId(), equalTo("idEventTest4"));

        assertThat(results.get(0).getResult(), equalTo("sucess"));
        assertThat(results.get(1).getResult(), equalTo("error"));
        assertThat(results.get(2).getResult(), equalTo("error"));
        assertThat(results.get(3).getResult(), equalTo("sucess"));

        assertThat(results.get(0).getMessage(), equalTo(""));
        assertThat(results.get(1).getMessage(), equalTo("Object for the Event doesn't exist"));
        assertThat(results.get(2).getMessage(), equalTo("Other error for test"));
        assertThat(results.get(3).getMessage(), equalTo(""));
    }
}
