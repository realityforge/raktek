package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLUniformLocation;
import javax.annotation.Nonnull;

public final class FloatUniform
  extends Uniform
{
  private float _x;

  public FloatUniform( @Nonnull final String name, @Nonnull final WebGLUniformLocation location, final float x )
  {
    super( name, location );
    _x = x;
  }

  public void setX( final float x )
  {
    _x = x;
  }

  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform1f( getLocation(), _x );
  }
}
