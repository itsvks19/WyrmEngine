//
// Created by Vivek on 8/8/2024.
//

#include "shader.h"

#include <GLES3/gl32.h>

#include <utility>

#include "logger.h"

namespace WyrmEngine {
  Shader::Shader(std::string vertexShaderCode, std::string fragmentShaderCode) : vertexShaderCode(std::move(vertexShaderCode)), fragmentShaderCode(std::move(fragmentShaderCode)) {
    this->program        = 0;
    this->IsShaderLoaded = false;
  }

  Shader::~Shader() {
    this->vertexShaderCode   = nullptr;
    this->fragmentShaderCode = nullptr;
    this->program            = 0;
    this->IsShaderLoaded     = false;
  }

  Shader* Shader::Create(std::string vertexShaderCode, std::string fragmentShaderCode) {
    return new Shader(std::move(vertexShaderCode), std::move(fragmentShaderCode));
  }

  unsigned int Shader::compileShader(const char* shaderCode, unsigned int type) {
    auto shader = glCreateShader(type);
    glShaderSource(shader, 1, &shaderCode, nullptr);
    glCompileShader(shader);

    int compileSuccess;
    glGetShaderiv(shader, GL_COMPILE_STATUS, &compileSuccess);
    if (!compileSuccess) {
      char infoLog[1024];
      glGetShaderInfoLog(shader, 1024, nullptr, infoLog);
      LogE("ERROR::SHADER_COMPILATION: type: %u\n%s\n", type, infoLog);
    }

    return shader;
  }

  unsigned int Shader::linkProgram(unsigned int vertexShader, unsigned int fragmentShader) {
    if (this->IsShaderLoaded) return this->program;

    auto i = glCreateProgram();
    glAttachShader(i, vertexShader);
    glAttachShader(i, fragmentShader);
    glLinkProgram(i);

    int linkSuccess;
    glGetProgramiv(i, GL_LINK_STATUS, &linkSuccess);
    if (!linkSuccess) {
      char infoLog[1024];
      glGetProgramInfoLog(i, 1024, nullptr, infoLog);
      LogE("ERROR::PROGRAM_LINKING: type: %s\n", infoLog);
    }

    return i;
  }

  void Shader::loadShader() {
    auto vertexShader   = compileShader(vertexShaderCode.c_str(), GL_VERTEX_SHADER);
    auto fragmentShader = compileShader(fragmentShaderCode.c_str(), GL_FRAGMENT_SHADER);

    this->program        = linkProgram(vertexShader, fragmentShader);
    this->IsShaderLoaded = true;

    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
  }

  void Shader::use() const {
    glUseProgram(this->program);
  }

  int Shader::getAttributeLocation(const char* name) const {
    return glGetAttribLocation(this->program, name);
  }

  int Shader::getUniformLocation(const char* name) const {
    return glGetUniformLocation(this->program, name);
  }

  void Shader::setInt(const char* name, int value) const {
    glUniform1i(getUniformLocation(name), value);
  }

  void Shader::setBool(const char* name, bool value) const {
    setInt(name, value ? 1 : 0);
  }

  void Shader::setFloat(const char* name, float value) const {
    glUniform1f(getUniformLocation(name), value);
  }

  void Shader::setVec2(const char* name, float x, float y) const {
    glUniform2f(getUniformLocation(name), x, y);
  }

  void Shader::setVec3(const char* name, float x, float y, float z) const {
    glUniform3f(getUniformLocation(name), x, y, z);
  }

  void Shader::setVec4(const char* name, float x, float y, float z, float w) const {
    glUniform4f(getUniformLocation(name), x, y, z, w);
  }

  void Shader::setVec2(const char* name, glm::vec2 value) const {
    glUniform2fv(getUniformLocation(name), 1, &value[0]);
  }

  void Shader::setVec3(const char* name, glm::vec3 value) const {
    glUniform3fv(getUniformLocation(name), 1, &value[0]);
  }

  void Shader::setVec4(const char* name, glm::vec4 value) const {
    glUniform4fv(getUniformLocation(name), 1, &value[0]);
  }

  void Shader::setMat2(const char* name, glm::mat2 value) const {
    glUniformMatrix2fv(getUniformLocation(name), 1, GL_FALSE, &value[0][0]);
  }

  void Shader::setMat3(const char* name, glm::mat3 value) const {
    glUniformMatrix3fv(getUniformLocation(name), 1, GL_FALSE, &value[0][0]);
  }

  void Shader::setMat4(const char* name, glm::mat4 value) const {
    glUniformMatrix4fv(getUniformLocation(name), 1, GL_FALSE, &value[0][0]);
  }
}// namespace WyrmEngine