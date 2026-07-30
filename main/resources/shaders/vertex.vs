#version 330 core

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in int texLayer; // Texture layer index.
layout (location=3) in float brightness; // face brightness
layout (location=4) in float lightLevel; // block light level.

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

out vec2 outTexCoord;
out float fragBrightness; // face brightness
flat out int outTexLayer;

void main()
{
    fragBrightness = (lightLevel / 10) * brightness; // face brightness

    vec4 worldPos = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * worldPos;
    outTexCoord = texCoord;
    outTexLayer = texLayer;  // To fragment shader
}
