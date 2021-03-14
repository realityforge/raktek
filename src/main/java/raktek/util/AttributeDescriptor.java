package raktek.util;

import akasha.gl.AttributeDataType;
import akasha.gl.AttributeIndex;
import akasha.gl.WebGL2RenderingContext;
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
    assert WebGL2RenderingContext.INVALID_INDEX != index;
    _name = Objects.requireNonNull( name );
    _type = AttributeDataType.Util.requireValid( type );
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
    return Raktek.shouldSupportDebugToString() ? toDebugString() : super.toString();
  }

  @Nonnull
  public String toDebugString()
  {
    return "Attribute[" + getName() +
           ",type=" + AttributeDataType.Util.describe( getType() ) +
           ",index=" + getIndex() +
           "]";
  }
}
