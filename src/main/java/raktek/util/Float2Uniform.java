package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class Float2Uniform
  extends Uniform
{
  private float _x;
  private float _y;

  public Float2Uniform( @Nonnull final UniformDescriptor uniform,
                        final float x,
                        final float y )
  {
    super( uniform );
    _x = x;
    _y = y;
  }

  public void setX( final float x )
  {
    _x = x;
  }

  public void setY( final float y )
  {
    _y = y;
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform2f( getUniform().getLocation(), _x, _y );
  }
}
