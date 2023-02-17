package com.nohit.jira_project.util;

import org.springframework.stereotype.*;

import static java.lang.Integer.*;
import static java.lang.Float.*;

@Component
public class NumberUtil {
    // Check string is integer or not
    public boolean isInt(String s) {
        try {
            parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check string is float or not
    public boolean isFloat(String s) {
        try {
            parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
