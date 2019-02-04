package com.exercise.connectt.pojo;

import java.util.Objects;

public class Crypt {
    private final int start;
    private final int length;
    private final int rotate;

    public Crypt(int start, int length, int rotate) {
        this.start = start;
        this.length = length;
        this.rotate = rotate;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getRotate() {
        return rotate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crypt)) return false;
        Crypt crypt = (Crypt) o;
        return start == crypt.start &&
                length == crypt.length &&
                rotate == crypt.rotate;
    }

    @Override
    public int hashCode() {

        return Objects.hash(start, length, rotate);
    }
}
