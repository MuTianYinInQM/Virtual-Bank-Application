package com.virtualbank.parameters;

import com.virtualbank.model.Pair;

import java.time.Period;
import java.util.Arrays;
import java.util.List;

public class Interest {
    public static final double currentInterest = 0.02;
    // 只有以年为单位的定期存款
    public static final List<Pair<Double, Period>> savingInterest = Arrays.asList(
            new Pair<>(0.02, Period.ofYears(1)),
            new Pair<>(0.03, Period.ofYears(2)),
            new Pair<>(0.04, Period.ofYears(3)),
            new Pair<>(0.05, Period.ofYears(5))
    );

    public static final double timeLapseCoefficient = 1;
}