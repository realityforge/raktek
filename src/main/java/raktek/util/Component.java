package raktek.util;

import akasha.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * A component in the rendering subsystem.
 */
public abstract class Component
{
  /**
   * GL context to which this resource belongs.
   * Most applications will use a single context and thus this field may be
   * eliminated in the future and the value retrieved from the singleton.
   */
  @Nonnull
  private final WebGL2RenderingContext _gl;

  protected Component( @Nonnull final WebGL2RenderingContext gl )
  {
    _gl = Objects.requireNonNull( gl );
  }

  @Nonnull
  protected final WebGL2RenderingContext gl()
  {
    return _gl;
  }
}
