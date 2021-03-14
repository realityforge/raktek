package raktek;

import akasha.core.Float32Array;
import akasha.gl.WebGL2RenderingContext;
import akasha.gl.WebGLTexture;
import akasha.gl.WebGLUniformLocation;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.vecmath.Matrix4d;
import org.realityforge.vecmath.Vector3d;
import org.realityforge.vecmath.Vector3f;
import raktek.util.Camera;
import raktek.util.GL;
import raktek.util.Program;
import raktek.util.ProgramDescriptor;
import raktek.util.VertexArrayObject;

final class Mesh
{
  @Nonnull
  private final VertexArrayObject _vertexArrayObject;
  private WebGLTexture _texture1;
  private WebGLTexture _texture2;
  @Nonnull
  private final Program _program;
  @Nonnull
  private final WebGLUniformLocation _modelMatrix;
  @Nonnull
  private final WebGLUniformLocation _viewMatrix;
  @Nonnull
  private final WebGLUniformLocation _projectionMatrix;
  @Nonnull
  private final WebGLUniformLocation _textureData0;
  @Nonnull
  private final WebGLUniformLocation _textureData1;
  @Nonnull
  private final WebGLUniformLocation _lightColor;
  @Nonnull
  private final WebGLUniformLocation _lightPosition;
  @Nonnull
  private final WebGLUniformLocation _cameraPosition;

  Mesh( @Nonnull final WebGL2RenderingContext gl,
        @Nonnull final Program program,
        @Nonnull final VertexArrayObject vertexArrayObject )
  {
    GL.loadTexture( gl, "img/wood.jpg" ).thenAccept( texture -> _texture1 = texture );
    GL.loadTexture( gl, "img/StoreLogo.png" ).thenAccept( texture -> _texture2 = texture );

    _program = Objects.requireNonNull( program );
    _program.allocate();
    _program.verify();
    final ProgramDescriptor descriptor = _program.getDescriptor();
    _modelMatrix = descriptor.getUniformByName( "modelMatrix" ).getLocation();
    _viewMatrix = descriptor.getUniformByName( "viewMatrix" ).getLocation();
    _projectionMatrix = descriptor.getUniformByName( "projectionMatrix" ).getLocation();
    _textureData0 = descriptor.getUniformByName( "textureData0" ).getLocation();
    _textureData1 = descriptor.getUniformByName( "textureData1" ).getLocation();
    _lightColor = descriptor.getUniformByName( "lightColor" ).getLocation();
    _lightPosition = descriptor.getUniformByName( "lightPosition" ).getLocation();
    _cameraPosition = descriptor.getUniformByName( "cameraPosition" ).getLocation();

    _vertexArrayObject = Objects.requireNonNull( vertexArrayObject );
    _vertexArrayObject.allocate();
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
    gl.uniformMatrix4fv( _modelMatrix, false, new Float32Array( modelMatrix.toArray() ) );
    gl.uniformMatrix4fv( _viewMatrix, false, new Float32Array( viewMatrix.toArray() ) );
    gl.uniformMatrix4fv( _projectionMatrix, false, new Float32Array( projectionMatrix.toArray() ) );
    final Vector3f color = light.getColor();
    gl.uniform3f( _lightColor, color.x, color.y, color.z );
    final Vector3f lightPosition = light.getPosition();
    gl.uniform3f( _lightPosition, lightPosition.x, lightPosition.y, lightPosition.z );

    final Vector3d eye = camera.getPosition();
    gl.uniform3f( _cameraPosition, (float) eye.x, (float) eye.y, (float) eye.z );

    _vertexArrayObject.draw();
  }

  @Nonnull
  VertexArrayObject getVertexArrayObject()
  {
    return _vertexArrayObject;
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
  WebGLUniformLocation getTextureData0()
  {
    return _textureData0;
  }

  @Nonnull
  WebGLUniformLocation getTextureData1()
  {
    return _textureData1;
  }
}
