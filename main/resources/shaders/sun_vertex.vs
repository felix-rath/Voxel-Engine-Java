#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

uniform mat4 projMatrix;
uniform mat4 modelViewMatrix;

out vec2 fragTexCoord;

void main() {
    vec4 worldPos = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projMatrix * worldPos;
    fragTexCoord = texCoord;
}