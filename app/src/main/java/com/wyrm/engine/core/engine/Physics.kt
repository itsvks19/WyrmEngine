/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.engine

import com.bulletphysics.collision.broadphase.BroadphaseInterface
import com.bulletphysics.collision.broadphase.DbvtBroadphase
import com.bulletphysics.collision.broadphase.Dispatcher
import com.bulletphysics.collision.dispatch.CollisionConfiguration
import com.bulletphysics.collision.dispatch.CollisionDispatcher
import com.bulletphysics.collision.dispatch.CollisionObject
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration
import com.bulletphysics.dynamics.DiscreteDynamicsWorld
import com.bulletphysics.dynamics.DynamicsWorld
import com.bulletphysics.dynamics.InternalTickCallback
import com.bulletphysics.dynamics.RigidBody
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver
import com.wyrm.engine.core.objects.BulletGameObject
import glm_.vec3.Vec3
import javax.vecmath.Vector3f

/// WIP
class Physics {
  companion object {
    @JvmStatic
    val gravity = Vec3(0f, -9.81f, 0f)
  }

  private var broadphaseInterface: BroadphaseInterface = DbvtBroadphase()
  private var collisionConfiguration: CollisionConfiguration = DefaultCollisionConfiguration()
  private var dispatcher: Dispatcher = CollisionDispatcher(collisionConfiguration)
  private var solver: ConstraintSolver = SequentialImpulseConstraintSolver()

  private val runningObjects = mutableListOf<BulletGameObject>()

  private lateinit var dynamicsWorld: DiscreteDynamicsWorld

  init {
    createWorld()
  }

  private fun createWorld() {
    dynamicsWorld = DiscreteDynamicsWorld(
      dispatcher,
      broadphaseInterface,
      solver,
      collisionConfiguration
    ).apply {
      debugDrawer = null
      setInternalTickCallback(object : InternalTickCallback() {
        override fun internalTick(world: DynamicsWorld, timeStep: Float) {
          val dispatcher = world.dispatcher
          val numManifolds = dispatcher.numManifolds

          for (i in 0 until numManifolds) {
            val manifold = dispatcher.getManifoldByIndexInternal(i)

            val objA = manifold.body0
            val bulletGameObjectA: BulletGameObject
            if (objA is RigidBody) {
              bulletGameObjectA = objA.userPointer as BulletGameObject
            } else if (objA is CollisionObject) {
              bulletGameObjectA = objA.userPointer as BulletGameObject
            }

            val objB = manifold.body1
            val bulletGameObjectB: BulletGameObject
            if (objB is RigidBody) {
              bulletGameObjectB = objB.userPointer as BulletGameObject
            } else if (objB is CollisionObject) {
              bulletGameObjectB = objB.userPointer as BulletGameObject
            }

            var contactNormal: Vector3f
            var contactOccurred = false
            for (j in 0 until manifold.numContacts) {
              val contactPoint = manifold.getContactPoint(j)
              if (contactPoint.distance < 0f) {
                contactNormal = contactPoint.normalWorldOnB
                contactOccurred = true
                break
              }
            }
          }
        }
      }, null)
    }
  }
}