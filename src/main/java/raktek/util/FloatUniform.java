package raktek.util;

import akasha.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class FloatUniform
  extends Uniform
{
  private float _x;

  public FloatUniform( @Nonnull final UniformDescriptor uniform, final float x )
  {
    super( uniform );
    _x = x;
  }

  public void setX( final float x )
  {
    _x = x;
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform1f( getUniform().getLocation(), _x );
  }
}
