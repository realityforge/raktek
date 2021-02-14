package raktek;

import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLProgram;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.util.GL;
import raktek.util.Geometry;
import raktek.util.Program;
import raktek.util.ResourceException;
import raktek.util.Shader;
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
    final Program program =
      new Program( gl,
                   "LightMesh",
                   new Shader( gl, null, WebGL2RenderingContext.VERTEX_SHADER, vertexShaderSource ),
                   new Shader( gl, null, WebGL2RenderingContext.FRAGMENT_SHADER, fragmentShaderSource ) );
    program.allocate();
    _program = program.getWebGLProgram();
    _modelMatrix = new Uniform( gl, _program, "modelMatrix" );
    _viewMatrix = new Uniform( gl, _program, "viewMatrix" );
    _projectionMatrix = new Uniform( gl, _program, "projectionMatrix" );
    _color = new Uniform( gl, _program, "color" );
    _geometry = Objects.requireNonNull( geometry );
    _geometry.getAttribute( 0 ).setLocation( GL.getAttribLocation( gl, _program, "position" ) );
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
