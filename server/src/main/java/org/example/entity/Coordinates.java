package org.example.entity;

public class Coordinates {
    private Long x; //!null
    private Long y; // y>-867, !null

    public Coordinates(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(){}

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
