package raktek.util;

import elemental3.gl.WebGLUniformLocation;
import java.util.Objects;
import javax.annotation.Nonnull;

public class Uniform
{
  /**
   * The name of the shader variable.
   */
  @Nonnull
  private final String _name;
  @Nonnull
  private final WebGLUniformLocation _location;

  public Uniform( @Nonnull final String name, @Nonnull final WebGLUniformLocation location )
  {
    _name = Objects.requireNonNull( name );
    _location = Objects.requireNonNull( location );
  }

  @Nonnull
  public final String getName()
  {
    return _name;
  }

  @Nonnull
  public final WebGLUniformLocation getLocation()
  {
    return _location;
  }
}
