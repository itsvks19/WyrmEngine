package com.wyrm.engine.managers

import com.wyrm.engine.Constants
import com.wyrm.engine.ext.doIf
import com.wyrm.engine.ext.getCoreContext
import com.wyrm.engine.ext.getCoreInstance
import com.wyrm.engine.ext.readFromAsset
import com.wyrm.engine.graphics.shader.Config
import com.wyrm.engine.graphics.shader.Shader
import com.wyrm.engine.model.shader.ShaderTexture

class ShaderManager {
  companion object {
    @JvmStatic
    val instance by lazy { ShaderManager() }
  }

  private val shaders = mutableListOf<Shader>()

  fun addShader(shader: Shader) {
    shaders.add(shader)
  }

  fun getShader(name: String): Shader {
    return shaders.find { it.name == name } ?: run {
      val context = getCoreContext()

      var shader = Shader(
        name,
        context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/$name.vert"),
        context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/$name.frag"),
        Config(context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/$name.config"))
      )

      if (shader.vertexShaderCode.isEmpty() || shader.fragmentShaderCode.isEmpty()) {
        getCoreInstance().console.logError("$name: SHADER COULDN'T BE FOUND.. DIFFUSE WILL BE LOADED INSTEAD")
        shader = Shader(
          "Diffuse/Diffuse",
          context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/diffuse.vert"),
          context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/diffuse.frag"),
          Config(context.readFromAsset("${Constants.ENGINE_SHADERS_PATH}/diffuse.config"))
        )
      }

      doIf(shader.config) {
        val configs = shader.config.configs

        doIf(configs.contains("albedoTexture")) {
          shader.addTexture(ShaderTexture("Texture", "albedoTexture"))
        }
        doIf(configs.contains("diffuseColor")) {
          shader.addColor(ShaderTexture("Color", "diffuseColor"))
        }
        doIf(configs.contains("diffuseLightReflection")) {
          shader.addColor(ShaderTexture("Light Reflection", "lightReflection"))
        }
      }
      addShader(shader)
      shader
    }
  }
}