package com.example.areport_dpm_xbrl;

public class Error {

    public static boolean isError(Object data) {
        if (!(data instanceof Error)) {
            return false;
        }
        return true;
    }

    public static void raiseError(String error) {
        System.out.println(error);
    }
}
