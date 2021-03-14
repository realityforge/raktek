package raktek.util;

import akasha.core.Float32Array;
import akasha.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class VecfUniform
  extends Uniform
{
  @Nonnull
  private Float32Array _values;

  public VecfUniform( @Nonnull final UniformDescriptor uniform, @Nonnull final Float32Array values )
  {
    super( uniform );
    _values = Objects.requireNonNull( values );
  }

  public void setValues( @Nonnull final Float32Array values )
  {
    _values = Objects.requireNonNull( values );
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform1fv( getUniform().getLocation(), _values );
  }
}
