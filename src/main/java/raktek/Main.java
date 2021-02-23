package raktek;

import com.google.gwt.core.client.EntryPoint;
import elemental3.Console;
import elemental3.Document;
import elemental3.Global;
import elemental3.HTMLCanvasElement;
import elemental3.core.Float32Array;
import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;
import org.realityforge.vecmath.Matrix4d;
import org.realityforge.vecmath.Vector3f;
import raktek.util.Camera;
import raktek.util.CanvasUtil;
import raktek.util.FMesh;
import raktek.util.GL;
import raktek.util.MathUtil;
import raktek.util.Matrix4fvUniform;
import raktek.util.PerspectiveViewport;
import raktek.util.Program;
import raktek.util.ResourceException;
import raktek.util.Shader;
import raktek.util.Vector3fvUniform;
import raktek.util.controls.FirsPersonControl;
import raktek.util.geometries.CubeGeometryFactory;

public final class Main
  implements EntryPoint
{
  @GLSL
  @Nonnull
  private static final String VERTEX_SHADER_SOURCE =
    // The shader language is OpenGL 3 (i.e. 300) ES and this version pragma must
    // be the first thing present in the shader source
    "#version 300 es\n" +
    // The incoming vertex position
    "in vec3 position;\n" +
    // The incoming vertex color
    "in vec4 color;\n" +
    // The incoming texture coordinate
    "in vec2 uv;\n" +
    // The incoming normal of vertex
    "in vec3 normal;\n" +
    // The output vertex color that will be fed to the next shader
    "out vec4 fcolor;\n" +
    // The outgoing texture coordinate
    "out vec2 fTextureCoordinate;\n" +
    "out vec4 vertexWorldPosition;\n" +
    "out vec3 vertexNormal;\n" +
    "\n" +
    // The following are the unions (aka constant across multiple vertices)
    "uniform mat4 modelMatrix;\n" +
    "uniform mat4 viewMatrix;\n" +
    "uniform mat4 projectionMatrix;\n" +
    // The main program/kernel
    "void main()\n" +
    "{\n" +
    "  vertexWorldPosition = modelMatrix * vec4(position, 1);\n" +
    // Copy position from input to output, converting to vec4 by adding using 1 for 4th dimension
    // and transforming via model/view/project matrices
    "  gl_Position = projectionMatrix * viewMatrix * vertexWorldPosition;" +
    // Copy color from input to output
    "  fcolor = color;" +
    // Copy textureCoordinate from input to output
    "  fTextureCoordinate = uv;" +
    // inverse(transpose(MM)) is required when we have a non-uniform scale.
    // In a uniform scale, the transpose of the identity is identity and the
    // inverse of an identity is identity and thus we could just use normal here
    // In a performant application, the calculation of normal matrix should be
    // done on the CPU so it is done once per-modelMatrix rather than once per
    // vertex
    "  vertexNormal = mat3(inverse(transpose(modelMatrix)))*normal;" +
    "}\n";
  // The fragment shader that will be run for every pixel
  @GLSL
  private static final String FRAGMENT_SHADER_SOURCE =
    // The version of language in use
    "#version 300 es\n" +
    // There is no default precision for floats in fragment shaders so specify it
    "precision mediump float;\n" +
    // The incoming fragment color
    "in vec4 fcolor;\n" +
    // The incoming fragment texture coordinate
    "in vec2 fTextureCoordinate;\n" +
    // The incoming interpolated vertex normal
    "in vec3 vertexNormal;\n" +
    // The position
    "in vec4 vertexWorldPosition;\n" +
    // The uniform for texture data
    "uniform sampler2D textureData0;\n" +
    // The uniform for other texture data
    "uniform sampler2D textureData1;\n" +
    // Ambient light color
    "uniform vec3 lightColor;\n" +
    // Position of the light giving the directional light
    "uniform vec3 lightPosition;\n" +
    // Position of the eye/camera
    "uniform vec3 cameraPosition;\n" +
    // The output fragment color
    "out vec4 finalColor;\n" +
    "" +
    // The main program/kernel
    "void main()\n" +
    "{\n" +
    // Calculate the ambient component of the light
    "  float ambientIntensity = 0.2;\n" +
    "  vec4 ambientComponent = vec4((ambientIntensity * lightColor), 1.0);\n" +
    // Due to interpolation this normal is no longer normalized
    "  vec3 normalizedNormal = normalize(vertexNormal);\n" +
    // Calculate the light direction in in world-space coordinates
    "  vec3 lightDirection = normalize(lightPosition - vec3(vertexWorldPosition));\n" +
    // Calculate the view direction in in world-space coordinates
    "  vec3 viewDirection = normalize(cameraPosition - vec3(vertexWorldPosition));\n" +

    // Calculate diffuse contribution based on normal on surface and position of light
    "  float diffuseFactor = 0.9;\n" +
    // max(x, 0) avoids the scenario where a fragment is lit from behind
    "  float diffuseIntensity = max(dot(normalizedNormal, lightDirection), 0.0);\n" +
    "  vec4 diffuseComponent = diffuseFactor * vec4(diffuseIntensity * lightColor, 1.0);\n" +

    // Calculate specular contribution based on position of light and position of eye.
    "  vec3 reflectedLightDirection = reflect(-lightDirection, normalizedNormal);\n" +
    // The specular intensity is based on the angle the eye and the reflected light beam
    "  float baseSpecularIntensity = max(dot(reflectedLightDirection, viewDirection), 0.0);\n" +
    // However the drop off is fast so we simulate this via pow function
    // as dot product is cosine which is less than 1 and pow will thus  increase drop off
    "  float specularIntensity = pow(baseSpecularIntensity, 128.0);\n" +
    "  vec4 specularComponent = vec4(specularIntensity * lightColor,1);\n" +

    "  finalColor = (ambientComponent + diffuseComponent + specularComponent) * mix( texture( textureData0, fTextureCoordinate ), texture( textureData1, fTextureCoordinate ), 0.5) * fcolor;" +
    "}\n";
  // The vertex shader for the "light" cube
  @GLSL
  @Nonnull
  private static final String LIGHT_VERTEX_SHADER_SOURCE =
    "#version 300 es\n" +
    "in vec3 position;\n" +
    "uniform mat4 modelMatrix;\n" +
    "uniform mat4 viewMatrix;\n" +
    "uniform mat4 projectionMatrix;\n" +
    "void main()\n" +
    "{\n" +
    "  gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1);" +
    "}\n";
  // The fragment shader that will be run for every pixel
  @GLSL
  @Nonnull
  private static final String LIGHT_FRAGMENT_SHADER_SOURCE =
    "#version 300 es\n" +
    "precision lowp float;\n" +
    "uniform vec3 color;\n" +
    "out vec4 finalColor;\n" +
    "void main()\n" +
    "{\n" +
    "  finalColor = vec4(color,1);" +
    "}\n";
  @Nonnull
  private final Matrix4d _modelMatrix = new Matrix4d();
  @Nonnull
  private final Camera _camera = new Camera();
  @Nonnull
  private final Light _light = new Light();
  private Mesh _mesh;
  private FMesh _lightMesh;
  private double _angle;
  private boolean _sentToGpu;
  private float _time;
  private FirsPersonControl _control;
  private PerspectiveViewport _viewport;
  private boolean _printed;

  @Override
  public void onModuleLoad()
  {
    final HTMLCanvasElement canvas = CanvasUtil.createCanvas();
    final WebGL2RenderingContext gl = CanvasUtil.getWebGL2RenderingContext( canvas );
    _viewport = new PerspectiveViewport( gl, canvas );
    _viewport.setFovY( MathUtil.degreesToRadians( 45 ) );
    _viewport.setZNear( 0.1 );
    _viewport.setZFar( 10.0 );

    try
    {
      final Program program = new Program( gl,
                                           "Mesh",
                                           new Shader( gl,
                                                       null,
                                                       WebGL2RenderingContext.VERTEX_SHADER,
                                                       VERTEX_SHADER_SOURCE ),
                                           new Shader( gl,
                                                       null,
                                                       WebGL2RenderingContext.FRAGMENT_SHADER,
                                                       FRAGMENT_SHADER_SOURCE ) );

      _mesh = new Mesh( gl,
                        program,
                        CubeGeometryFactory.create( gl,
                                                    program,
                                                    0.5,
                                                    CubeGeometryFactory.NORMALS |
                                                    CubeGeometryFactory.UVS |
                                                    CubeGeometryFactory.COLORS ) );
      final Program lmProgram =
        new Program( gl,
                     "LightMesh",
                     new Shader( gl, null, WebGL2RenderingContext.VERTEX_SHADER, LIGHT_VERTEX_SHADER_SOURCE ),
                     new Shader( gl, null, WebGL2RenderingContext.FRAGMENT_SHADER, LIGHT_FRAGMENT_SHADER_SOURCE ) );

      _lightMesh = new FMesh( lmProgram, CubeGeometryFactory.create( gl, lmProgram, 0.2, 0 ) );
    }
    catch ( final ResourceException e )
    {
      Console.log( "Error", e );
    }

    final Document document = Global.document();
    _control = new FirsPersonControl( _camera, document );

    renderLoop( gl );
  }

  private void renderLoop( @Nonnull final WebGL2RenderingContext gl )
  {
    Global.requestAnimationFrame( t -> renderLoop( gl ) );
    _viewport.resize();
    renderFrame( gl );
  }

  private void renderFrame( @Nonnull final WebGL2RenderingContext gl )
  {
    if ( null == _mesh.getTexture1() || null == _mesh.getTexture2() )
    {
      return;
    }
    else if ( !_sentToGpu )
    {
      // Have to send to GPU here as otherwise texture data has not loaded
      try
      {
        _mesh.getVertexArrayObject().allocate();
      }
      catch ( final ResourceException e )
      {
        Console.log( "Error", e );
      }

      _mesh.getProgram().bind();
      GL.bindTexture( gl, _mesh.getTextureData0(), _mesh.getTexture1(), 0 );
      GL.bindTexture( gl, _mesh.getTextureData1(), _mesh.getTexture2(), 1 );
      _sentToGpu = true;
    }

    gl.clearColor( 0, 0, 0, 1 );
    gl.clear( WebGL2RenderingContext.COLOR_BUFFER_BIT | WebGL2RenderingContext.DEPTH_BUFFER_BIT );
    gl.enable( WebGL2RenderingContext.DEPTH_TEST );

    // UpdateCamera should be done in the sim loop ... but we are inlining in render loop
    _control.updateCamera();

    final Vector3f position = _light.getPosition();
    position.y = (float) ( 2 * Math.sin( 0.1 * ( _time + position.y ) ) );
    position.x = (float) ( -2 * Math.sin( 0.1 * ( _time + position.x ) ) );

    final Matrix4d viewMatrix = _camera.getViewMatrix();

    _mesh.getProgram().bind();

    if ( !_printed )
    {
      Console.log( _mesh.getProgram().getDescriptor().toString() );
      _printed = true;
    }

    // ModelMatrix should be calculated in the simulation loop rather than render loop
    // but they are effectively the same in out app so we can just recalculate in render loop
    _modelMatrix.setTranslation( 0, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    final Matrix4d projectionMatrix = _viewport.getProjectionMatrix();

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( 3, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( -3, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( position.x, position.y, position.z );
    _modelMatrix.scale( 0.2 );

    // These unbinds are not really necessary but adding until we get an easier to track framework
    _mesh.getProgram().unbind();

    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "modelMatrix" ) ).setValues( new Float32Array( _modelMatrix.toArray() ) );
    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "viewMatrix" ) ).setValues( new Float32Array( viewMatrix.toArray() ) );
    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "projectionMatrix" ) )
      .setValues( new Float32Array( projectionMatrix.toArray() ) );
    ( (Vector3fvUniform) _lightMesh.getUniformByName( "color" ) )
      .setValues( new Float32Array( _light.getColor().toArray() ) );
    _lightMesh.render( gl );

    _angle += 0.01;
    _time += 0.1;
  }
}
