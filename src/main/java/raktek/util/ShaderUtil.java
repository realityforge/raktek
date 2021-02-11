package raktek.util;

import elemental3.Global;
import elemental3.core.RegExp;
import elemental3.core.RegExpResult;
import elemental3.gl.GLSL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ShaderUtil
{
  private ShaderUtil()
  {
  }

  /**
   * Extract a shader's name from defines in its source.
   * The names are specified in the source using defines on a separate line either as plain text
   * (i.e <code>#define SHADER_NAME my shader name</code>) or encoded using base64 for non-ascii
   * characters (i.e. <code>#define SHADER_NAME_B64 aGVsbG8gd29ybGQ=</code>).
   *
   * @param shaderSource the source of the shader.
   * @return the shaders name if any.
   * @see <a href="https://github.com/glslify/glsl-shader-name">glslify/glsl-shader-name which inspired this method</a>
   * @see <a href="https://github.com/spite/ShaderEditorExtension/issues/10">@spite's ShaderEditorExtension</a>
   */
  @Nullable
  public static String extractName( @GLSL @Nonnull final String shaderSource )
  {
    final RegExpResult result = new RegExp( "^\\s*#define\\s+SHADER_NAME(_B64)?\\s+(.+)$", "m" ).exec( shaderSource );
    if ( null == result )
    {
      return null;
    }
    else
    {
      final String nameMatch = result.getAt( 2 );
      assert null != nameMatch;
      final String name = nameMatch.trim();
      final String base64 = result.getAt( 1 );
      return null != base64 ? Global.atob( name ) : name;
    }
  }

  /**
   * Errors returned by getShaderInfoLog are browser and opengl implementation dependent.
   * This method attempts to parse a line returned from getShaderInfoLog with a format that is common
   * in Chromium based browsers and several OpenGL implementations. These errors are formatted like:
   * <pre>
   * ERROR: 0:137: 'blend2' : no matching overloaded function found
   * ERROR: 0:137: '=' : dimension mismatch
   * ERROR: 0:137: '=' : cannot convert from 'const mediump float' to 'highp 3-component vector of float'
   * </pre>
   *
   * @param line a line returned from getShaderInfoLog.
   * @return the parsed line or null if it is not one of the known formats.
   */
  @Nullable
  public static ShaderInfoMessage parseShaderErrorLine( @Nonnull final String line )
  {
    //ERROR: 0:137: 'blend2' : no matching overloaded function found
    final RegExpResult result = new RegExp( "^(ERROR|WARNING):\\s*\\d+:(\\d+): (.+)$" ).exec( line );
    if ( null == result )
    {
      return null;
    }
    else
    {
      try
      {
        final boolean error = "ERROR".equals( result.getAt( 1 ) );
        final String lineNoText = result.getAt( 2 );
        assert null != lineNoText;
        final int lineNumber = Integer.parseInt( lineNoText );
        final String message = result.getAt( 3 );
        assert null != message;
        return new ShaderInfoMessage( error, lineNumber, message );
      }
      catch ( final NumberFormatException nfe )
      {
        return null;
      }
    }
  }
}
