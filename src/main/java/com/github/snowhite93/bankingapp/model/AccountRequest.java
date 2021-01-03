package com.github.snowhite93.bankingapp.model;

public class AccountRequest {

    private int requestId;
    private int userId;
    private String requestStatus;
    private String rejectionReason;

    public AccountRequest() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "requestId=" + requestId +
                ", userId=" + userId +
                ", requestStatus='" + requestStatus + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }

}
