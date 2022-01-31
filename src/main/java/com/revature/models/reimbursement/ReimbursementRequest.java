package com.revature.models.reimbursement;

import com.revature.models.users.User;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class ReimbursementRequest {

    //FIELDS
    private int reimbursementID;
    private double reimbursementAmount;
    private Timestamp reimbursementSubmitted;
    private Timestamp reimbursementResolved;
    private String reimbursementDescription;
    private byte[] reimbursementReceipt;
    private int reimbursementAuthor;
    private int reimbursementResolver;
    private int reimbursementStatusId;
    private int reimbursementTypeId;

    //initially used model classes to represent the below items but realized it would be much more easy (and better match what's in the database) to just use integers
    //corresponding to the unique id's for each object


    //CONSTRUCTORS
    public ReimbursementRequest() {}
    public ReimbursementRequest(int reimbursementID, double reimbursementAmount, Timestamp reimbursementSubmitted, Timestamp reimbursementResolved, String reimbursementDescription, byte[] reimbursementReceipt, int reimbursementAuthor, int reimbursementResolver, int reimbursementStatusId, int reimbursementTypeId) {
        this.reimbursementID = reimbursementID;
        this.reimbursementAmount = reimbursementAmount;
        this.reimbursementSubmitted = reimbursementSubmitted;
        this.reimbursementResolved = reimbursementResolved;
        this.reimbursementDescription = reimbursementDescription;
        this.reimbursementReceipt = reimbursementReceipt;
        this.reimbursementAuthor = reimbursementAuthor;
        this.reimbursementResolver = reimbursementResolver;
        this.reimbursementStatusId = reimbursementStatusId;
        this.reimbursementTypeId = reimbursementTypeId;
    }

    //GETTERS AND SETTERS
    public int getReimbursementID() {
        return reimbursementID;
    }
    public void setReimbursementID(int reimbursementID) {
        this.reimbursementID = reimbursementID;
    }
    public double getReimbursementAmount() {
        return reimbursementAmount;
    }
    public void setReimbursementAmount(double reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }
    public Timestamp getReimbursementSubmitted() {
        return reimbursementSubmitted;
    }
    public void setReimbursementSubmitted(Timestamp reimbursementSubmitted) {
        this.reimbursementSubmitted = reimbursementSubmitted;
    }
    public Timestamp getReimbursementResolved() {
        return reimbursementResolved;
    }
    public void setReimbursementResolved(Timestamp reimbursementResolved) {
        this.reimbursementResolved = reimbursementResolved;
    }
    public String getReimbursementDescription() {
        return reimbursementDescription;
    }
    public void setReimbursementDescription(String reimbursementDescription) {
        this.reimbursementDescription = reimbursementDescription;
    }
    public byte[] getReimbursementReceipt() {
        return reimbursementReceipt;
    }
    public void setReimbursementReceipt(byte[] reimbursementReceipt) {
        this.reimbursementReceipt = reimbursementReceipt;
    }
    public int getReimbursementAuthor() {
        return reimbursementAuthor;
    }
    public void setReimbursementAuthor(int reimbursementAuthor) {
        this.reimbursementAuthor = reimbursementAuthor;
    }
    public int getReimbursementResolver() {
        return reimbursementResolver;
    }
    public void setReimbursementResolver(int reimbursementResolver) {
        this.reimbursementResolver = reimbursementResolver;
    }
    public int getReimbursementStatusId() {
        return reimbursementStatusId;
    }
    public void setReimbursementStatusId(int reimbursementStatusId) {
        this.reimbursementStatusId = reimbursementStatusId;
    }
    public int getReimbursementTypeId() {
        return reimbursementTypeId;
    }
    public void setReimbursementTypeId(int reimbursementTypeId) {
        this.reimbursementTypeId = reimbursementTypeId;
    }

    //METHODS FOR JSON

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementRequest that = (ReimbursementRequest) o;
        return reimbursementID == that.reimbursementID && Double.compare(that.reimbursementAmount, reimbursementAmount) == 0 && reimbursementAuthor == that.reimbursementAuthor && reimbursementResolver == that.reimbursementResolver && reimbursementStatusId == that.reimbursementStatusId && reimbursementTypeId == that.reimbursementTypeId && Objects.equals(reimbursementSubmitted, that.reimbursementSubmitted) && Objects.equals(reimbursementResolved, that.reimbursementResolved) && Objects.equals(reimbursementDescription, that.reimbursementDescription) && Arrays.equals(reimbursementReceipt, that.reimbursementReceipt);
    }
    @Override
    public int hashCode() {
        int result = Objects.hash(reimbursementID, reimbursementAmount, reimbursementSubmitted, reimbursementResolved, reimbursementDescription, reimbursementAuthor, reimbursementResolver, reimbursementStatusId, reimbursementTypeId);
        result = 31 * result + Arrays.hashCode(reimbursementReceipt);
        return result;
    }
    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "reimbursementID=" + reimbursementID +
                ", reimbursementAmount=" + reimbursementAmount +
                ", reimbursementSubmitted=" + reimbursementSubmitted +
                ", reimbursementResolved=" + reimbursementResolved +
                ", reimbursementDescription='" + reimbursementDescription + '\'' +
                ", reimbursementReceipt=" + Arrays.toString(reimbursementReceipt) +
                ", reimbursementAuthor=" + reimbursementAuthor +
                ", reimbursementResolver=" + reimbursementResolver +
                ", reimbursementStatusId=" + reimbursementStatusId +
                ", reimbursementTypeId=" + reimbursementTypeId +
                '}';
    }
}
