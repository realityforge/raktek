package raktek.util;

import javax.annotation.Nonnull;

public final class StringUtil
{
  private StringUtil()
  {
  }

  @Nonnull
  public static String padRight( @Nonnull final String string, final int digits )
  {
    final StringBuilder sb = new StringBuilder();
    padRight( sb, string, digits );
    return sb.toString();
  }

  public static void padRight( @Nonnull final StringBuilder sb, @Nonnull final String string, final int digits )
  {
    sb.append( string );
    for ( int i = 0; i < digits; ++i )
    {
      sb.append( ' ' );
    }
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
