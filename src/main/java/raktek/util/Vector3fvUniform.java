package raktek.util;

import elemental3.core.Float32Array;
import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class Vector3fvUniform
  extends Float32ArrayUniform
{
  public Vector3fvUniform( @Nonnull final UniformDescriptor uniform, @Nonnull final Float32Array value )
  {
    super( uniform, value );
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform3fv( getUniform().getLocation(), getValues() );
  }
}
