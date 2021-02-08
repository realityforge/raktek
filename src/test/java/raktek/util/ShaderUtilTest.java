package raktek.util;

import elemental3.gl.GLSL;
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
}
