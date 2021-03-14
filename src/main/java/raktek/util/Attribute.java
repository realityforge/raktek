package raktek.util;

import akasha.core.ArrayBufferView;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class Attribute
{
  @Nonnull
  private final String _name;
  /**
   * The data and layout of data.
   */
  @Nonnull
  private final Buffer _buffer;
  /**
   * The description of how to access the buffer to retrieve the vertex data.
   */
  @Nonnull
  private final Accessor _accessor;

  /**
   * Return the number of vertices using the the specified attributes,
   * assuming that every attribute makes use of the entire buffer.
   *
   * @param attributes the attributes.
   * @return the number of vertices using the the specified attributes.
   */
  public static int getVertexCount( @Nonnull final Attribute... attributes )
  {
    assert 0 != attributes.length;
    int count = Integer.MAX_VALUE;
    for ( final Attribute attribute : attributes )
    {
      count = Math.min( count, attribute.getVertexCount() );
    }
    return count;
  }

  public Attribute( @Nonnull final String name, @Nonnull final Buffer buffer, @Nonnull final Accessor accessor )
  {
    _name = Objects.requireNonNull( name );
    _buffer = Objects.requireNonNull( buffer );
    _accessor = Objects.requireNonNull( accessor );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @Nonnull
  public Buffer getBuffer()
  {
    return _buffer;
  }

  @Nonnull
  public Accessor getAccessor()
  {
    return _accessor;
  }

  /**
   * Return the number of vertices present in the attribute assuming that the full buffer is used.
   *
   * @return the number of vertices present in the attribute assuming that the full buffer is used.
   */
  public int getVertexCount()
  {
    final ArrayBufferView data = getBuffer().getData();
    final int dataLength = data.byteLength() - data.byteOffset();
    return dataLength / getAccessor().getBytesPerVertex();
  }
}
