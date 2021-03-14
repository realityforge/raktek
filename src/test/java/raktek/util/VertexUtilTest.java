package raktek.util;

import akasha.gl.DrawMode;
import akasha.gl.WebGL2RenderingContext;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class VertexUtilTest
{
  @Test
  public void getVertexCount()
  {
    assertVertexCount( WebGL2RenderingContext.TRIANGLE_STRIP, 1, 3 );
    assertVertexCount( WebGL2RenderingContext.TRIANGLE_STRIP, 8, 16 );
    assertVertexCount( WebGL2RenderingContext.TRIANGLE_FAN, 1, 3 );
    assertVertexCount( WebGL2RenderingContext.TRIANGLE_FAN, 12, 24 );
    assertVertexCount( WebGL2RenderingContext.LINES, 6, 12 );
    assertVertexCount( WebGL2RenderingContext.TRIANGLES, 1, 3 );
    assertVertexCount( WebGL2RenderingContext.TRIANGLES, 7, 21 );
    assertVertexCount( WebGL2RenderingContext.LINE_STRIP, 21, 22 );
    assertVertexCount( WebGL2RenderingContext.POINTS, 22, 22 );
    assertVertexCount( WebGL2RenderingContext.LINE_LOOP, 24, 24 );
  }

  private void assertVertexCount( @DrawMode final int drawMode, final int primitiveCount, final int vertexCount )
  {
    final int actualVertexCount = VertexUtil.getVertexCount( drawMode, primitiveCount );
    assertEquals( actualVertexCount, vertexCount );
    assertEquals( VertexUtil.getVertexCount( drawMode, VertexUtil.getPrimitiveCount( drawMode, actualVertexCount ) ),
                  vertexCount );
  }

  @Test
  public void getPrimitiveCount()
  {
    assertPrimitiveCount( WebGL2RenderingContext.TRIANGLE_STRIP, 2, 0 );
    assertPrimitiveCount( WebGL2RenderingContext.TRIANGLE_STRIP, 16, 8 );
    assertPrimitiveCount( WebGL2RenderingContext.TRIANGLE_FAN, 25, 12 );
    assertPrimitiveCount( WebGL2RenderingContext.LINES, 12, 6 );
    assertPrimitiveCount( WebGL2RenderingContext.TRIANGLES, 2, 0 );
    assertPrimitiveCount( WebGL2RenderingContext.TRIANGLES, 22, 7 );
    assertPrimitiveCount( WebGL2RenderingContext.LINE_STRIP, 22, 21 );
    assertPrimitiveCount( WebGL2RenderingContext.POINTS, 22, 22 );
    assertPrimitiveCount( WebGL2RenderingContext.LINE_LOOP, 22, 22 );
  }

  private void assertPrimitiveCount( @DrawMode final int drawMode, final int vertexCount, final int primitiveCount )
  {
    final int actualPrimitiveCount = VertexUtil.getPrimitiveCount( drawMode, vertexCount );
    assertEquals( actualPrimitiveCount, primitiveCount );
    assertEquals( VertexUtil.getPrimitiveCount( drawMode, VertexUtil.getVertexCount( drawMode, actualPrimitiveCount ) ),
                  primitiveCount );
  }
}
