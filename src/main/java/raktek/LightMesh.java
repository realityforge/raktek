package raktek;

import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLProgram;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.util.GL;
import raktek.util.Geometry;
import raktek.util.ResourceException;
import raktek.util.Uniform;

final class LightMesh
{
  @Nonnull
  private final WebGLProgram _program;
  @Nonnull
  private final Uniform _modelMatrix;
  @Nonnull
  private final Uniform _viewMatrix;
  @Nonnull
  private final Uniform _projectionMatrix;
  @Nonnull
  private final Uniform _color;
  @Nonnull
  private final Geometry _geometry;

  LightMesh( @Nonnull final WebGL2RenderingContext gl,
             @Nonnull final Geometry geometry,
             @GLSL @Nonnull final String vertexShaderSource,
             @GLSL @Nonnull final String fragmentShaderSource )
    throws ResourceException
  {
    final WebGLProgram program = GL.createProgram( gl, vertexShaderSource, fragmentShaderSource );
    assert null != program;
    _program = program;
    _modelMatrix = new Uniform( gl, program, "modelMatrix" );
    _viewMatrix = new Uniform( gl, program, "viewMatrix" );
    _projectionMatrix = new Uniform( gl, program, "projectionMatrix" );
    _color = new Uniform( gl, program, "color" );

    _geometry = Objects.requireNonNull( geometry );
    _geometry.getAttribute( 0 ).setLocation( GL.getAttribLocation( gl, program, "position" ) );
    _geometry.allocate();
  }

  @Nonnull
  WebGLProgram getProgram()
  {
    return _program;
  }

  @Nonnull
  Uniform getColor()
  {
    return _color;
  }

  @Nonnull
  Uniform getProjectionMatrix()
  {
    return _projectionMatrix;
  }

  @Nonnull
  Uniform getViewMatrix()
  {
    return _viewMatrix;
  }

  @Nonnull
  Uniform getModelMatrix()
  {
    return _modelMatrix;
  }

  @Nonnull
  Geometry getGeometry()
  {
    return _geometry;
  }
}
