package raktek.util;

import org.intellij.lang.annotations.MagicConstant;

@MagicConstant(
  intValues = {
    CompletionStatus.COMPLETE,
    CompletionStatus.INCOMPLETE,
    CompletionStatus.UNKNOWN,
  }
)
public @interface CompletionStatus
{
  int COMPLETE = 1;
  int INCOMPLETE = 2;
  int UNKNOWN = 3;

  final class Validator
  {
    private Validator()
    {
    }

    @CompletionStatus
    public static int cast( final int value )
    {
      assertValid( value );
      return value;
    }

    public static void assertValid( final int value )
    {
      assert isValid( value ) : "@CompletionStatus annotated value must be a known value but is " + value;
    }

    public static boolean isValid( final int value )
    {
      return
        CompletionStatus.COMPLETE == value ||
        CompletionStatus.INCOMPLETE == value ||
        CompletionStatus.UNKNOWN == value;
    }
  }
}
