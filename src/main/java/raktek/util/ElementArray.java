package raktek.util;

import elemental3.core.ArrayBufferView;
import elemental3.gl.DrawElementsDataType;
import elemental3.gl.UsageType;
import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class ElementArray
{
  @Nonnull
  private final Buffer _buffer;
  @DrawElementsDataType
  private final int _type;

  public ElementArray( @Nonnull final WebGL2RenderingContext gl,
                       @Nonnull final ArrayBufferView data,
                       @DrawElementsDataType final int type )
  {
    this( gl, data, WebGL2RenderingContext.STATIC_DRAW, type );
  }

  public ElementArray( @Nonnull final WebGL2RenderingContext gl,
                       @Nonnull final ArrayBufferView data,
                       @UsageType final int usage,
                       @DrawElementsDataType final int type )
  {
    _buffer = new Buffer( gl, data, usage, WebGL2RenderingContext.ELEMENT_ARRAY_BUFFER );
    _type = DrawElementsDataType.Util.requireValid( type );
  }

  @Nonnull
  public Buffer getBuffer()
  {
    return _buffer;
  }

  @DrawElementsDataType
  public int getType()
  {
    return _type;
  }
}
