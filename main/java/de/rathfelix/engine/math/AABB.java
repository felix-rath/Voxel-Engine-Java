package de.rathfelix.engine.math;

import org.joml.Vector3f;

public class AABB {
    public Vector3f min;
    public Vector3f max;

    public final float width;
    public final float height;
    public final float depth;

    public AABB(Vector3f center, float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        float halfW = width / 2f;
        float halfD = depth / 2f;

        this.min = new Vector3f(center.x - halfW, center.y, center.z - halfD);
        this.max = new Vector3f(center.x + halfW, center.y + height, center.z + halfD);
    }

    public boolean intersects(AABB other) {
        return (this.min.x <= other.max.x && this.max.x >= other.min.x) &&
                (this.min.y <= other.max.y && this.max.y >= other.min.y) &&
                (this.min.z <= other.max.z && this.max.z >= other.min.z);
    }
}
