package raktek.util;

import elemental3.gl.UniformDataType;
import elemental3.gl.WebGLUniformLocation;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class UniformDescriptor
{
  @Nonnull
  private final String _name;
  @UniformDataType
  private final int _type;
  @Nonnull
  private final WebGLUniformLocation _location;

  public UniformDescriptor( @Nonnull final String name,
                            @UniformDataType final int type,
                            @Nonnull final WebGLUniformLocation location )
  {
    UniformDataType.Validator.assertValid( type );
    _name = Objects.requireNonNull( name );
    _type = type;
    _location = Objects.requireNonNull( location );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @UniformDataType
  public int getType()
  {
    return _type;
  }

  @Nonnull
  public WebGLUniformLocation getLocation()
  {
    return _location;
  }

  @Override
  public String toString()
  {
    return Raktek.shouldSupportDebugToString() ? toDebugString() : super.toString();
  }

  @Nonnull
  public String toDebugString()
  {
    return "Uniform[" + getName() +
           ",type=" + UniformDataType.Validator.describe( getType() ) +
           "]";
  }
}
