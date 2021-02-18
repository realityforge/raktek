package raktek.util;

import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class ProgramDescriptor
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final AttributeDescriptor[] _attributes;
  @Nonnull
  private final UniformDescriptor[] _uniforms;

  public ProgramDescriptor( @Nonnull final String name,
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
}
