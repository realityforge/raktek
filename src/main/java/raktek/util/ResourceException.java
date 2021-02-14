package raktek.util;

import elemental3.gl.WebGLContextError;
import javax.annotation.Nullable;

public class ResourceException
  extends RuntimeException
{
  @ErrorCode
  private final int _code;
  @WebGLContextError
  private final int _contextError;

  public ResourceException( @ErrorCode final int code, @WebGLContextError final int contextError )
  {
    this( code, contextError, null, null );
  }

  public ResourceException( @ErrorCode final int code,
                            @WebGLContextError final int contextError,
                            @Nullable final String message )
  {
    this( code, contextError, message, null );
  }

  public ResourceException( @ErrorCode final int code,
                            @WebGLContextError final int contextError,
                            @Nullable final Throwable cause )
  {
    this( code, contextError, null, cause );
  }

  public ResourceException( @ErrorCode final int code,
                            @WebGLContextError final int contextError,
                            @Nullable final String message,
                            @Nullable final Throwable cause )
  {
    super( message, cause );
    ErrorCode.Validator.assertValid( code );
    WebGLContextError.Validator.assertValid( contextError );
    _code = code;
    _contextError = contextError;
  }

  @ErrorCode
  public int getCode()
  {
    return _code;
  }

  @WebGLContextError
  public int getContextError()
  {
    return _contextError;
  }
}
