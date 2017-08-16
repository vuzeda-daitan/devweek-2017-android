package com.vruzeda.wanikani.api.model;

public class BaseResponse<RequestedInformation> {

    private UserInformation userInformation;
    private RequestedInformation requestedInformation;

    public BaseResponse(UserInformation userInformation, RequestedInformation requestedInformation) {
        this.userInformation = userInformation;
        this.requestedInformation = requestedInformation;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public RequestedInformation getRequestedInformation() {
        return requestedInformation;
    }
}
