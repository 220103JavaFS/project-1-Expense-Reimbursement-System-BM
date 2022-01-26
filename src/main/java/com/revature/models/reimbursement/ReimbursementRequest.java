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
    private User reimbursementAuthor;
    private User reimbursementResolver;
    private ReimbursementStatus reimbursementStatus;
    private ReimbursementType reimbursementType;

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
    public User getReimbursementAuthor() {
        return reimbursementAuthor;
    }
    public void setReimbursementAuthor(User reimbursementAuthor) {
        this.reimbursementAuthor = reimbursementAuthor;
    }
    public User getReimbursementResolver() {
        return reimbursementResolver;
    }
    public void setReimbursementResolver(User reimbursementResolver) {
        this.reimbursementResolver = reimbursementResolver;
    }
    public ReimbursementStatus getReimbursementStatus() {
        return reimbursementStatus;
    }
    public void setReimbursementStatus(ReimbursementStatus reimbursementStatus) {
        this.reimbursementStatus = reimbursementStatus;
    }
    public ReimbursementType getReimbursementType() {
        return reimbursementType;
    }
    public void setReimbursementType(ReimbursementType reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    //METHODS FOR JSON
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementRequest that = (ReimbursementRequest) o;
        return reimbursementID == that.reimbursementID && Double.compare(that.reimbursementAmount, reimbursementAmount) == 0 && Objects.equals(reimbursementSubmitted, that.reimbursementSubmitted) && Objects.equals(reimbursementResolved, that.reimbursementResolved) && Objects.equals(reimbursementDescription, that.reimbursementDescription) && Arrays.equals(reimbursementReceipt, that.reimbursementReceipt) && Objects.equals(reimbursementAuthor, that.reimbursementAuthor) && Objects.equals(reimbursementResolver, that.reimbursementResolver) && Objects.equals(reimbursementStatus, that.reimbursementStatus) && Objects.equals(reimbursementType, that.reimbursementType);
    }
    @Override
    public int hashCode() {
        int result = Objects.hash(reimbursementID, reimbursementAmount, reimbursementSubmitted, reimbursementResolved, reimbursementDescription, reimbursementAuthor, reimbursementResolver, reimbursementStatus, reimbursementType);
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
                ", reimbursementStatus=" + reimbursementStatus +
                ", reimbursementType=" + reimbursementType +
                '}';
    }
}
