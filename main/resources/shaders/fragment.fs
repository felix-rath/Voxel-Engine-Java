#version 330 core

in vec2 outTexCoord;
in float fragBrightness;
flat in int outTexLayer;

uniform sampler2DArray textureArray;
uniform vec3 colour;
uniform int useColour;

out vec4 fragColor;


void main()
{
    if (useColour == 1) {
        fragColor = vec4(colour, 1);
    } else {
           vec4 texColor = texture(textureArray, vec3(outTexCoord, outTexLayer));
           fragColor = vec4(texColor.rgb * fragBrightness, 1.0);

           if (outTexLayer == 5) { // if water
               vec4 waterAlpha = vec4(0.8,0.8,0.8,0.6);
               fragColor = vec4(fragColor * waterAlpha);
           } else if (outTexLayer == 7 || outTexLayer == 10) { // If birch or oak leave
                if (texColor.a < 0.1) discard;

                if (outTexLayer == 10) {
                    fragColor = vec4(fragColor.a * 0.8, fragColor.g * 1.1, fragColor.b * 0.9, 1.0);   // Birch leave color
                }
           }
    }
}
