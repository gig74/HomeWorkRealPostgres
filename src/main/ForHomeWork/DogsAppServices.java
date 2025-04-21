package com.roks.realpostgres.services;

import com.roks.realpostgres.repository.Dog;
import com.roks.realpostgres.repository.Repository;
import com.roks.realpostgres.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class DogsAppServices {
    @Autowired
    private Repository repository;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    @PostMapping("/createUser/{login}")
    public String createUser(
            @PathVariable(value = "login") String login
    ) {
        final double longitude = rndDouble();
        final double latitude = rndDouble();
        if(login == null) {
            throw new IllegalArgumentException("login is null");
        }

        repository.createUser(login, longitude, latitude);
        return repository.getUser(login).toString();
    }

    @PostMapping("/createDog/{name}")
    public String createDog(
            @PathVariable(value = "name") String name
    ) {
        final String id = repository.createDog(name, rndDouble(), rndDouble());
        return repository.getDog(id).toString();
    }

    @PostMapping("/createWalk/{userLogin}/{dogName}")
    public String createWalk(
            @PathVariable(value = "userLogin") String userLogin,
            @PathVariable(value = "dogName") String dogName
    ) {
        final Date startDate = rndDate();
        repository.createWalk(userLogin, dogName, rndDouble(), rndDouble(), startDate, 10, 10.0, false);
        return repository.getWalk(userLogin, dogName, startDate).toString();
    }

    @PostMapping("/increaseUserBalance/{login}/{amount}")
    public String increaseUserBalance(
            @PathVariable(value = "login") String login,
            @PathVariable(value = "amount") double amount
    ) {
        final User user = repository.getUser(login);
        final double newBalance = user.getBalance() + amount;
        repository.updateUserBalance(login, newBalance);
        return repository.getUser(login).toString();
    }

    //part 3
    @PostMapping("/increaseUserBalanceTransactional/{login}/{amount}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String increaseUserBalanceTransactional(
            @PathVariable(value = "login") String login,
            @PathVariable(value = "amount") double amount
    ) {
        final User user = repository.getUser(login);
        final double newBalance = user.getBalance() + amount;
        repository.updateUserBalance(login, newBalance);
        return repository.getUser(login).toString();
    }

    //part 4
    //geo-request
    @GetMapping("/getClosestDogs/{login}/{count}")
    public String getClosestDogs(
            @PathVariable(value = "login") String login,
            @PathVariable(value = "count") int count
    ) {
        final User user = repository.getUser(login);
        final List<Dog> closestDogs = repository.getClosestDogs(user.getLongitude(), user.getLatitude(), count);
        return closestDogs.toString();
    }


    //advisory locking
    @PostMapping("/createDogWithCoords/{name}/{ln}/{lat}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String createDogWithCoords(
            @PathVariable(value = "name") String name,
            @PathVariable(value = "ln") double ln,
            @PathVariable(value = "lat") double lat
    ) {
        final int dogCountByCoords = repository.countDogsByCoords(ln, lat);

        if(dogCountByCoords == 0) {
            final String id = repository.createDog(name, ln, lat);
            return repository.getDog(id).toString();
        }
        return "Already exists";
    }

    @PostMapping("/createDogWithCoordsSerializable/{name}/{ln}/{lat}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String createDogWithCoordsSerializable(
            @PathVariable(value = "name") String name,
            @PathVariable(value = "ln") double ln,
            @PathVariable(value = "lat") double lat
    ) {
        final int dogCountByCoords = repository.countDogsByCoords(ln, lat);
        if(dogCountByCoords == 0) {
            final String id = repository.createDog(name, ln, lat);
            return repository.getDog(id).toString();
        }
        return "Already exists";
    }

    @PostMapping("/createDogWithCoordsAdvisoryLocked/{name}/{ln}/{lat}")
    public String createDogWithCoordsAdvisoryLocked2(
            @PathVariable(value = "name") String name,
            @PathVariable(value = "ln") double ln,
            @PathVariable(value = "lat") double lat
    ) {
        repository.advisoryLockByCoords(ln, lat);
        final int dogCountByCoords = repository.countDogsByCoords(ln, lat);
        final String res;
        if (dogCountByCoords == 0) {
            final String id = repository.createDog(name, ln, lat);
            res = repository.getDog(id).toString();
        } else {
            res = "Already exists";
        }
        repository.advisoryUnlockByCoords(ln, lat);
        return res;
    }

    @GetMapping("/max_walks")
    public List<String> getTop10WalkingUsersFromDb () {
        final List<String> top10WalkingUsers = repository.getTop10WalkingUsersFromDb();
        return top10WalkingUsers;
    }
    //util methods
    private double rndDouble() {
        return Math.random() * 100;
    }
    private Date rndDate() {
        final String dateInString = "1-Dec-2030";
        try {
            final int plusSec = ThreadLocalRandom.current().nextInt(400000,512643);
            final Instant startInstant = formatter.parse(dateInString).toInstant().plus(plusSec, ChronoUnit.SECONDS);
            return Date.from(startInstant);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

//Привет, если ты это читаешь, то ты нашел посхалку) Красава! Если хочешь, напиши мне об этом в телегу @uwuthless, мне будет приятно)