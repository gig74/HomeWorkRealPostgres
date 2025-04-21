package com.roks.realpostgres.repository;

import org.springframework.jdbc.core.RowMapper;

public class Dog {
    private String name;
    private double longitude;
    private double latitude;

    public Dog(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static RowMapper<Dog> rowMapper = (rs, rowNum) -> new Dog(
            rs.getString("name"),
            rs.getDouble("longitude"),
            rs.getDouble("latitude")
    );

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
