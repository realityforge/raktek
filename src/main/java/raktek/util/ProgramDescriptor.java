package raktek.util;

import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class ProgramDescriptor
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final AttributeDescriptor[] _attributes;

  public ProgramDescriptor( @Nonnull final String name, @Nonnull final AttributeDescriptor[] attributes )
  {
    _name = Objects.requireNonNull( name );
    _attributes = Objects.requireNonNull( attributes );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  public int getAttributeCount()
  {
    return _attributes.length;
  }

  @Nonnull
  public AttributeDescriptor getAttribute( final int index )
  {
    return _attributes[ index ];
  }

  @Override
  public String toString()
  {
    if ( Raktek.shouldSupportDebugToString() )
    {
      return "ProgramDescriptor[" + getName() +
             ",attributes=" + Arrays.asList( _attributes ) +
             "]";
    }
    else
    {
      return super.toString();
    }
  }
}
