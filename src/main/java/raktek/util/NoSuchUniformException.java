package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class NoSuchUniformException
  extends RuntimeException
{
  @Nonnull
  private final String _name;

  public NoSuchUniformException( @Nonnull final String name )
  {
    _name = Objects.requireNonNull( name );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }
}
