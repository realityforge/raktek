package raktek.util;

import elemental3.core.ArrayBufferView;
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
