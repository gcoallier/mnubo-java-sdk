package com.mnubo.java.sdk.client.services;

import static com.mnubo.java.sdk.client.utils.ValidationUtils.notBlank;
import static com.mnubo.java.sdk.client.utils.ValidationUtils.validNotNull;

import java.util.List;

import com.mnubo.java.sdk.client.models.Owner;
import com.mnubo.java.sdk.client.models.result.Result;
import com.mnubo.java.sdk.client.spi.OwnersSDK;

class OwnersSDKServices implements OwnersSDK {

    public static final String OWNER_PATH = "/owners";
    private final SDKService sdkCommonServices;

    OwnersSDKServices(SDKService sdkCommonServices) {
        this.sdkCommonServices = sdkCommonServices;
    }

    @Override
    public void create(Owner owner) {
        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OWNER_PATH)
                                            .build().toString();

        validNotNull(owner, "Owner body");
        notBlank(owner.getUsername(), "usermame cannot be blank.");

        sdkCommonServices.postRequest(url, Owner.class, owner);
    }

    @Override
    public void claim(String username, String deviceId) {
        notBlank(username, "usermame cannot be blank.");
        notBlank(deviceId, "x_deviceId cannot be blank.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OWNER_PATH)
                                            .pathSegment(username, "objects", deviceId, "claim")
                                            .build().toString();

        sdkCommonServices.postRequest(url);
    }

    @Override
    public void update(Owner owner, String username) {
        notBlank(username, "usermame cannot be blank.");

        validNotNull(owner, "Owner body");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OWNER_PATH)
                                            .pathSegment(username)
                                            .build().toString();

        sdkCommonServices.putRequest(url, owner);
    }

    @Override
    public void delete(String username) {
        notBlank(username, "usermame cannot be blank.");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OWNER_PATH)
                                            .pathSegment(username)
                                            .build().toString();

        sdkCommonServices.deleteRequest(url);
    }

    @Override
    public List<Result> createOrUpdateOwners(List<Owner> owners) {
        validNotNull(owners, "List of owners body");

        final String url = sdkCommonServices.getIngestionBaseUri()
                                            .path(OWNER_PATH)
                                            .build().toString();

        return sdkCommonServices.putRequest(url, owners, List.class);
    }
}
