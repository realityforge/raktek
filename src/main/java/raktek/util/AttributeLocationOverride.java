package raktek.util;

import elemental3.gl.AttributeIndex;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Class containing a mapping between an attribute name and an attribute location.
 * This is passed into Attribute
 */
public final class AttributeLocationOverride
{
  @Nonnull
  private final String _name;
  @AttributeIndex
  private final int _index;

  public AttributeLocationOverride( @Nonnull final String name, @AttributeIndex final int index )
  {
    assert WebGL2RenderingContext.INVALID_INDEX != index;
    _name = Objects.requireNonNull( name );
    _index = index;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @AttributeIndex
  public int getIndex()
  {
    return _index;
  }
}
