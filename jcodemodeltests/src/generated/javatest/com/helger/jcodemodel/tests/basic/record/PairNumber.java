package com.helger.jcodemodel.tests.basic.record;

public record PairNumber<T extends Number>(T first, T second) {
}
