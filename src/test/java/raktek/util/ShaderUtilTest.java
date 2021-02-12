package raktek.util;

import elemental3.gl.GLSL;
import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ShaderUtilTest
{
  // Test disabled as no easy way to run tests in javascript context atm
  @Test( enabled = false )
  public void extractName()
  {
    {
      @GLSL
      @Nonnull
      final String shader =
        "#version 300 es\n" +
        "in vec3 position;\n" +
        "uniform mat4 modelMatrix;\n" +
        "uniform mat4 viewMatrix;\n" +
        "uniform mat4 projectionMatrix;\n" +
        "void main()\n" +
        "{\n" +
        "  gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1);" +
        "}\n";
      assertNull( ShaderUtil.extractName( shader ) );
    }

    {
      @GLSL
      @Nonnull
      final String shader =
        "#version 300 es\n" +
        "#define SHADER_NAME pass thru shader\n" +
        "in vec3 position;\n" +
        "void main() { gl_Position = vec4(position, 1);} ";
      assertEquals( ShaderUtil.extractName( shader ), "pass thru shader" );
    }
    {
      @GLSL
      @Nonnull
      final String shader =
        "#version 300 es\n" +
        "  #define     SHADER_NAME     The shader of Magic!     \n" +
        "in vec3 position;\n" +
        "void main() { gl_Position = vec4(position, 1);} ";
      assertEquals( ShaderUtil.extractName( shader ), "The shader of Magic!" );
    }
    {
      @GLSL
      @Nonnull
      final String shader =
        "#version 300 es\n" +
        " #define SHADER_NAME_B64 aGVsbG8gd29ybGQ=\n" +
        "in vec3 position;\n" +
        "void main() { gl_Position = vec4(position, 1);} ";
      assertEquals( ShaderUtil.extractName( shader ), "XXX" );
    }
  }

  // Test disabled as no easy way to run tests in javascript context atm
  @Test( enabled = false )
  public void parseShaderErrorLine()
  {
    {
      final ShaderInfoMessage message =
        ShaderUtil.parseShaderErrorLine( "ERROR: 0:137: 'blend2' : no matching overloaded function found" );
      assertNotNull( message );
      assertTrue( message.isError() );
      assertEquals( message.getLine(), 137 );
      assertEquals( message.getMessage(), "'blend2' : no matching overloaded function found" );
    }
    {
      final ShaderInfoMessage message = ShaderUtil.parseShaderErrorLine( "WARNING: 0:12: '/' : Zero divided by zero" );
      assertNotNull( message );
      assertFalse( message.isError() );
      assertEquals( message.getLine(), 12 );
      assertEquals( message.getMessage(), "'/' : Zero divided by zero" );
    }
    assertNull( ShaderUtil.parseShaderErrorLine( "INFO: 0:1: '/' : Zero divided by zero" ) );
    assertNull( ShaderUtil.parseShaderErrorLine( "Some random string" ) );
  }

  // Test disabled as no easy way to run tests in javascript context atm
  @Test( enabled = false )
  public void formatInfoLog()
  {
    final String message =
      ShaderUtil.formatInfoLog( "ERROR: 0:8: 'finXalColor' : undeclared identifier\n" +
                                "ERROR: 0:8: 'assign' : l-value required (can't modify a const)\n" +
                                "ERROR: 0:8: '=' : dimension mismatch\n" +
                                "ERROR: 0:8: 'assign' : cannot convert from 'lowp 4-component vector of float' to 'const highp float'",
                                "Test",
                                WebGL2RenderingContext.FRAGMENT_SHADER,
                                "#version 300 es\n" +
                                "#define SHADER_NAME_B64 aGVsbG8gd29ybGQ=\n" +
                                "precision lowp float;\n" +
                                "uniform vec3 color;\n" +
                                "out vec4 finalColor;\n" +
                                "void main()\n" +
                                "{\n" +
                                "  finXalColor = vec4(color,1);" +
                                "}\n" );
    assertEquals( message,
                  "GLSL compilation error in FRAGMENT_SHADER shader Test:\n" +
                  "\n" +
                  "    4: out vec4 finalColor;\n" +
                  "    5: void main()\n" +
                  "    6: {\n" +
                  "    7:   finXalColor = vec4(color,1);}\n" +
                  " ^^^ ERROR 'finXalColor' : undeclared identifier\n" +
                  "\n" +
                  "    4: out vec4 finalColor;\n" +
                  "    5: void main()\n" +
                  "    6: {\n" +
                  "    7:   finXalColor = vec4(color,1);}\n" +
                  " ^^^ ERROR 'assign' : l-value required (can't modify a const)\n" +
                  "\n" +
                  "    4: out vec4 finalColor;\n" +
                  "    5: void main()\n" +
                  "    6: {\n" +
                  "    7:   finXalColor = vec4(color,1);}\n" +
                  " ^^^ ERROR '=' : dimension mismatch\n" +
                  "\n" +
                  "    4: out vec4 finalColor;\n" +
                  "    5: void main()\n" +
                  "    6: {\n" +
                  "    7:   finXalColor = vec4(color,1);}\n" +
                  " ^^^ ERROR 'assign' : cannot convert from 'lowp 4-component vector of float' to 'const highp float'\n" );
  }
}
