package de.rathfelix.engine.math;

public class Mathf {

    // Lerp between 2 numbers.
    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public static float smoothstep(float edge0, float edge1, float x) {
        float t = (x - edge0) / (edge1 - edge0);
        t = Math.max(0, Math.min(1, t)); // Clamp zwischen 0 und 1
        return t * t * (3 - 2 * t);
    }

}
