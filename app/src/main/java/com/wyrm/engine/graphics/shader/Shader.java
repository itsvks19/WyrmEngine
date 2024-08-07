/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.graphics.shader;

import android.opengl.GLES30;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wyrm.engine.core.components.color.Color;
import com.wyrm.engine.model.shader.ShaderTexture;

import org.jetbrains.annotations.Contract;

import java.io.Serial;
import java.io.Serializable;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import glm_.mat2x2.Mat2;
import glm_.mat3x3.Mat3;
import glm_.mat4x4.Mat4;
import glm_.vec2.Vec2;
import glm_.vec3.Vec3;
import glm_.vec4.Vec4;

public class Shader implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String vertexShaderCode;
  private String fragmentShaderCode;

  private int program;
  private String name;
  private Config config;

  private boolean isShaderLoaded;

  private final List<ShaderTexture> textures = new ArrayList<>();
  private final List<ShaderTexture> colors = new ArrayList<>();

  public Shader(String name, String vertexShaderCode, String fragmentShaderCode, Config config) {
    this.name = name;
    this.vertexShaderCode = vertexShaderCode;
    this.fragmentShaderCode = fragmentShaderCode;
    this.config = config;
    isShaderLoaded = false;
  }

  public void loadShader() {
    int vertexShader = compileShader(vertexShaderCode, GLES30.GL_VERTEX_SHADER);
    int fragmentShader = compileShader(fragmentShaderCode, GLES30.GL_FRAGMENT_SHADER);

    program = linkProgram(vertexShader, fragmentShader);

    GLES30.glDeleteShader(vertexShader);
    GLES30.glDeleteShader(fragmentShader);
  }

  private int compileShader(String shaderCode, int type) {
    int shader = GLES30.glCreateShader(type);
    GLES30.glShaderSource(shader, shaderCode);
    GLES30.glCompileShader(shader);

    IntBuffer compileStatus = IntBuffer.allocate(1);
    GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compileStatus);
    if (compileStatus.get(0) != GLES30.GL_TRUE) {
      var msg = "Error compiling shader: " + GLES30.glGetShaderInfoLog(shader);
      Log.e("Shader", msg);
      throw new RuntimeException(msg);
    }

    return shader;
  }

  private int linkProgram(int vertexShader, int fragmentShader) {
    if (isShaderLoaded) return program;

    int program = GLES30.glCreateProgram();

    GLES30.glAttachShader(program, vertexShader);
    GLES30.glAttachShader(program, fragmentShader);
    GLES30.glLinkProgram(program);

    IntBuffer linkStatus = IntBuffer.allocate(1);
    GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus);
    if (linkStatus.get(0) != GLES30.GL_TRUE) {
      var msg = "Error linking shader program: " + GLES30.glGetProgramInfoLog(program);
      Log.e("Shader", msg);
      throw new RuntimeException(msg);
    }

    isShaderLoaded = true;
    return program;
  }

  public void use() {
    GLES30.glUseProgram(program);
  }

  public int getAttributeLocation(String name) {
    return GLES30.glGetAttribLocation(program, name);
  }

  public int getUniformLocation(String name) {
    return GLES30.glGetUniformLocation(program, name);
  }

  public void setBoolean(String name, boolean value) {
    setInt(name, value ? 1 : 0);
  }

  public void setInt(String name, int value) {
    GLES30.glUniform1i(getUniformLocation(name), value);
  }

  public void setFloat(String name, float value) {
    GLES30.glUniform1f(getUniformLocation(name), value);
  }

  public void setVec2(String name, float x, float y) {
    GLES30.glUniform2f(getUniformLocation(name), x, y);
  }

  public void setVec3(String name, float x, float y, float z) {
    GLES30.glUniform3f(getUniformLocation(name), x, y, z);
  }

  public void setVec4(String name, float x, float y, float z, float w) {
    GLES30.glUniform4f(getUniformLocation(name), x, y, z, w);
  }

  public void setMat2(String name, float[] matrix) {
    GLES30.glUniformMatrix2fv(getUniformLocation(name), 1, false, matrix, 0);
  }

  public void setMat2(String name, @NonNull Mat2 matrix) {
    setMat2(name, matrix.toFloatArray());
  }

  public void setMat3(String name, float[] matrix) {
    GLES30.glUniformMatrix3fv(getUniformLocation(name), 1, false, matrix, 0);
  }

  public void setMat3(String name, @NonNull Mat3 matrix) {
    setMat3(name, matrix.toFloatArray());
  }

  public void setMat4(String name, float[] matrix) {
    GLES30.glUniformMatrix4fv(getUniformLocation(name), 1, false, matrix, 0);
  }

  public void setMat4(String name, @NonNull Mat4 matrix) {
    setMat4(name, matrix.toFloatArray());
  }

  public int getProgram() {
    if (!isShaderLoaded) throw new RuntimeException("Shader not loaded");
    return program;
  }

  @NonNull
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static Shader create(String name, String vertexShaderCode, String fragmentShaderCode, Config config) {
    return new Shader(name, vertexShaderCode, fragmentShaderCode, config);
  }

  public void setVec2(String name, @NonNull Vec2 vec) {
    setVec2(name, vec.getX(), vec.getY());
  }

  public void setVec3(String name, @NonNull Vec3 value) {
    setVec3(name, value.getX(), value.getY(), value.getZ());
  }

  public void setVec4(String name, @NonNull Vec4 vec) {
    setVec4(name, vec.getX(), vec.getY(), vec.getZ(), vec.getW());
  }

  public void setColor(String name, @NonNull Color color) {
    setVec4(name, color.r, color.g, color.b, color.a);
  }

  public void addTexture(ShaderTexture texture) {
    textures.add(texture);
  }

  public void addColor(ShaderTexture color) {
    colors.add(color);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isShaderLoaded() {
    return isShaderLoaded;
  }

  public void setShaderLoaded(boolean shaderLoaded) {
    isShaderLoaded = shaderLoaded;
  }

  public String getVertexShaderCode() {
    return vertexShaderCode;
  }

  public String getFragmentShaderCode() {
    return fragmentShaderCode;
  }

  public void destroy() {
    GLES30.glDeleteProgram(program);
    program = 0;
    isShaderLoaded = false;
    textures.clear();
    colors.clear();
    config = null;
    fragmentShaderCode = null;
    vertexShaderCode = null;
  }

  public Config getConfig() {
    return config;
  }
}
