//
// Created by Vivek on 8/8/2024.
//

#ifndef WYRMENGINE_SHADER_H
#define WYRMENGINE_SHADER_H

#include <glm/glm.hpp>
#include <string>

namespace WyrmEngine {

  class Shader {
  private:
    std::string vertexShaderCode;
    std::string fragmentShaderCode;

    unsigned int program;

    unsigned int compileShader(const char* shaderCode, unsigned int type);
    unsigned int linkProgram(unsigned int vertexShader, unsigned int fragmentShader);

  public:
    bool IsShaderLoaded = false;

    Shader(std::string vertexShaderCode, std::string fragmentShaderCode);
    ~Shader();

    static Shader* Create(std::string vertexShaderCode, std::string fragmentShaderCode);

    void loadShader();
    void use() const;

    int getAttributeLocation(const char* name) const;
    int getUniformLocation(const char* name) const;

    void setBool(const char* name, bool value) const;
    void setInt(const char* name, int value) const;
    void setFloat(const char* name, float value) const;

    void setVec2(const char* name, float x, float y) const;
    void setVec3(const char* name, float x, float y, float z) const;
    void setVec4(const char* name, float x, float y, float z, float w) const;

    void setVec2(const char* name, glm::vec2 value) const;
    void setVec3(const char* name, glm::vec3 value) const;
    void setVec4(const char* name, glm::vec4 value) const;

    void setMat2(const char* name, glm::mat2 value) const;
    void setMat3(const char* name, glm::mat3 value) const;
    void setMat4(const char* name, glm::mat4 value) const;
  };

}// namespace WyrmEngine

#endif//WYRMENGINE_SHADER_H
