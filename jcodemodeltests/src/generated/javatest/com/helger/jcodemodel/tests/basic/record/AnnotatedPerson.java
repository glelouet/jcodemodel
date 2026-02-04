package com.helger.jcodemodel.tests.basic.record;

public record AnnotatedPerson(@JRecordTestGen.RecordAnnotationExample String name, int age) {
}
