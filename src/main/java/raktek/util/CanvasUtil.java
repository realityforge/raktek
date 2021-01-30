package raktek.util;

import elemental3.Document;
import elemental3.Global;
import elemental3.HTMLCanvasElement;
import elemental3.HTMLElement;
import elemental3.RenderContextType;
import elemental3.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class CanvasUtil
{
  private CanvasUtil()
  {
  }

  @Nonnull
  public static HTMLCanvasElement createCanvas()
  {
    final Document document = Global.document();
    final HTMLCanvasElement canvas = (HTMLCanvasElement) document.createElement( "canvas" );
    final HTMLElement body = document.body;
    assert null != body;
    body.appendChild( canvas );
    return canvas;
  }

  @Nonnull
  public static WebGL2RenderingContext getWebGL2RenderingContext( @Nonnull final HTMLCanvasElement canvas )
  {
    final WebGL2RenderingContext gl = (WebGL2RenderingContext) canvas.getContext( RenderContextType.webgl2 );
    assert null != gl;
    return gl;
  }

  public static double getAspect( @Nonnull final HTMLCanvasElement canvas )
  {
    // Don't use canvas.width and canvas.height as the actual size may be controlled by css
    // See https://webgl2fundamentals.org/webgl/lessons/webgl-anti-patterns.html for more understanding
    return canvas.clientWidth() / ( (double) canvas.clientHeight() );
  }
}
