/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.raycast;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import glm_.vec3.Vec3;

public class RayPicker {
  @NonNull
  @Contract("_, _, _, _, _, _ -> new")
  public static Ray getRayFromTouch(float touchX, float touchY, int screenWidth, int screenHeight, float[] viewMatrix, float[] projectionMatrix) {
    float[] rayClip = {2.0f * touchX / screenWidth - 1.0f, 1.0f - 2.0f * touchY / screenHeight, -1.0f, 1.0f};
    float[] rayEye = new float[4];
    float[] invertedProjectionMatrix = new float[16];
    Matrix.invertM(invertedProjectionMatrix, 0, projectionMatrix, 0);
    Matrix.multiplyMV(rayEye, 0, invertedProjectionMatrix, 0, rayClip, 0);
    rayEye[2] = -1.0f;
    rayEye[3] = 0.0f;

    float[] rayWorld = new float[4];
    float[] invertedViewMatrix = new float[16];
    Matrix.invertM(invertedViewMatrix, 0, viewMatrix, 0);
    Matrix.multiplyMV(rayWorld, 0, invertedViewMatrix, 0, rayEye, 0);

    return new Ray(new Vec3(rayWorld[0], rayWorld[1], rayWorld[2]), new Vec3(rayWorld[0], rayWorld[1], rayWorld[2]).normalize());
  }
}

