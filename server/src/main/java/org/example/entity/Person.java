package org.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Person implements Comparable<Person> {
    private long id; //uniq
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate; //!null, auto
    private double height; //height>0
    private LocalDate birthday; //can be null
    private Integer weight; //weight>0, !null
    private Color hairColor; //!null
    private Location location; //!null
    static long countId = 0;

    public Person(long id, String name, Coordinates coordinates, LocalDateTime creationDate, double height, LocalDate birthday, Integer weight, Color hairColor, Location location) {
        super();
        this.id = countId + 1;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.height = height;
        this.birthday = birthday;
        this.weight = weight;
        this.hairColor = hairColor;
        this.location = location;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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
