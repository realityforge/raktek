#version 300 es
// The shader language is OpenGL 3 (i.e. 300) ES and this version pragma must
// be the first thing present in the shader source

// The incoming vertex position
in vec3 position;
// The incoming vertex color
in vec4 color;
// The incoming texture coordinate
in vec2 uv;
// The incoming normal of vertex
in vec3 normal;
// The output vertex color that will be fed to the next shader
out vec4 fcolor;
// The outgoing texture coordinate
out vec2 fTextureCoordinate;
out vec4 vertexWorldPosition;
out vec3 vertexNormal;

// The following are the unions (aka constant across multiple vertices)
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

// The main program/kernel
void main()
{
    vertexWorldPosition = modelMatrix * vec4(position, 1);
    // Copy position from input to output, converting to vec4 by adding using 1 for 4th dimension
    // and transforming via model/view/project matrices
    gl_Position = projectionMatrix * viewMatrix * vertexWorldPosition;
    // Copy color from input to output
    fcolor = color;
    // Copy textureCoordinate from input to output
    fTextureCoordinate = uv;
    // inverse(transpose(MM)) is required when we have a non-uniform scale.
    // In a uniform scale, the transpose of the identity is identity and the
    // inverse of an identity is identity and thus we could just use normal here
    // In a performant application, the calculation of normal matrix should be
    // done on the CPU so it is done once per-modelMatrix rather than once per
    // vertex
    vertexNormal = mat3(inverse(transpose(modelMatrix)))*normal;
}
