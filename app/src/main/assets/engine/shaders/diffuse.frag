#version 300 es
precision mediump float;
out vec4 fragColor;

//// FROM ENGINE

uniform sampler2D albedoTexture;

uniform vec4 ambientColor;
uniform vec4 diffuseColor;

////

in highp vec4 vertexLight;
in vec2 texCoord;

void main() {
    float ambientStrength = 0.7;
    vec4 ambient = ambientStrength * ambientColor;

    vec4 textureColor = texture2D(albedoTexture, texCoord);

    fragColor = vec4(((ambient + vertexLight) * textureColor).rgb, 1.0) * diffuseColor;
    fragColor.a = 1.0;
}
