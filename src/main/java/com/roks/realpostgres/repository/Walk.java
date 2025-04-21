package com.roks.realpostgres.repository;

import org.springframework.jdbc.core.RowMapper;

import java.time.Duration;
import java.util.Date;

public class Walk {
    private String userLogin;
    private String dogId;
    private double longitude;
    private double latitude;
    private Date startTime;
    private Duration duration;
    private double priceUsd;
    private boolean isAccepted;


    public Walk(String userLogin, String dogId, double longitude, double latitude, Date startTime, Duration duration, double price, boolean isAccepted) {
        this.userLogin = userLogin;
        this.dogId = dogId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.startTime = startTime;
        this.duration = duration;
        this.priceUsd = price;
        this.isAccepted = isAccepted;
    }

    public static RowMapper<Walk> rowMapper = (rs, rowNum) -> new Walk(
            rs.getString("user_login"),
            rs.getString("dog_id"),
            rs.getDouble("longitude"),
            rs.getDouble("latitude"),
            rs.getDate("start_time"),
            Duration.ofMinutes(rs.getLong("duration_minutes")),
            rs.getDouble("price_usd"),
            rs.getBoolean("is_accepted")
    );

    @Override
    public String toString() {
        return "Walk{" +
                "userLogin='" + userLogin + '\'' +
                ", dogId='" + dogId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", priceUsd=" + priceUsd +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
