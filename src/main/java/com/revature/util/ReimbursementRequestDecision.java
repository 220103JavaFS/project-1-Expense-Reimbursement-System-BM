package com.revature.util;

public class ReimbursementRequestDecision {
    private String decision;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "ReimbursementRequestDecision{" +
                "decision='" + decision + '\'' +
                '}';
    }
}
