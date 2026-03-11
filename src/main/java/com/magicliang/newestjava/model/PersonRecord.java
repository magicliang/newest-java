package com.magicliang.newestjava.model;

/**
 * Java 14: Record 类型 (JEP 395, finalized in Java 16)
 *
 * Record 是一种特殊的类，自动生成 constructor、getter、equals、hashCode、toString。
 * 适用于不可变数据载体（Data Carrier）。
 */
public record PersonRecord(String name, int age, String email) {

    // Record 支持紧凑构造器（Compact Constructor）进行参数校验
    public PersonRecord {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    // Record 可以有自定义方法
    public String greeting() {
        return "Hello, I'm %s, %d years old.".formatted(name, age);
    }
}
