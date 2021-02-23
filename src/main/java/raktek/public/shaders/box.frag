#version 300 es
// There is no default precision for floats in fragment shaders so specify it
precision mediump float;
// The incoming fragment color
in vec4 fcolor;
// The incoming fragment texture coordinate
in vec2 fTextureCoordinate;
// The incoming interpolated vertex normal
in vec3 vertexNormal;
// The position
in vec4 vertexWorldPosition;
// The uniform for texture data
uniform sampler2D textureData0;
// The uniform for other texture data
uniform sampler2D textureData1;
// Ambient light color
uniform vec3 lightColor;
// Position of the light giving the directional light
uniform vec3 lightPosition;
// Position of the eye/camera
uniform vec3 cameraPosition;
// The output fragment color
out vec4 finalColor;

// The main program/kernel
void main()
{
    // Calculate the ambient component of the light
    float ambientIntensity = 0.2;
    vec4 ambientComponent = vec4((ambientIntensity * lightColor), 1.0);
    // Due to interpolation this normal is no longer normalized
    vec3 normalizedNormal = normalize(vertexNormal);
    // Calculate the light direction in in world-space coordinates
    vec3 lightDirection = normalize(lightPosition - vec3(vertexWorldPosition));
    // Calculate the view direction in in world-space coordinates
    vec3 viewDirection = normalize(cameraPosition - vec3(vertexWorldPosition));

    // Calculate diffuse contribution based on normal on surface and position of light
    float diffuseFactor = 0.9;
    // max(x, 0) avoids the scenario where a fragment is lit from behind
    float diffuseIntensity = max(dot(normalizedNormal, lightDirection), 0.0);
    vec4 diffuseComponent = diffuseFactor * vec4(diffuseIntensity * lightColor, 1.0);

    // Calculate specular contribution based on position of light and position of eye.
    vec3 reflectedLightDirection = reflect(-lightDirection, normalizedNormal);
    // The specular intensity is based on the angle the eye and the reflected light beam
    float baseSpecularIntensity = max(dot(reflectedLightDirection, viewDirection), 0.0);
    // However the drop off is fast so we simulate this via pow function
    // as dot product is cosine which is less than 1 and pow will thus  increase drop off
    float specularIntensity = pow(baseSpecularIntensity, 128.0);
    vec4 specularComponent = vec4(specularIntensity * lightColor, 1);

    finalColor = (ambientComponent + diffuseComponent + specularComponent) * mix(texture(textureData0, fTextureCoordinate), texture(textureData1, fTextureCoordinate), 0.5) * fcolor;
}
