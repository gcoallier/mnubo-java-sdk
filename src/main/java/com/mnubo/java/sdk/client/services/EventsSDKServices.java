package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notNullNorEmpty;

import java.util.List;

import com.mnubo.java.sdk.client.models.Event;
import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.spi.EventsSDK;

class EventsSDKServices implements EventsSDK {

    public static final String EVENT_PATH = "/events";
    public static final String EVENT_PATH_SEGMENT = "events";
    private static final String REPORT_RESULTS_QUERY_PARAM = "report_results";
    private final SDKService sdkCommonServices;

    EventsSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public List<Result> send(String deviceId, List<Event> events) {

        notBlank(deviceId, "x_device_Id cannot be blank.");
        notNullNorEmpty(events, "Event list cannot be null or empty.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OBJECT_PATH)
                                            .pathSegment(deviceId, EVENT_PATH_SEGMENT)
                                            .queryParam(REPORT_RESULTS_QUERY_PARAM, true)
                                            .build().toString();

        return sdkCommonServices.postRequest(url, List.class, events);
    }

    @Override
    public List<Result> send(List<Event> events) {

        notNullNorEmpty(events, "Event list cannot be null or empty.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(EVENT_PATH)
                                            .queryParam(REPORT_RESULTS_QUERY_PARAM, true)
                                            .build().toString();

        return sdkCommonServices.postRequest(url, List.class, events);
    }
}
