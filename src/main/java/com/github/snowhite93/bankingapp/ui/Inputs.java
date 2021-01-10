package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inputs {

    private static final Logger log = Logger.getLogger(Inputs.class);

    private Inputs() {

    }

    public static Date readDate(Input input) {
        while (true) {
            String readLine = input.readLine();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(readLine);
            } catch (ParseException e) {
                log.error("Invalid date, expecting format yyyy-MM-dd but got: " + readLine);
            }
        }
    }

    public static int readInt(Input input) {
        while (true) {
            String readLine = input.readLine();
            try {
                return Integer.parseInt(readLine);
            } catch (NumberFormatException e) {
                log.error("Invalid number: " + readLine);
            }
        }
    }

    public static double readDouble(Input input) {
        while (true) {
            String readLine = input.readLine();

            try {
                return Double.parseDouble(readLine);
            }catch (NumberFormatException e) {
                log.error("Invalid balance " + readLine);
            }
        }
    }

}
