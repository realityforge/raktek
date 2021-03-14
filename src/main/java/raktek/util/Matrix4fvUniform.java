package raktek.util;

import akasha.core.Float32Array;
import akasha.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class Matrix4fvUniform
  extends Float32ArrayUniform
{
  public Matrix4fvUniform( @Nonnull final UniformDescriptor uniform, @Nonnull final Float32Array value )
  {
    super( uniform, value );
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniformMatrix4fv( getUniform().getLocation(), false, getValues() );
  }
}
