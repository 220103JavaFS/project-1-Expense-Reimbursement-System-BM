package com.revature.util;

import java.util.Objects;

public class AppErrorCode {
    //allows us to return a specific error code to the browser if something goes wrong. This will
    //help us diagnose from the browser end why exactly we get a 400 error as opposed to just getting
    //a 400 error and wondering
    public int errorCode;

    public AppErrorCode() {
    }

    public AppErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppErrorCode that = (AppErrorCode) o;
        return errorCode == that.errorCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode);
    }

    @Override
    public String toString() {
        return "AppErrorCode{" +
                "errorCode=" + errorCode +
                '}';
    }
}
