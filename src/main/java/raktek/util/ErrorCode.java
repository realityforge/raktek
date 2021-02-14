package raktek.util;

import org.intellij.lang.annotations.MagicConstant;

@MagicConstant(
  intValues = {
    ResourceException.BUFFER_CREATE_FAILED,
    ResourceException.SHADER_CREATE_FAILED,
    ResourceException.SHADER_COMPILE_FAILED,
    ResourceException.PROGRAM_CREATE_FAILED,
    ResourceException.PROGRAM_LINK_FAILED
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
      return
        ResourceException.BUFFER_CREATE_FAILED == value ||
        ResourceException.SHADER_CREATE_FAILED == value ||
        ResourceException.SHADER_COMPILE_FAILED == value ||
        ResourceException.PROGRAM_CREATE_FAILED == value ||
        ResourceException.PROGRAM_LINK_FAILED == value;
    }
  }
}
