package raktek.util;

import elemental3.core.Float32Array;
import elemental3.gl.UniformDataType;
import elemental3.gl.WebGL2RenderingContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class FMesh
{
  @Nonnull
  private final Program _program;
  @Nonnull
  private final Geometry _geometry;
  @Nonnull
  private final Map<String, Uniform> _uniforms = new HashMap<>();

  public FMesh( @Nonnull final Program program, @Nonnull final Geometry geometry )
  {
    _program = program;
    _program.allocate();
    program.verify();
    final ProgramDescriptor descriptor = program.getDescriptor();
    final int uniformCount = descriptor.getUniformCount();
    for ( int i = 0; i < uniformCount; i++ )
    {
      final UniformDescriptor uniform = descriptor.getUniform( i );
      final int type = uniform.getType();
      //      SAMPLER_2D textureData1
      if ( WebGL2RenderingContext.FLOAT_VEC3 == type )
      {
        _uniforms.put( uniform.getName(), new Vector3fvUniform( uniform, new Float32Array( 3 ) ) );
      }
      else if ( WebGL2RenderingContext.FLOAT_MAT4 == type )
      {
        _uniforms.put( uniform.getName(), new Matrix4fvUniform( uniform, new Float32Array( 16 ) ) );
      }
      else
      {
        throw new IllegalStateException( "Uniform of data type " + UniformDataType.Validator.describe( type ) +
                                         " is not yet supported" );
      }
    }

    _geometry = Objects.requireNonNull( geometry );
    _geometry.allocate();
  }

  @Nonnull
  public Uniform getUniformByName( @Nonnull final String name )
  {
    final Uniform uniform = _uniforms.get( name );
    assert null != uniform;
    return uniform;
  }

  public void render( @Nonnull final WebGL2RenderingContext gl )
  {
    _program.bind();
    for ( final Uniform uniform : _uniforms.values() )
    {
      uniform.sendToGpu( gl );
    }
    _geometry.draw();
    _program.unbind();
  }
}
