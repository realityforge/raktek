package raktek.util;

import org.intellij.lang.annotations.MagicConstant;

@MagicConstant(
  intValues = {
    ResourceException.SHADER_CREATE_FAILED
  }
)
public @interface ErrorCode
{
  final class Validator
  {
    private Validator()
    {
    }

    @ErrorCode
    public static int cast( final int value )
    {
      assertValid( value );
      return value;
    }

    public static void assertValid( final int value )
    {
      assert isValid( value ) : "@ErrorCode annotated value must be a known value but is " + value;
    }

    public static boolean isValid( final int value )
    {
      return ResourceException.SHADER_CREATE_FAILED == value;
    }
  }
}
