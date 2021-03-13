package raktek.util;

import elemental3.gl.AttributeComponentIntegerDataType;
import elemental3.gl.DrawMode;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLVertexArrayObject;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class VertexArrayObject
  extends BindableResource<WebGLVertexArrayObject>
{
  @DrawMode
  private final int _mode;
  private final int _offset;
  private final int _count;
  // 0 implies that the geometry is not setup to be instances, >1 indicates the maximum number of instances possible
  private final int _maxInstances;
  @Nonnull
  private final Attribute[] _attributes;
  @Nullable
  private final ElementArray _elements;
  @Nonnull
  private final Program _program;

  public VertexArrayObject( @Nonnull final WebGL2RenderingContext gl,
                            @Nonnull final Program program,
                            @Nonnull final Attribute... attributes )
  {
    this( gl,
          program,
          VertexUtil.getPrimitiveCount( WebGL2RenderingContext.TRIANGLES, Attribute.getVertexCount( attributes ) ),
          attributes );
  }

  public VertexArrayObject( @Nonnull final WebGL2RenderingContext gl,
                            @Nonnull final Program program,
                            final int count,
                            @Nonnull final Attribute... attributes )
  {
    this( gl, program, WebGL2RenderingContext.TRIANGLES, 0, count, null, attributes );
  }

  public VertexArrayObject( @Nonnull final WebGL2RenderingContext gl,
                            @Nonnull final Program program,
                            @DrawMode final int mode,
                            final int offset,
                            final int count,
                            @Nullable final ElementArray elements,
                            @Nonnull final Attribute... attributes )
  {
    this( gl, program, mode, offset, count, 0, elements, attributes );
  }

  public VertexArrayObject( @Nonnull final WebGL2RenderingContext gl,
                            @Nonnull final Program program,
                            @DrawMode final int mode,
                            final int offset,
                            final int count,
                            final int maxInstances,
                            @Nullable final ElementArray elements,
                            @Nonnull final Attribute... attributes )
  {
    super( gl );
    assert offset >= 0 : "Offset must not be negative";
    assert count > 0 : "Count must be greater than 0";
    assert maxInstances >= 0 : "Max instance count must not be negative";
    assert attributes.length > 0 : "At least one attribute must be supplied";
    _program = Objects.requireNonNull( program );
    _mode = DrawMode.Util.requireValid( mode );
    _offset = offset;
    _count = count;
    _maxInstances = maxInstances;
    _elements = elements;
    _attributes = Objects.requireNonNull( attributes );
  }

  /**
   * Return true if the attribute data is accessed via an indexed array (a.k.a. elements buffer).
   *
   * @return true if the attribute data is accessed via an indexed array (a.k.a. elements buffer).
   */
  public boolean isIndexed()
  {
    return null != _elements;
  }

  @Override
  public void bind()
  {
    gl().bindVertexArray( getHandle() );
  }

  @Override
  public void unbind()
  {
    gl().bindVertexArray( null );
  }

  @Nonnull
  @Override
  protected WebGLVertexArrayObject allocateResource()
    throws ResourceException
  {
    // Upload the buffers ... outside the scope of the VAO
    if ( null != _elements )
    {
      _elements.getBuffer().allocateIfNecessary();
    }
    for ( final Attribute attribute : _attributes )
    {
      attribute.getBuffer().allocateIfNecessary();
    }

    final WebGL2RenderingContext gl = gl();
    final WebGLVertexArrayObject vertexArrayObject = gl.createVertexArray();
    assert null != vertexArrayObject;
    gl.bindVertexArray( vertexArrayObject );
    if ( null != _elements )
    {
      _elements.getBuffer().bind();
    }
    for ( final Attribute attribute : _attributes )
    {
      final AttributeDescriptor descriptor = _program.getDescriptor().getAttributeByName( attribute.getName() );
      final Buffer buffer = attribute.getBuffer();
      final Accessor accessor = attribute.getAccessor();
      final int index = descriptor.getIndex();
      gl.enableVertexAttribArray( index );
      buffer.bind();
      if ( accessor.isInteger() )
      {
        gl.vertexAttribIPointer( index,
                                 accessor.getComponentCount(),
                                 AttributeComponentIntegerDataType.Util.requireValid( accessor.getComponentType() ),
                                 accessor.getStride(),
                                 accessor.getOffset() );
      }
      else
      {
        gl.vertexAttribPointer( index,
                                accessor.getComponentCount(),
                                accessor.getComponentType(),
                                accessor.shouldNormalize(),
                                accessor.getStride(),
                                accessor.getOffset() );
      }
      gl.vertexAttribDivisor( index, accessor.getDivisor() );
    }
    gl.bindVertexArray( null );

    // No need to call disableVertexAttribArray as that state is bound to VAO and
    // effectively disabled when the VAO is unbound

    return vertexArrayObject;
  }

  @Override
  protected void releaseResource( @Nonnull final WebGLVertexArrayObject handle )
  {
    gl().deleteVertexArray( handle );
  }

  public boolean isInstanced()
  {
    return 0 != _maxInstances;
  }

  public void draw()
  {
    assert isAllocated();
    assert !isInstanced();
    bind();

    final WebGL2RenderingContext gl = gl();
    if ( null == _elements )
    {
      gl.drawArrays( _mode, _offset, _count );
    }
    else
    {
      gl.drawElements( _mode, _count, _elements.getType(), _offset );
    }
    unbind();
  }

  public void drawInstanced( final int instanceCount )
  {
    assert isAllocated();
    assert isInstanced();
    assert instanceCount <= _maxInstances : "Instance count must be below or equal to maxInstanceCount";
    bind();

    final WebGL2RenderingContext gl = gl();
    if ( null == _elements )
    {
      gl.drawArraysInstanced( _mode, _offset, _count, instanceCount );
    }
    else
    {
      gl.drawElementsInstanced( _mode, _count, _elements.getType(), _offset, instanceCount );
    }
    unbind();
  }
}
