package raktek.util;

import elemental3.Console;
import elemental3.gl.AttributeDataType;
import elemental3.gl.UniformDataType;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLActiveInfo;
import elemental3.gl.WebGLProgram;
import elemental3.gl.WebGLUniformLocation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import jsinterop.base.Any;
import raktek.Raktek;

public final class ProgramDescriptor
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final AttributeDescriptor[] _attributes;
  @Nonnull
  private final UniformDescriptor[] _uniforms;

  private ProgramDescriptor( @Nonnull final String name,
                             @Nonnull final AttributeDescriptor[] attributes,
                             @Nonnull final UniformDescriptor[] uniforms )
  {
    _name = Objects.requireNonNull( name );
    _attributes = Objects.requireNonNull( attributes );
    _uniforms = Objects.requireNonNull( uniforms );
    assert Arrays.stream( _attributes ).allMatch( Objects::nonNull );
    assert Arrays.stream( _uniforms ).allMatch( Objects::nonNull );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  public int getAttributeCount()
  {
    return _attributes.length;
  }

  @Nonnull
  public AttributeDescriptor getAttribute( final int index )
  {
    return _attributes[ index ];
  }

  public int getUniformCount()
  {
    return _uniforms.length;
  }

  @Nonnull
  public UniformDescriptor getUniform( final int index )
  {
    return _uniforms[ index ];
  }

  @Override
  public String toString()
  {
    return Raktek.shouldSupportDebugToString() ? toDebugString() : super.toString();
  }

  @Nonnull
  public String toDebugString()
  {
    return "Program[" + getName() +
           ",attributes=" + Arrays.asList( _attributes ) +
           ",uniforms=" + Arrays.asList( _uniforms ) +
           "]";
  }

  @Nonnull
  static ProgramDescriptor create( @Nonnull final WebGL2RenderingContext gl,
                                   @Nonnull final String programName,
                                   @Nonnull final AttributeLocation[] attributeLocations,
                                   @Nonnull final WebGLProgram program )
  {
    final Any activeUniforms = gl.getProgramParameter( program, WebGL2RenderingContext.ACTIVE_UNIFORMS );
    assert null != activeUniforms;
    final int uniformCount = activeUniforms.asInt();
    final List<UniformDescriptor> uniforms = new ArrayList<>();
    for ( int i = 0; i < uniformCount; i++ )
    {
      final WebGLActiveInfo info = gl.getActiveUniform( program, i );
      assert null != info;
      final String name = info.name();
      final WebGLUniformLocation location = gl.getUniformLocation( program, name );
      assert null != location;
      final UniformDescriptor uniform =
        new UniformDescriptor( name, UniformDataType.Validator.cast( info.type() ), location );
      uniforms.add( uniform );
      Console.log( uniform.toString() );
    }
    final Any activeAttributes = gl.getProgramParameter( program, WebGL2RenderingContext.ACTIVE_ATTRIBUTES );
    assert null != activeAttributes;
    final int attributeCount = activeAttributes.asInt();
    final List<AttributeDescriptor> attributes = new ArrayList<>();
    for ( int i = 0; i < attributeCount; i++ )
    {
      final WebGLActiveInfo info = gl.getActiveAttrib( program, i );
      assert null != info;

      int index = WebGL2RenderingContext.INVALID_INDEX;
      final String name = info.name();
      for ( final AttributeLocation location : attributeLocations )
      {
        if ( location.getName().equals( name ) )
        {
          index = location.getIndex();
        }
      }
      if ( WebGL2RenderingContext.INVALID_INDEX == index )
      {
        index = gl.getAttribLocation( program, name );
        assert WebGL2RenderingContext.INVALID_INDEX != index;
      }
      final AttributeDescriptor attribute =
        new AttributeDescriptor( name, AttributeDataType.Validator.cast( info.type() ), index );
      attributes.add( attribute );
      Console.log( attribute.toString() );
    }
    return new ProgramDescriptor( programName,
                                  attributes.toArray( new AttributeDescriptor[ 0 ] ),
                                  uniforms.toArray( new UniformDescriptor[ 0 ] ) );
  }
}
