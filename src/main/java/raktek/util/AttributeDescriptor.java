package raktek.util;

import elemental3.gl.AttributeDataType;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class AttributeDescriptor
{
  @Nonnull
  private final String _name;
  @AttributeDataType
  private final int _type;
  private final int _index;

  public AttributeDescriptor( @Nonnull final String name, @AttributeDataType final int type, final int index )
  {
    AttributeDataType.Validator.assertValid( type );
    assert WebGL2RenderingContext.INVALID_INDEX != index;
    _name = Objects.requireNonNull( name );
    _type = type;
    _index = index;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @AttributeDataType
  public int getType()
  {
    return _type;
  }

  public int getIndex()
  {
    return _index;
  }
}
