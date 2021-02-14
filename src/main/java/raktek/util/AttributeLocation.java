package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Class containing a mapping between an attribute name and an attribute location.
 */
public final class AttributeLocation
{
  @Nonnull
  private final String _name;
  private final int _index;

  public AttributeLocation( @Nonnull final String name, final int index )
  {
    _name = Objects.requireNonNull( name );
    _index = index;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  public int getIndex()
  {
    return _index;
  }
}
