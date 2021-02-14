package raktek.util;

import org.intellij.lang.annotations.MagicConstant;

@MagicConstant(
  intValues = {
    ErrorCode.BUFFER_CREATE_FAILED,
    ErrorCode.SHADER_CREATE_FAILED,
    ErrorCode.SHADER_COMPILE_FAILED,
    ErrorCode.PROGRAM_CREATE_FAILED,
    ErrorCode.PROGRAM_LINK_FAILED
  }
)
public @interface ErrorCode
{
  int SHADER_CREATE_FAILED = 1;
  int SHADER_COMPILE_FAILED = 2;
  int BUFFER_CREATE_FAILED = 3;
  int PROGRAM_CREATE_FAILED = 4;
  int PROGRAM_LINK_FAILED = 5;

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
      return
        BUFFER_CREATE_FAILED == value ||
        SHADER_CREATE_FAILED == value ||
        SHADER_COMPILE_FAILED == value ||
        PROGRAM_CREATE_FAILED == value ||
        PROGRAM_LINK_FAILED == value;
    }
  }
}
