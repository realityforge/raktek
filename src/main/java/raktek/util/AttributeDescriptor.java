package raktek.util;

import elemental3.gl.AttributeDataType;
import elemental3.gl.AttributeIndex;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class AttributeDescriptor
{
  @Nonnull
  private final String _name;
  @AttributeDataType
  private final int _type;
  @AttributeIndex
  private final int _index;

  public AttributeDescriptor( @Nonnull final String name,
                              @AttributeDataType final int type,
                              @AttributeIndex final int index )
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

  @AttributeIndex
  public int getIndex()
  {
    return _index;
  }

  @Override
  public String toString()
  {
    if ( Raktek.shouldSupportDebugToString() )
    {
      return "Attribute[" + getName() +
             ",type=" + AttributeDataType.Validator.describe( getType() ) +
             ",index=" + getIndex() +
             "]";
    }
    else
    {
      return super.toString();
    }
  }
}
