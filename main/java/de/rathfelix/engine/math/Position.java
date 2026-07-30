package de.rathfelix.engine.math;

import java.util.Objects;

public class Position {

    public int x;

    public int y;

    public int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {
        x = 0;
        y = 0;
        z =0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position p = (Position) obj;
        return x == p.x && y == p.y && z == p.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
