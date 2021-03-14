package raktek.util;

import akasha.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class BoolUniform
  extends Uniform
{
  private boolean _value;

  public BoolUniform( @Nonnull final UniformDescriptor uniform, final boolean value )
  {
    super( uniform );
    _value = value;
  }

  public void setValue( final boolean value )
  {
    _value = value;
  }

  @Override
  public void sendToGpu( @Nonnull final WebGL2RenderingContext gl )
  {
    gl.uniform1f( getUniform().getLocation(), _value ? 1 : 0 );
  }
}
