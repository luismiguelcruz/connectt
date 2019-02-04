package com.exercise.connectt.pojo;

import java.util.Objects;

public class Body {
    private final int id;
    private final String data;

    public Body(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Body)) return false;
        Body body = (Body) o;
        return id == body.id &&
                Objects.equals(data, body.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, data);
    }
}
