package raktek.util;

import javax.annotation.Nonnull;

public final class StringUtil
{
  private StringUtil()
  {
  }

  @Nonnull
  public static String padLeft( @Nonnull final String string, final int digits )
  {
    final StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < digits; ++i )
    {
      sb.append( ' ' );
    }
    sb.append( string );
    return sb.toString();
  }
}
