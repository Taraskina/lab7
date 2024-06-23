package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import static org.example.utility.CheckingReader.checkRead;

public class Person implements Comparable<Person> {
    private long id; //uniq
    private String name;
    private Coordinates coordinates;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationDate; //!null, auto
    private double height; //height>0
    private Integer weight; //weight>0, !null
    private Color hairColor; //!null
    private Location location; //!null
    static long countId = 0;
    String ownerLogin;

    public Person(String name, Coordinates coordinates, double height, Integer weight, Color hairColor, Location location, String ownerLogin) {
        super();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.location = location;
        this.ownerLogin = ownerLogin;
    }

    public Person(String s, Coordinates coordinates, double d, Integer i, Color s1, Location location) {
        super();
        this.name = s;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.height = d;
        this.weight = i;
        this.hairColor = s1;
        this.location = location;
    }

    public void update(String[] args) {

        this.name = (String) checkRead("s", args[0]);
        this.coordinates = new Coordinates(
                (Long) checkRead("l", args[1]),
                (Long) checkRead("l", args[2]));

        this.height = (double) checkRead("d", args[3]);
        this.weight = (Integer) checkRead("i", args[4]);
        this.hairColor = Color.choose(
                (String) checkRead("s", args[6]));
        this.location = new Location(
                (Long) checkRead("l", args[7]),
                (long) checkRead("l", args[7]),
                (Float) checkRead("f", args[8]));
    }

    public Person(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static long getCountId() {
        return countId;
    }

    public static void setCountId(long countId) {
        Person.countId = countId;
    }

    @Override
    public int compareTo(Person o) {
        return this.name.compareTo(o.name);
    }
}
