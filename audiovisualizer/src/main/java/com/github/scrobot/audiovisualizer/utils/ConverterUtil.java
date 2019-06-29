package com.github.scrobot.audiovisualizer.utils;

import org.joda.time.DateTime;

public class ConverterUtil {

    public static String convertMillsToTime(Long mill) {
        return new DateTime(mill).toString("mm:ss");
    }

}
