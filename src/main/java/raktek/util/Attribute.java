package raktek.util;

import elemental3.gl.AttributeIntegerDataType;
import elemental3.gl.WebGL2RenderingContext;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class Attribute
{
  /**
   * The data and layout of data.
   */
  @Nonnull
  private final Buffer _buffer;
  /**
   * The description of how to access the buffer to retrieve the vertex data.
   */
  @Nonnull
  private final Accessor _accessor;
  /**
   * The location/index of attribute in the program. This can either be specified in
   * the shader definition or looked up at runtime. Specifying the value in the shader source
   * is more efficient but more complex when authoring shaders.
   */
  private int _location;

  public Attribute( @Nonnull final Buffer buffer, @Nonnull final Accessor accessor )
  {
    this( buffer, accessor, WebGL2RenderingContext.INVALID_INDEX );
  }

  public Attribute( @Nonnull final Buffer buffer, @Nonnull final Accessor accessor, final int location )
  {
    _buffer = Objects.requireNonNull( buffer );
    _accessor = Objects.requireNonNull( accessor );
    _location = location;
  }

  @Nonnull
  public Buffer getBuffer()
  {
    return _buffer;
  }

  public boolean isLocationValid()
  {
    return WebGL2RenderingContext.INVALID_INDEX != _location;
  }

  public int getLocation()
  {
    return _location;
  }

  public void setLocation( final int location )
  {
    // Location should only be set once
    assert WebGL2RenderingContext.INVALID_INDEX == _location;
    _location = location;
  }

  public void sendToGpu()
  {
    assert isLocationValid();
    final WebGL2RenderingContext gl = _buffer.gl();
    gl.enableVertexAttribArray( _location );
    _buffer.bind();
    if ( _accessor.isInteger() )
    {
      gl.vertexAttribIPointer( _location,
                               _accessor.getComponentCount(),
                               AttributeIntegerDataType.Validator.cast( _accessor.getComponentType() ),
                               _accessor.getStride(),
                               _accessor.getOffset() );
    }
    else
    {
      gl.vertexAttribPointer( _location,
                              _accessor.getComponentCount(),
                              _accessor.getComponentType(),
                              _accessor.shouldNormalize(),
                              _accessor.getStride(),
                              _accessor.getOffset() );
    }
  }
}
