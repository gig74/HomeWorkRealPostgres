package com.roks.realpostgres.repository;

import org.springframework.jdbc.core.RowMapper;

public class User {
    private String login;
    private double longitude;
    private double latitude;
    private double balance;


    public User(String login, double longitude, double latitude, double balance) {
        this.login = login;
        this.longitude = longitude;
        this.latitude = latitude;
        this.balance = balance;
    }

    public static RowMapper<User> rowMapper = (rs, rowNum) -> new User(
            rs.getString("login"),
            rs.getDouble("longitude"),
            rs.getDouble("latitude"),
            rs.getDouble("balance")
    );

    public double getBalance() {
        return balance;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", balance=" + balance +
                '}';
    }
}
