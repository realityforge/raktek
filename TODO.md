* Add FMesh.toDebugString that lists program name, vertexArrayObject name as well as all uniforms that need to be supplied.

* Add util to convert component type+count into composite types for attributes

* Add a Shader Manager that contains a cache of all shader objects by name. The cache should also include the
  number of references and when it was last used so that old shaders can be deleted

* Add a ProgramManager that contains a cache all programs by name. The cache should indicate whether the number
  of references and when it was last used.

* Add support for dynamically loading shaders from urls and determine type from extension.
  (i.e. `*.vert` => vertex shader, `*.frag` => fragment shader)

* Link program input names and attribute names via intents/semantics rather than names. Uniforms
  required for programs should also have an intent/semantic associated
  with so can bind  based semantics. Consider intents from
  - https://github.com/jmarshall23/IcedTech/blob/Doom-3-Vanilla-Compatible/code/renderer/RenderProgs_GLSL.cpp

* Add flags to buffer to indicate whether the data has been uploaded or needs to be uploaded before next use.

* Add restore functionality for when WebGL context loss occurs. This should be possible for every
  `Resource` that will just retrieve new context and re-allocate?