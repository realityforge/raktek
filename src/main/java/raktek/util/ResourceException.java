package raktek.util;

import javax.annotation.Nullable;

public class ResourceException
  extends Exception
{
  public static final int SHADER_CREATE_FAILED = 1;
  @ErrorCode
  private final int _code;

  public ResourceException( @ErrorCode final int code )
  {
    ErrorCode.Validator.assertValid( code );
    _code = code;
  }

  public ResourceException( @Nullable final String message, @ErrorCode final int code )
  {
    super( message );
    ErrorCode.Validator.assertValid( code );
    _code = code;
  }

  public ResourceException( @Nullable final Throwable cause, @ErrorCode final int code )
  {
    super( cause );
    ErrorCode.Validator.assertValid( code );
    _code = code;
  }

  public ResourceException( @Nullable final String message, @Nullable final Throwable cause, @ErrorCode final int code )
  {
    super( message, cause );
    ErrorCode.Validator.assertValid( code );
    _code = code;
  }

  @ErrorCode
  public int getCode()
  {
    return _code;
  }

}
