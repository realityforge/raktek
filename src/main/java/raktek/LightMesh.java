package raktek;

import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLUniformLocation;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.util.Geometry;
import raktek.util.Program;
import raktek.util.ProgramDescriptor;
import raktek.util.ResourceException;
import raktek.util.Shader;

final class LightMesh
{
  @Nonnull
  private final Program _program;
  @Nonnull
  private final WebGLUniformLocation _modelMatrix;
  @Nonnull
  private final WebGLUniformLocation _viewMatrix;
  @Nonnull
  private final WebGLUniformLocation _projectionMatrix;
  @Nonnull
  private final WebGLUniformLocation _color;
  @Nonnull
  private final Geometry _geometry;

  LightMesh( @Nonnull final WebGL2RenderingContext gl,
             @Nonnull final Geometry geometry,
             @GLSL @Nonnull final String vertexShaderSource,
             @GLSL @Nonnull final String fragmentShaderSource )
    throws ResourceException
  {
    _program =
      new Program( gl,
                   "LightMesh",
                   new Shader( gl, null, WebGL2RenderingContext.VERTEX_SHADER, vertexShaderSource ),
                   new Shader( gl, null, WebGL2RenderingContext.FRAGMENT_SHADER, fragmentShaderSource ) );
    _program.allocate();
    final ProgramDescriptor descriptor = _program.getDescriptor();
    _modelMatrix = descriptor.getUniformByName( "modelMatrix" ).getLocation();
    _viewMatrix = descriptor.getUniformByName( "viewMatrix" ).getLocation();
    _projectionMatrix = descriptor.getUniformByName( "projectionMatrix" ).getLocation();
    _color = descriptor.getUniformByName( "color" ).getLocation();
    _geometry = Objects.requireNonNull( geometry );
    _geometry.getAttribute( 0 ).setLocation( descriptor.getAttributeByName( "position" ).getIndex() );
    _geometry.allocate();
  }

  @Nonnull
  Program getProgram()
  {
    return _program;
  }

  @Nonnull
  WebGLUniformLocation getColor()
  {
    return _color;
  }

  @Nonnull
  WebGLUniformLocation getProjectionMatrix()
  {
    return _projectionMatrix;
  }

  @Nonnull
  WebGLUniformLocation getViewMatrix()
  {
    return _viewMatrix;
  }

  @Nonnull
  WebGLUniformLocation getModelMatrix()
  {
    return _modelMatrix;
  }

  @Nonnull
  Geometry getGeometry()
  {
    return _geometry;
  }
}
