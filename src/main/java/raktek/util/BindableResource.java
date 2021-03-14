package raktek.util;

import akasha.gl.WebGL2RenderingContext;
import javax.annotation.Nonnull;

public abstract class BindableResource<T>
  extends Resource<T>
{
  protected BindableResource( @Nonnull final WebGL2RenderingContext gl )
  {
    super( gl );
  }

  public abstract void bind();

  public abstract void unbind();
}
