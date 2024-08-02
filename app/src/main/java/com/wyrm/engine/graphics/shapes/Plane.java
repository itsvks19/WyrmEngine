package com.wyrm.engine.graphics.shapes;

import android.content.Context;
import android.opengl.GLES30;

import androidx.annotation.NonNull;

import com.wyrm.engine.core.components.camera.Camera;
import com.wyrm.engine.ext.Sizes;
import com.wyrm.engine.graphics.Texture;
import com.wyrm.engine.graphics.shader.Config;
import com.wyrm.engine.graphics.shader.Shader;
import com.wyrm.engine.utils.BufferUtils;
import com.wyrm.engine.utils.FileUtil;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

public class Plane {
  private final float[] planeVertices = {
    // positions         // normals        // texcoords
    10.0f, -0.5f, 10.0f, 0.0f, 1.0f, 0.0f, 10.0f, 0.0f,
    -10.0f, -0.5f, 10.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
    -10.0f, -0.5f, -10.0f, 0.0f, 1.0f, 0.0f, 0.0f, 10.0f,

    10.0f, -0.5f, 10.0f, 0.0f, 1.0f, 0.0f, 10.0f, 0.0f,
    -10.0f, -0.5f, -10.0f, 0.0f, 1.0f, 0.0f, 0.0f, 10.0f,
    10.0f, -0.5f, -10.0f, 0.0f, 1.0f, 0.0f, 10.0f, 10.0f
  };

  private final int[] vao = new int[1];
  private final Vec3 lightPos = new Vec3(0f, 0f, 0f);

  private Shader shader;
  private Texture texture;

  private boolean blinn = true;

  public void setup(Context context) {
    shader = Shader.create(
      "Simple",
      FileUtil.readFromAssets(context, "engine/shaders/plane.vert"),
      FileUtil.readFromAssets(context, "engine/shaders/plane.frag"),
      new Config("")
    );
    shader.loadShader();

    GLES30.glGenVertexArrays(1, vao, 0);
    GLES30.glBindVertexArray(vao[0]);

    int[] vbo = new int[1];
    GLES30.glGenBuffers(1, vbo, 0);
    GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0]);
    GLES30.glBufferData(
      GLES30.GL_ARRAY_BUFFER,
      planeVertices.length * Sizes.FLOAT_SIZE,
      BufferUtils.toFloatBuffer(planeVertices),
      GLES30.GL_STATIC_DRAW
    );

    GLES30.glEnableVertexAttribArray(0);
    GLES30.glVertexAttribPointer(
      0,
      3,
      GLES30.GL_FLOAT,
      false,
      8 * Sizes.FLOAT_SIZE,
      0
    );
    GLES30.glEnableVertexAttribArray(1);
    GLES30.glVertexAttribPointer(
      1,
      3,
      GLES30.GL_FLOAT,
      false,
      8 * Sizes.FLOAT_SIZE,
      3 * Sizes.FLOAT_SIZE
    );
    GLES30.glEnableVertexAttribArray(2);
    GLES30.glVertexAttribPointer(
      2,
      2,
      GLES30.GL_FLOAT,
      false,
      8 * Sizes.FLOAT_SIZE,
      6 * Sizes.FLOAT_SIZE
    );

    texture = new Texture(context, "engine/images/road.png", true);
    texture.load();

    shader.use();
    shader.setInt("floorTexture", 0);
  }

  public void draw(@NonNull Mat4 projectionMatrix, @NonNull Camera camera) {
    shader.use();
    shader.setMat4("projection", projectionMatrix.toFloatArray());
    shader.setMat4("view", camera.getViewMatrix().toFloatArray());

    shader.setVec3("viewPos", camera.getPosition());
    shader.setVec3("lightPos", lightPos);
    shader.setBoolean("blinn", blinn);

    GLES30.glBindVertexArray(vao[0]);
    GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture.getId());
    GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 6);
  }

  public void setBlinn(boolean blinn) {
    this.blinn = blinn;
  }

  public boolean isBlinn() {
    return blinn;
  }
}
