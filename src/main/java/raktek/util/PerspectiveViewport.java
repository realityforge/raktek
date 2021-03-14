package raktek.util;

import akasha.HTMLCanvasElement;
import akasha.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public final class PerspectiveViewport
  extends Viewport
{
  /**
   * The vertical field of view in radians (must be greater than zero and less than {@link Math#PI PI}).
   */
  private double _fovY;
  /**
   * The near clipping plane distance.
   */
  private double _zNear;
  /**
   * the far clipping plane distance.
   */
  private double _zFar;

  public PerspectiveViewport( @Nonnull final WebGL2RenderingContext gl, @Nonnull final HTMLCanvasElement canvas )
  {
    super( gl, canvas );
  }

  public double getFovY()
  {
    return _fovY;
  }

  public void setFovY( final double fovY )
  {
    _fovY = fovY;
  }

  public double getZNear()
  {
    return _zNear;
  }

  public void setZNear( final double zNear )
  {
    _zNear = zNear;
  }

  public double getZFar()
  {
    return _zFar;
  }

  public void setZFar( final double zFar )
  {
    _zFar = zFar;
  }

  protected void updateProjection()
  {
    getProjectionMatrix().setPerspective( _fovY, CanvasUtil.getAspect( getCanvas() ), _zNear, _zFar );
  }
}
