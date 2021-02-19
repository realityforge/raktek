package raktek.util;

import elemental3.gl.AttributeIndex;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLProgram;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Class containing a mapping between an attribute name and an attribute index.
 * This class may record a mapping that is declared in the shader source or it
 * may represent a request to explicitly bind an index to a specific name.
 */
public final class AttributeLocation
{
  @Nonnull
  private final String _name;
  @AttributeIndex
  private final int _index;
  private final boolean _override;

  public AttributeLocation( @Nonnull final String name,
                            @AttributeIndex final int index,
                            final boolean override )
  {
    assert WebGL2RenderingContext.INVALID_INDEX != index;
    _name = Objects.requireNonNull( name );
    _index = index;
    _override = override;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @AttributeIndex
  public int getIndex()
  {
    return _index;
  }

  /**
   * Return true if the {@link WebGL2RenderingContext#bindAttribLocation(WebGLProgram, int, String)} should be invoked to override the index assigned by the driver.
   *
   * @return true if the {@link WebGL2RenderingContext#bindAttribLocation(WebGLProgram, int, String)} should be invoked to override the index assigned by the driver.
   */
  public boolean isOverride()
  {
    return _override;
  }

  /**
   * Return true if the data represents the index explicitly declared in the shader source.
   *
   * @return true if the data represents the index explicitly declared in the shader source.
   */
  public boolean isDeclared()
  {
    return !isOverride();
  }
}
