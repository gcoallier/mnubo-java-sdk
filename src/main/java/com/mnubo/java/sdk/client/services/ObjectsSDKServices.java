package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.Constants.OBJECT_PATH;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import java.util.List;

import com.mnubo.java.sdk.client.models.SmartObject;
import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.spi.ObjectsSDK;

class ObjectsSDKServices implements ObjectsSDK {

    private final SDKService sdkCommonServices;

    ObjectsSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void create(SmartObject object) {
        validNotNull(object, "Object body");
        notBlank(object.getObjectType(), "x_object_Type cannot be blank.");
        notBlank(object.getDeviceId(), "x_device_Id cannot be blank.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OBJECT_PATH)
                                            .build().toString();

        sdkCommonServices. postRequest(url, SmartObject.class, object);
    }

    @Override
    public void update(SmartObject object, String deviceId) {
        notBlank(deviceId, "x_device_Id cannot be blank.");
        validNotNull(object, "Object body");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OBJECT_PATH)
                                            .pathSegment(deviceId)
                                            .build().toString();

        sdkCommonServices.putRequest(url, object);
    }

    @Override
    public void delete(String deviceId) {
        notBlank(deviceId, "x_device_Id cannot be blank.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OBJECT_PATH)
                                            .pathSegment(deviceId)
                                            .build().toString();

        sdkCommonServices.deleteRequest(url);
    }

    @Override
    public List<Result> createOrUpdateSmartObjects(List<SmartObject> objects) {
        validNotNull(objects, "List of smart objects body");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OBJECT_PATH)
                                            .build().toString();

        return sdkCommonServices.putRequest(url, objects, List.class);
    }
}
