package raktek;

import elemental3.gl.WebGLUniformLocation;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.util.Geometry;
import raktek.util.Program;
import raktek.util.ProgramDescriptor;
import raktek.util.ResourceException;

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

  LightMesh( @Nonnull final Program program, @Nonnull final Geometry geometry )
    throws ResourceException
  {
    _program = Objects.requireNonNull( program );
    _program.allocate();
    final ProgramDescriptor descriptor = _program.getDescriptor();
    _modelMatrix = descriptor.getUniformByName( "modelMatrix" ).getLocation();
    _viewMatrix = descriptor.getUniformByName( "viewMatrix" ).getLocation();
    _projectionMatrix = descriptor.getUniformByName( "projectionMatrix" ).getLocation();
    _color = descriptor.getUniformByName( "color" ).getLocation();
    _geometry = Objects.requireNonNull( geometry );
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
