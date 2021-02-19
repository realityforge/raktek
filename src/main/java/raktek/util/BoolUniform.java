package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLUniformLocation;
import javax.annotation.Nonnull;

public final class BoolUniform
  extends Uniform
{
  private boolean _value;

  public BoolUniform( @Nonnull final String name,
                      @Nonnull final WebGLUniformLocation location,
                      final boolean value )
  {
    super( name, location );
    _value = value;
  }

  public void setValue( final boolean value )
  {
    _value = value;
  }

  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform1f( getLocation(), _value ? 1 : 0 );
  }
}
