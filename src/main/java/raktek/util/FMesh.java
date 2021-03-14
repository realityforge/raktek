package raktek.util;

import akasha.core.Float32Array;
import akasha.gl.UniformDataType;
import akasha.gl.WebGL2RenderingContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class FMesh
{
  @Nonnull
  private final Program _program;
  @Nonnull
  private final VertexArrayObject _vertexArrayObject;
  @Nonnull
  private final Map<String, Uniform> _uniforms = new HashMap<>();

  public FMesh( @Nonnull final Program program, @Nonnull final VertexArrayObject vertexArrayObject )
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
        throw new IllegalStateException( "Uniform of data type " + UniformDataType.Util.describe( type ) +
                                         " is not yet supported" );
      }
    }

    _vertexArrayObject = Objects.requireNonNull( vertexArrayObject );
    _vertexArrayObject.allocate();
  }

  @Nonnull
  public Uniform getUniformByName( @Nonnull final String name )
  {
    final Uniform uniform = _uniforms.get( name );
    if ( null == uniform )
    {
      throw new NoSuchUniformException( name );
    }
    return uniform;
  }

  public void render( @Nonnull final WebGL2RenderingContext gl )
  {
    _program.bind();
    for ( final Uniform uniform : _uniforms.values() )
    {
      uniform.sendToGpu( gl );
    }
    //TODO: Upload any buffers that have changed here? Or do we do that when we change the buffers
    _vertexArrayObject.draw();
    _program.unbind();
  }
}
