package com.helger.jcodemodel.tests.basic.record;

public record Range(int lo, int hi) {

    public Range {
        if (lo >hi) {
            throw new IllegalArgumentException("High must be greater or equal to Low");
        }
    }
}
