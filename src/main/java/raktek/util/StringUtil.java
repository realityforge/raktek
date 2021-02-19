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
    padLeft( sb, string, digits );
    return sb.toString();
  }

  public static void padLeft( @Nonnull final StringBuilder sb, @Nonnull final String string, final int digits )
  {
    for ( int i = 0; i < digits; ++i )
    {
      sb.append( ' ' );
    }
    sb.append( string );
  }
}
