#version 330 core

in vec2 fragTexCoord;

uniform sampler2D textureSampler; // normales 2D Texture
uniform vec4 colour;

out vec4 fragColor;

void main() {
    fragColor = texture(textureSampler, fragTexCoord) * colour;
}
