package com.revature.models.reimbursement;

import java.util.Objects;

public class ReimbursementType {

    //FIELDS
    private int typeID;
    private String type;

    //CONSTRUCTORS
    public ReimbursementType() {}
    public ReimbursementType(int typeID, String type) {
        this.typeID = typeID;
        this.type = type;
    }

    //GETTERS AND SETTERS
    public int getTypeID() {
        return typeID;
    }
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //METHODS FOR JSON
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementType that = (ReimbursementType) o;
        return typeID == that.typeID && Objects.equals(type, that.type);
    }
    @Override
    public int hashCode() {
        return Objects.hash(typeID, type);
    }
    @Override
    public String toString() {
        return "ReimbursementType{" +
                "typeID=" + typeID +
                ", type='" + type + '\'' +
                '}';
    }
}
