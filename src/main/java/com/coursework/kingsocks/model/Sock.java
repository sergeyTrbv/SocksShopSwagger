package com.coursework.kingsocks.model;

import java.util.Objects;


public class Sock {

    private final Color color;
    private final Size size;
    private final int cottonPart;

    public Sock(Color color, Size size, int cottonPart) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sock sock = (Sock) o;
        return cottonPart == sock.cottonPart && color == sock.color && size == sock.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }
}
