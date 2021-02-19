package raktek.util;

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
}
