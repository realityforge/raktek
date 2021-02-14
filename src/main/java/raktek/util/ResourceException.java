package raktek.util;

import elemental3.gl.WebGLContextError;
import javax.annotation.Nullable;

public class ResourceException
  extends RuntimeException
{
  public static final int SHADER_CREATE_FAILED = 1;
  public static final int SHADER_COMPILE_FAILED = 2;
  public static final int BUFFER_CREATE_FAILED = 3;
  public static final int PROGRAM_CREATE_FAILED = 4;
  public static final int PROGRAM_LINK_FAILED = 5;
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
