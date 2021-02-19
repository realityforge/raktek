package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLUniformLocation;
import javax.annotation.Nonnull;

public final class Float2Uniform
  extends Uniform
{
  private float _x;
  private float _y;

  public Float2Uniform( @Nonnull final String name,
                        @Nonnull final WebGLUniformLocation location,
                        final float x,
                        final float y )
  {
    super( name, location );
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

  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform2f( getLocation(), _x, _y );
  }
}
