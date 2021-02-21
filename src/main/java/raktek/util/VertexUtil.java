package raktek.util;

import elemental3.gl.DrawMode;
import elemental3.gl.WebGL2RenderingContext;

public final class VertexUtil
{
  private VertexUtil()
  {
  }

  /**
   * Return the number of vertices required to render the specified number of primitives in the specified draw mode.
   *
   * @param drawMode       the draw mode.
   * @param primitiveCount the number primitives.
   * @return the number of vertices.
   */
  public static int getVertexCount( @DrawMode final int drawMode, final int primitiveCount )
  {
    switch ( drawMode )
    {
      case WebGL2RenderingContext.POINTS:
      case WebGL2RenderingContext.LINE_LOOP:
        return primitiveCount;
      case WebGL2RenderingContext.TRIANGLE_STRIP:
      case WebGL2RenderingContext.TRIANGLE_FAN:
        return 1 == primitiveCount ? 3 : primitiveCount * 2;
      case WebGL2RenderingContext.LINES:
        return primitiveCount * 2;
      case WebGL2RenderingContext.LINE_STRIP:
        return primitiveCount + 1;
      default:
        assert WebGL2RenderingContext.TRIANGLES == drawMode;
        return primitiveCount * 3;
    }
  }

  /**
   * Return the number of primitives for the specified draw mode and vertex count.
   *
   * @param drawMode    the draw mode.
   * @param vertexCount the number of vertices.
   * @return the number primitives.
   */
  public static int getPrimitiveCount( @DrawMode final int drawMode, final int vertexCount )
  {
    switch ( drawMode )
    {
      case WebGL2RenderingContext.POINTS:
      case WebGL2RenderingContext.LINE_LOOP:
        return vertexCount;
      case WebGL2RenderingContext.TRIANGLE_STRIP:
      case WebGL2RenderingContext.TRIANGLE_FAN:
        if ( 3 > vertexCount )
        {
          return 0;
        }
        else if ( 3 == vertexCount )
        {
          return 1;
        }
        else
        {
          return vertexCount / 2;
        }
      case WebGL2RenderingContext.LINES:
        return vertexCount / 2;
      case WebGL2RenderingContext.LINE_STRIP:
        return vertexCount - 1;
      default:
        assert WebGL2RenderingContext.TRIANGLES == drawMode;
        return vertexCount / 3;
    }
  }
}
