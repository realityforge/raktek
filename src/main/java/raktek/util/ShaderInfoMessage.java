package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class ShaderInfoMessage
{
  /**
   * True if message is an error.
   */
  private final boolean _error;
  /**
   * The line on which the error occurred.
   */
  private final int _line;
  /**
   * The message.
   */
  @Nonnull
  private final String _message;

  public ShaderInfoMessage( final boolean error, final int line, @Nonnull final String message )
  {
    _error = error;
    _line = line;
    _message = Objects.requireNonNull( message );
  }

  public boolean isError()
  {
    return _error;
  }

  public int getLine()
  {
    return _line;
  }

  @Nonnull
  public String getMessage()
  {
    return _message;
  }
}
