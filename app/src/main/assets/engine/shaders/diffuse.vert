#version 300 es
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;
layout (location = 2) in vec3 aNormal;

//// FROM ENGINE

uniform mat4 uPMatrix;
uniform mat4 uVMatrix;
uniform mat4 uMMatrix;

uniform vec4 lightReflection;

// LIGHTS (DIRECTION, COLOR, DIAMETER, INTENSITY)
uniform vec3 lightDirection0;
uniform vec4 lightColor0;
uniform float lightDiameter0;
uniform float lightIntensity0;
uniform vec3 lightPostion0;

uniform vec3 lightDirection1;
uniform vec4 lightColor1;
uniform float lightDiameter1;
uniform float lightIntensity1;
uniform vec3 lightPostion1;

uniform vec3 lightDirection2;
uniform vec4 lightColor2;
uniform float lightDiameter2;
uniform float lightIntensity2;
uniform vec3 lightPostion2;

uniform vec3 lightDirection3;
uniform vec4 lightColor3;
uniform float lightDiameter3;
uniform float lightIntensity3;
uniform vec3 lightPostion3;

uniform vec3 lightDirection4;
uniform vec4 lightColor4;
uniform float lightDiameter4;
uniform float lightIntensity4;
uniform vec3 lightPostion4;

uniform vec3 lightDirection5;
uniform vec4 lightColor5;
uniform float lightDiameter5;
uniform float lightIntensity5;
uniform vec3 lightPostion5;

uniform vec3 lightDirection6;
uniform vec4 lightColor6;
uniform float lightDiameter6;
uniform float lightIntensity6;
uniform vec3 lightPostion6;

uniform vec3 lightDirection7;
uniform vec4 lightColor7;
uniform float lightDiameter7;
uniform float lightIntensity7;
uniform vec3 lightPostion7;

uniform vec3 lightDirection8;
uniform vec4 lightColor8;
uniform float lightDiameter8;
uniform float lightIntensity8;
uniform vec3 lightPostion8;

uniform vec3 lightDirection9;
uniform vec4 lightColor9;
uniform float lightDiameter9;
uniform float lightIntensity9;
uniform vec3 lightPostion9;

out vec4 vertexLight;
out vec2 texCoord;
out vec3 normal;

float vectorDistance(vec3 v1, vec3 v2);

void main() {
    normal = aNormal;
    gl_Position = uMMatrix * uVMatrix * uPMatrix * vec4(aPos, 1.0);
    vec4 pos = uMMatrix * vec4(aPos, 1.0);
    texCoord = vec2(aTexCoord.s, 1.0 - aTexCoord.t);// FLIPPING Y

    // LIGHT
    float intensity0 = (max(0.0, dot(aPos.xyz, lightDirection0))) * (lightIntensity0 - ((vectorDistance(lightPostion0.xyz, pos.xyz) / lightDiameter0) * lightIntensity0));
    float intensity1 = (max(0.0, dot(aPos.xyz, lightDirection1))) * (lightIntensity1 - ((vectorDistance(lightPostion1.xyz, pos.xyz) / lightDiameter1) * lightIntensity1));
    float intensity2 = (max(0.0, dot(aPos.xyz, lightDirection2))) * (lightIntensity2 - ((vectorDistance(lightPostion2.xyz, pos.xyz) / lightDiameter2) * lightIntensity2));
    float intensity3 = (max(0.0, dot(aPos.xyz, lightDirection3))) * (lightIntensity3 - ((vectorDistance(lightPostion3.xyz, pos.xyz) / lightDiameter3) * lightIntensity3));
    float intensity4 = (max(0.0, dot(aPos.xyz, lightDirection4))) * (lightIntensity4 - ((vectorDistance(lightPostion4.xyz, pos.xyz) / lightDiameter4) * lightIntensity4));
    float intensity5 = (max(0.0, dot(aPos.xyz, lightDirection5))) * (lightIntensity5 - ((vectorDistance(lightPostion5.xyz, pos.xyz) / lightDiameter5) * lightIntensity5));
    float intensity6 = (max(0.0, dot(aPos.xyz, lightDirection6))) * (lightIntensity6 - ((vectorDistance(lightPostion6.xyz, pos.xyz) / lightDiameter6) * lightIntensity6));
    float intensity7 = (max(0.0, dot(aPos.xyz, lightDirection7))) * (lightIntensity7 - ((vectorDistance(lightPostion7.xyz, pos.xyz) / lightDiameter7) * lightIntensity7));
    float intensity8 = (max(0.0, dot(aPos.xyz, lightDirection8))) * (lightIntensity8 - ((vectorDistance(lightPostion8.xyz, pos.xyz) / lightDiameter8) * lightIntensity8));
    float intensity9 = (max(0.0, dot(aPos.xyz, lightDirection9))) * (lightIntensity9 - ((vectorDistance(lightPostion9.xyz, pos.xyz) / lightDiameter9) * lightIntensity9));

    vertexLight = vec4(0, 0, 0, 0);

    if (intensity0 > 0.0) {
        vertexLight += (lightColor0 * intensity0 * lightReflection);
    }

    if (intensity1 > 0.0) {
        vertexLight += (lightColor1 * intensity1 * lightReflection);
    }

    if (intensity2 > 0.0) {
        vertexLight += (lightColor2 * intensity2 * lightReflection);
    }

    if (intensity3 > 0.0) {
        vertexLight += (lightColor3 * intensity3 * lightReflection);
    }

    if (intensity4 > 0.0) {
        vertexLight += (lightColor4 * intensity4 * lightReflection);
    }

    if (intensity5 > 0.0) {
        vertexLight += (lightColor5 * intensity5 * lightReflection);
    }

    if (intensity6 > 0.0) {
        vertexLight += (lightColor6 * intensity6 * lightReflection);
    }

    if (intensity7 > 0.0) {
        vertexLight += (lightColor7 * intensity7 * lightReflection);
    }

    if (intensity8 > 0.0) {
        vertexLight += (lightColor8 * intensity8 * lightReflection);
    }

    if (intensity9 > 0.0) {
        vertexLight += (lightColor9 * intensity9 * lightReflection);
    }
}

float vectorDistance(vec3 v1, vec3 v2) {
    return sqrt(
    (v1.x - v2.x) * (v1.x - v2.x) +
    (v1.y - v2.y) * (v1.y - v2.y) +
    (v1.z - v2.z) * (v1.z - v2.z)
    );
}
