package raktek.util;

import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLContextError;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ErrorUtil
{
  private ErrorUtil()
  {
  }

  @Nullable
  public static String describeContextErrors( @Nonnull final WebGL2RenderingContext gl )
  {
    int code;
    String message = null;
    while ( WebGL2RenderingContext.NO_ERROR != ( code = gl.getError() ) )
    {
      final String contextError = describeContextError( code );
      message = null == message ? contextError : message + "\n" + contextError;
    }
    return message;
  }

  @Nonnull
  public static String describeContextError( @WebGLContextError final int value )
  {
    WebGLContextError.Util.assertValid( value );

    if ( WebGL2RenderingContext.NO_ERROR == value )
    {
      return "No error has been recorded.";
    }
    else if ( WebGL2RenderingContext.INVALID_ENUM == value )
    {
      return "An unacceptable value has been specified for an enumerated argument.";
    }
    else if ( WebGL2RenderingContext.INVALID_VALUE == value )
    {
      return "A numeric argument is out of range.";
    }
    else if ( WebGL2RenderingContext.INVALID_OPERATION == value )
    {
      return "The specified command is not allowed for the current state.";
    }
    else if ( WebGL2RenderingContext.INVALID_FRAMEBUFFER_OPERATION == value )
    {
      return "The currently bound framebuffer is not framebuffer complete when trying to render to or to read from it.";
    }
    else if ( WebGL2RenderingContext.OUT_OF_MEMORY == value )
    {
      return "Not enough memory is left to execute the command.";
    }
    else if ( WebGL2RenderingContext.CONTEXT_LOST_WEBGL == value )
    {
      return "The WebGL context has been lost.";
    }
    else
    {
      return "Unknown WebGL error code=" + value;
    }
  }
}
