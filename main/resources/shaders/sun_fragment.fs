#version 330 core

in vec2 fragTexCoord;

uniform sampler2D textureSampler;

out vec4 fragColor;

void main() {
    vec4 texColor = texture(textureSampler, fragTexCoord);

    vec2 center = vec2(0.5, 0.5);
    float dist = length(fragTexCoord - center);

    float glow = 1.0 - smoothstep(0.2, 0.6, dist);

    fragColor = texColor * (1.0 + glow * 2.0);
}