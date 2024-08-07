#!/bin/bash
#
# This file is a part of WyrmEngine.
#
# This software is provided 'as-is', without any express or implied warranty.
# In no event will the authors be held liable for any damages arising from the use of this software.
#
# For terms of use and licensing, please see the End-User License Agreement (EULA).
#

## -V: create SPIR-V binary
## -x: save binary output as text-based 32-bit hexadecimal numbers
## -o: output file
glslangValidator -V -x -o glsl_shader.frag.u32 glsl_shader.frag
glslangValidator -V -x -o glsl_shader.vert.u32 glsl_shader.vert
