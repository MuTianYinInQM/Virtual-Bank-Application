package com.virtualbank.parameters;

import com.virtualbank.model.Pair;

import java.time.Period;
import java.util.Arrays;
import java.util.List;

/**
 * Parameters related to interest rates in the virtual bank application.
 */
public class Interest {
    /**
     * The interest rate for current accounts.
     */
    public static final double currentInterest = 0.02;
    // 只有以年为单位的定期存款
    /**
     * The interest rates for different term periods in saving accounts.
     * Each pair consists of an interest rate and the corresponding period.
     */
    public static final List<Pair<Double, Period>> savingInterest = Arrays.asList(
            new Pair<>(0.02, Period.ofYears(1)),
            new Pair<>(0.03, Period.ofYears(2)),
            new Pair<>(0.04, Period.ofYears(3)),
            new Pair<>(0.05, Period.ofYears(5))
    );
    /**
     * The time lapse coefficient used for interest calculation.
     */
    public static final double timeLapseCoefficient = 536000.0;
}