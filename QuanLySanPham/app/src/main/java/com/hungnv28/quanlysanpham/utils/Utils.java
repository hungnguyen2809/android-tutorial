package com.hungnv28.quanlysanpham.utils;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
    @NonNull
    public static String formatNumber(double number) {
        NumberFormat numberFormatter = new DecimalFormat("#,###");
        return numberFormatter.format(number);
    }

    @NonNull
    public static String formatNumber2(double number) {
        NumberFormat numberFormatter = new DecimalFormat("###");
        return numberFormatter.format(number);
    }
}
