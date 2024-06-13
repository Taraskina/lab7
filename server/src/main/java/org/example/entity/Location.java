package org.example.entity;

public class Location {
    private long x;
    private Long y; //!null
    private float z;

    public Location(long x, Long y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(){}

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}
