package com.helger.jcodemodel.tests.basic.record;

public record PointDistance(int x, int y) {

    public double distance() {
        return Math.sqrt(((x*x)+(y*y)));
    }
}
