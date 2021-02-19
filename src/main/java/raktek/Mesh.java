package raktek;

import elemental3.core.Float32Array;
import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLTexture;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.vecmath.Matrix4d;
import org.realityforge.vecmath.Vector3d;
import org.realityforge.vecmath.Vector3f;
import raktek.util.Camera;
import raktek.util.GL;
import raktek.util.Geometry;
import raktek.util.Program;
import raktek.util.ProgramDescriptor;
import raktek.util.Shader;
import raktek.util.Uniform;

final class Mesh
{
  @Nonnull
  private final Geometry _geometry;
  private WebGLTexture _texture1;
  private WebGLTexture _texture2;
  @Nonnull
  private final Program _program;
  @Nonnull
  private final Uniform _modelMatrix;
  @Nonnull
  private final Uniform _viewMatrix;
  @Nonnull
  private final Uniform _projectionMatrix;
  @Nonnull
  private final Uniform _textureData0;
  @Nonnull
  private final Uniform _textureData1;
  @Nonnull
  private final Uniform _lightColor;
  @Nonnull
  private final Uniform _lightPosition;
  @Nonnull
  private final Uniform _cameraPosition;

  Mesh( @Nonnull final WebGL2RenderingContext gl,
        @Nonnull final Geometry geometry,
        @GLSL @Nonnull final String vertexShaderSource,
        @GLSL @Nonnull final String fragmentShaderSource )
  {
    GL.loadTexture( gl, "img/wood.jpg" ).thenAccept( texture -> _texture1 = texture );
    GL.loadTexture( gl, "img/StoreLogo.png" ).thenAccept( texture -> _texture2 = texture );

    _program = new Program( gl,
                            "Mesh",
                            new Shader( gl, null, WebGL2RenderingContext.VERTEX_SHADER, vertexShaderSource ),
                            new Shader( gl, null, WebGL2RenderingContext.FRAGMENT_SHADER, fragmentShaderSource ) );
    _program.allocate();
    _program.verify();
    final ProgramDescriptor descriptor = _program.getDescriptor();
    _modelMatrix = new Uniform( "modelMatrix", descriptor.getUniformByName( "modelMatrix" ).getLocation() );
    _viewMatrix = new Uniform( "viewMatrix", descriptor.getUniformByName( "viewMatrix" ).getLocation() );
    _projectionMatrix =
      new Uniform( "projectionMatrix", descriptor.getUniformByName( "projectionMatrix" ).getLocation() );
    _textureData0 = new Uniform( "textureData0", descriptor.getUniformByName( "textureData0" ).getLocation() );
    _textureData1 = new Uniform( "textureData1", descriptor.getUniformByName( "textureData1" ).getLocation() );
    _lightColor = new Uniform( "lightColor", descriptor.getUniformByName( "lightColor" ).getLocation() );
    _lightPosition = new Uniform( "lightPosition", descriptor.getUniformByName( "lightPosition" ).getLocation() );
    _cameraPosition = new Uniform( "cameraPosition", descriptor.getUniformByName( "cameraPosition" ).getLocation() );

    _geometry = Objects.requireNonNull( geometry );
    _geometry.getAttribute( 0 ).setLocation( descriptor.getAttributeByName( "position" ).getIndex() );
    _geometry.getAttribute( 1 ).setLocation( descriptor.getAttributeByName( "color" ).getIndex() );
    _geometry.getAttribute( 2 ).setLocation( descriptor.getAttributeByName( "textureCoordinate" ).getIndex() );
    _geometry.getAttribute( 3 ).setLocation( descriptor.getAttributeByName( "normal" ).getIndex() );
  }

  @Nonnull
  Program getProgram()
  {
    return _program;
  }

  void render( @Nonnull final WebGL2RenderingContext gl,
               @Nonnull final Matrix4d modelMatrix,
               @Nonnull final Matrix4d viewMatrix,
               @Nonnull final Matrix4d projectionMatrix,
               @Nonnull final Light light,
               @Nonnull final Camera camera )
  {
    gl.uniformMatrix4fv( _modelMatrix.getLocation(), false, new Float32Array( modelMatrix.toArray() ) );
    gl.uniformMatrix4fv( _viewMatrix.getLocation(), false, new Float32Array( viewMatrix.toArray() ) );
    gl.uniformMatrix4fv( _projectionMatrix.getLocation(), false, new Float32Array( projectionMatrix.toArray() ) );
    final Vector3f color = light.getColor();
    gl.uniform3f( _lightColor.getLocation(), color.x, color.y, color.z );
    final Vector3f lightPosition = light.getPosition();
    gl.uniform3f( _lightPosition.getLocation(), lightPosition.x, lightPosition.y, lightPosition.z );

    final Vector3d eye = camera.getPosition();
    gl.uniform3f( _cameraPosition.getLocation(), (float) eye.x, (float) eye.y, (float) eye.z );

    _geometry.draw();
  }

  @Nonnull
  Geometry getGeometry()
  {
    return _geometry;
  }

  WebGLTexture getTexture1()
  {
    return _texture1;
  }

  WebGLTexture getTexture2()
  {
    return _texture2;
  }

  @Nonnull
  Uniform getTextureData0()
  {
    return _textureData0;
  }

  @Nonnull
  Uniform getTextureData1()
  {
    return _textureData1;
  }
}
