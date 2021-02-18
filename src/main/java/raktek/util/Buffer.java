package raktek.util;

import elemental3.core.ArrayBufferView;
import elemental3.gl.BufferTargetType;
import elemental3.gl.UsageType;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLBuffer;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class Buffer
  extends BindableResource<WebGLBuffer>
{
  @Nonnull
  private final ArrayBufferView _data;
  @UsageType
  private final int _usage;
  @BufferTargetType
  private final int _bufferTarget;

  public Buffer( @Nonnull final WebGL2RenderingContext gl,
                 @Nonnull final ArrayBufferView data,
                 @BufferTargetType final int bufferTarget )
  {
    this( gl, data, WebGL2RenderingContext.STATIC_DRAW, bufferTarget );
  }

  public Buffer( @Nonnull final WebGL2RenderingContext gl,
                 @Nonnull final ArrayBufferView data,
                 @UsageType final int usage,
                 @BufferTargetType final int bufferTarget )
  {
    super( gl );
    UsageType.Validator.assertValid( usage );
    BufferTargetType.Validator.assertValid( bufferTarget );
    _data = Objects.requireNonNull( data );
    _usage = usage;
    _bufferTarget = bufferTarget;
  }

  @Nonnull
  public ArrayBufferView getData()
  {
    return _data;
  }

  @UsageType
  public int getUsage()
  {
    return _usage;
  }

  @Override
  public void bind()
  {
    gl().bindBuffer( getBufferTarget(), getHandle() );
  }

  @BufferTargetType
  public int getBufferTarget()
  {
    return _bufferTarget;
  }

  @Override
  public void unbind()
  {
    gl().bindBuffer( getBufferTarget(), null );
  }

  @Nonnull
  @Override
  protected WebGLBuffer allocateResource()
    throws ResourceException
  {
    final WebGL2RenderingContext gl = gl();
    final WebGLBuffer buffer = gl.createBuffer();
    if ( null == buffer )
    {
      throw new ResourceException( ErrorCode.BUFFER_CREATE_FAILED, gl.getError() );
    }
    gl.bindBuffer( getBufferTarget(), buffer );
    gl.bufferData( getBufferTarget(), _data, _usage );
    return buffer;
  }

  @Override
  protected void releaseResource( @Nonnull final WebGLBuffer handle )
  {
    gl().deleteBuffer( handle );
  }
}
