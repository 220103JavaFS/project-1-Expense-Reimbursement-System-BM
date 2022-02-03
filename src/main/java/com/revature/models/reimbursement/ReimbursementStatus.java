package com.revature.models.reimbursement;

import java.util.Objects;

public class ReimbursementStatus {

    //FIELDS
    private int statusID;
    private String status;

    //CONSTRUCTORS
    public ReimbursementStatus() {}
    public ReimbursementStatus(int statusID, String status) {
        this.statusID = statusID;
        this.status = status;
    }

    //GETTERS AND SETTERS
    public int getStatusID() {
        return statusID;
    }
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    //METHODS FOR JSON
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementStatus that = (ReimbursementStatus) o;
        return statusID == that.statusID && Objects.equals(status, that.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(statusID, status);
    }
    @Override
    public String toString() {
        return "ReimbursementStatus{" +
                "statusID=" + statusID +
                ", status='" + status + '\'' +
                '}';
    }
}
