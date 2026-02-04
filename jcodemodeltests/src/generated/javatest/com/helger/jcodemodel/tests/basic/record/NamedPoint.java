package com.helger.jcodemodel.tests.basic.record;

public record NamedPoint(int x, int y, String name)
    implements Comparable<NamedPoint>
{

    public int compareTo(NamedPoint other) {
        return  0;
    }
}
