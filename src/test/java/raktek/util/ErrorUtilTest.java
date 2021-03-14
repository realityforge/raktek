package raktek.util;

import akasha.gl.WebGL2RenderingContext;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public final class ErrorUtilTest
{
  @Test
  public void getContextError()
  {
    final WebGL2RenderingContext gl = mock( WebGL2RenderingContext.class );

    when( gl.getError() ).thenReturn( WebGL2RenderingContext.NO_ERROR,
                                      WebGL2RenderingContext.INVALID_VALUE,
                                      WebGL2RenderingContext.OUT_OF_MEMORY,
                                      WebGL2RenderingContext.CONTEXT_LOST_WEBGL,
                                      WebGL2RenderingContext.NO_ERROR );

    assertNull( ErrorUtil.describeContextErrors( gl ) );
    assertEquals( ErrorUtil.describeContextErrors( gl ),
                  "A numeric argument is out of range.\n" +
                  "Not enough memory is left to execute the command.\n" +
                  "The WebGL context has been lost." );
  }

  @Test
  public void describeContextError()
  {
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.NO_ERROR ),
                  "No error has been recorded." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.INVALID_ENUM ),
                  "An unacceptable value has been specified for an enumerated argument." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.INVALID_OPERATION ),
                  "The specified command is not allowed for the current state." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.INVALID_VALUE ),
                  "A numeric argument is out of range." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.OUT_OF_MEMORY ),
                  "Not enough memory is left to execute the command." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.INVALID_FRAMEBUFFER_OPERATION ),
                  "The currently bound framebuffer is not framebuffer complete when trying to render to or to read from it." );
    assertEquals( ErrorUtil.describeContextError( WebGL2RenderingContext.CONTEXT_LOST_WEBGL ),
                  "The WebGL context has been lost." );

    @SuppressWarnings( { "MagicConstant", "ResultOfMethodCallIgnored" } )
    final AssertionError assertionError =
      expectThrows( AssertionError.class, () -> ErrorUtil.describeContextError( -1 ) );
    assertEquals( assertionError.getMessage(),
                  "@WebGLContextError annotated value must be one of [WebGL2RenderingContext.NO_ERROR, WebGL2RenderingContext.INVALID_ENUM, WebGL2RenderingContext.INVALID_VALUE, WebGL2RenderingContext.INVALID_OPERATION, WebGL2RenderingContext.INVALID_FRAMEBUFFER_OPERATION, WebGL2RenderingContext.OUT_OF_MEMORY, WebGL2RenderingContext.CONTEXT_LOST_WEBGL] but is -1" );
  }
}
