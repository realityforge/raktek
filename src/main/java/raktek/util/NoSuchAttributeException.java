package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class NoSuchAttributeException
  extends RuntimeException
{
  @Nonnull
  private final String _name;

  public NoSuchAttributeException( @Nonnull final String name )
  {
    _name = Objects.requireNonNull( name );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }
}
