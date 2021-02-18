package raktek.util;

import elemental3.gl.AttributeComponentDataType;
import elemental3.gl.AttributeIndex;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class AttributeDescriptor
{
  @Nonnull
  private final String _name;
  @AttributeComponentDataType
  private final int _type;
  @AttributeIndex
  private final int _index;

  public AttributeDescriptor( @Nonnull final String name,
                              @AttributeComponentDataType final int type,
                              @AttributeIndex final int index )
  {
    AttributeComponentDataType.Validator.assertValid( type );
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

  @AttributeComponentDataType
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
             ",type=" + AttributeComponentDataType.Validator.describe( getType() ) +
             ",index=" + getIndex() +
             "]";
    }
    else
    {
      return super.toString();
    }
  }
}
