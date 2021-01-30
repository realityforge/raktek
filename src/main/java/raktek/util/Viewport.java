package raktek.util;

import elemental3.Element;
import elemental3.Global;
import elemental3.HTMLCanvasElement;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.vecmath.Matrix4d;

public abstract class Viewport
  extends Component
{
  /**
   * The camera to clip space transform.
   */
  @Nonnull
  private final Matrix4d _projectionMatrix = new Matrix4d();
  @Nonnull
  private final HTMLCanvasElement _canvas;

  public Viewport( @Nonnull final WebGL2RenderingContext gl, @Nonnull final HTMLCanvasElement canvas )
  {
    super( gl );
    _canvas = Objects.requireNonNull( canvas );
  }

  public final void resize()
  {
    final Element element = Global.document().documentElement();
    assert null != element;

    final int displayWidth = element.clientWidth();
    final int displayHeight = element.clientHeight();

    if ( _canvas.width != displayWidth || _canvas.height != displayHeight )
    {
      // Make the canvas the same size
      _canvas.width = displayWidth;
      _canvas.height = displayHeight;

      // When you need to set the viewport to match the size of the canvas's
      // drawingBuffer this will always be correct. See the reasoning at
      //   https://webgl2fundamentals.org/webgl/lessons/webgl-anti-patterns.html#drawingbuffer
      final WebGL2RenderingContext gl = gl();
      gl.viewport( 0, 0, gl.drawingBufferWidth(), gl.drawingBufferHeight() );

      updateProjection();
    }
  }

  protected abstract void updateProjection();

  @Nonnull
  public HTMLCanvasElement getCanvas()
  {
    return _canvas;
  }

  @Nonnull
  public final Matrix4d getProjectionMatrix()
  {
    return _projectionMatrix;
  }
}
