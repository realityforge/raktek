package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

public abstract class Uniform
{
  @Nonnull
  private final UniformDescriptor _uniform;

  protected Uniform( @Nonnull final UniformDescriptor uniform )
  {
    _uniform = Objects.requireNonNull( uniform );
  }

  public abstract void sendToGpu( @Nonnull WebGL2RenderingContext gl );

  @Nonnull
  protected UniformDescriptor getUniform()
  {
    return _uniform;
  }
}
