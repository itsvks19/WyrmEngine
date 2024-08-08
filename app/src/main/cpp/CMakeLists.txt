# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("WyrmEngine")

set(LAYER_SRC_DIR ${ANDROID_NDK}/sources/third_party/vulkan/src)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wno-unused-variable -DUSE_DEBUG_EXTENSIONS -DVK_USE_PLATFORM_ANDROID_KHR")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#
# In this top level CMakeLists.txt, ${CMAKE_PROJECT_NAME} is used to define
# the target library name; in the sub-module's CMakeLists.txt, ${PROJECT_NAME}
# is preferred for the same purpose.
#
# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
# for GameActivity/NativeActivity derived applications, the same library name must be
# used in the AndroidManifest.xml file.
add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        engine/managers/project_manager.cpp
        engine/core/imgui/input/imgui_input_handler.cpp
        engine/core/imgui/imgui_renderer_helper.cpp
        engine/core/imgui/main_menu_bar.cpp
        engine/core/renderer/im_gui_android_helper.cpp
        engine/core/renderer/imgui_scene_data.cpp
        engine/graphics/scene/scene.cpp
        engine/graphics/scene/settings/light_settings.cpp
        engine/graphics/shader/shader.cpp
        engine/utils/util.cpp
        engine/ext/toast.cpp
        engine/wyrm_application.cpp
        third-party/imgui/imgui.cpp
        third-party/imgui/imgui_demo.cpp
        third-party/imgui/imgui_draw.cpp
        third-party/imgui/imgui_tables.cpp
        third-party/imgui/imgui_widgets.cpp
        third-party/imgui/backends/imgui_impl_android.cpp
        third-party/imgui/backends/imgui_impl_opengl3.cpp
        third-party/imgui/backends/imgui_impl_vulkan.cpp
)

add_subdirectory(third-party/glm)

include_directories(${LAYER_SRC_DIR}/include)

target_compile_definitions(${CMAKE_PROJECT_NAME} PRIVATE
        IMGUI_IMPL_OPENGL_ES3
)

# Specifies libraries CMake should link to your target library. You
# can link libraries from various origins, such as libraries defined in this
# build script, prebuilt third-party libraries, or Android system libraries.
target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        log
        GLESv3
        EGL
        glm
        vulkan
)

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}
        third-party
        engine
        third-party/imgui
        third-party/imgui/backends
)