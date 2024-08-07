/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.objects;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;
import com.wyrm.engine.core.components.Component;

import glm_.vec3.Vec3;

public class BulletGameObject extends GameObject {
  public CollisionObject collisionObject;
  public CollisionShape collisionShape;
  public Component component;
  public Vec3 localIntertia;
  public MotionState motionState;
  public RigidBody rigidBody;
  public GameObject object;
}
