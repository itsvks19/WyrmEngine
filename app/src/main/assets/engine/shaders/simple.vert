#version 300 es
layout(location = 0) in vec3 aPos;
layout(location = 1) in vec3 aNormal;
layout(location = 2) in vec2 aTexCoord;

uniform mat4 uPMatrix;
uniform mat4 uVMatrix;
uniform mat4 uMMatrix;

out vec2 texCoord;
out vec3 normal;

void main() {
    texCoord = aTexCoord;
    normal = aNormal;
    gl_Position = uPMatrix * uVMatrix * uMMatrix * vec4(aPos, 1.0);
}
