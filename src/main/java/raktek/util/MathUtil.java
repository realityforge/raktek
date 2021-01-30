package raktek.util;

public final class MathUtil
{
  private MathUtil()
  {
  }

  public static double degreesToRadians( final double degrees )
  {
    return degrees * Math.PI / 180.0;
  }
}
