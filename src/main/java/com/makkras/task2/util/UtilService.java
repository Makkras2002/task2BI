package com.makkras.task2.util;

import org.springframework.stereotype.Service;

@Service
public class UtilService {
    public boolean isLong(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Long d = Long.valueOf(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public boolean isDouble(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double d = Double.valueOf(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
