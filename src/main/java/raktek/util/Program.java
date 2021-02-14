package raktek.util;

import elemental3.gl.KHR_parallel_shader_compile;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLProgram;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Any;
import jsinterop.base.Js;
import raktek.Raktek;

public final class Program
  extends Resource<WebGLProgram>
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final Shader _vertexShader;
  @Nonnull
  private final Shader _fragmentShader;
  @Nullable
  private String _error;

  public Program( @Nonnull final WebGL2RenderingContext gl,
                  @Nonnull final String name,
                  @Nonnull final Shader vertexShader,
                  @Nonnull final Shader fragmentShader )
  {
    super( gl, false );
    _name = Objects.requireNonNull( name );
    _vertexShader = Objects.requireNonNull( vertexShader );
    _fragmentShader = Objects.requireNonNull( fragmentShader );
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @Nonnull
  public Shader getVertexShader()
  {
    return _vertexShader;
  }

  @Nonnull
  public Shader getFragmentShader()
  {
    return _fragmentShader;
  }

  @Nonnull
  public WebGLProgram getWebGLProgram()
  {
    return getHandle();
  }

  public boolean hasError()
  {
    return null != _error;
  }

  /**
   * Return the an error description when the program could not be allocated.
   * This is only set after the {@link #allocate()} or {@link #verify()} methods are called and
   * only if an error occurred. This error is the raw form emitted by the underlying API.
   *
   * @return the error message resulting from the last attempt to allocate the program.
   */
  @Nullable
  public String getError()
  {
    return _error;
  }

  /**
   * Return the completion status. This relies on the <code>KHR_parallel_shader_compile</code> extension and will return
   * {@link CompletionStatus#UNKNOWN} if the extension is not present.
   *
   * @return the completion status.
   */
  @CompletionStatus
  public int isComplete()
  {
    final WebGL2RenderingContext gl = gl();
    final KHR_parallel_shader_compile extension =
      (KHR_parallel_shader_compile) gl.getExtension( "KHR_parallel_shader_compile" );
    if ( null == extension )
    {
      return CompletionStatus.UNKNOWN;
    }
    else
    {
      final Any parameter =
        gl.getProgramParameter( getHandle(), KHR_parallel_shader_compile.COMPLETION_STATUS_KHR );
      assert null != parameter;
      return parameter.asBoolean() ? CompletionStatus.COMPLETE : CompletionStatus.INCOMPLETE;
    }
  }

  @Nonnull
  @Override
  protected WebGLProgram allocateResource()
    throws ResourceException
  {
    _error = null;
    final WebGL2RenderingContext gl = gl();
    final WebGLProgram program = gl.createProgram();
    if ( null == program )
    {
      throw new ResourceException( ErrorCode.PROGRAM_CREATE_FAILED, gl.getError() );
    }
    _vertexShader.allocateIfNecessary();
    _fragmentShader.allocateIfNecessary();
    gl.attachShader( program, _vertexShader.getHandle() );
    gl.attachShader( program, _fragmentShader.getHandle() );
    gl.linkProgram( program );

    if ( Raktek.shouldCheckSProgramLinkAtAllocate() )
    {
      checkProgramLink( program );
    }
    return program;
  }

  @Override
  protected void releaseResource( @Nonnull final WebGLProgram handle )
  {
    gl().deleteProgram( getHandle() );
    //TODO: Assert context has no errors in debug mode
  }

  @Override
  protected void verifyResource( @Nonnull final WebGLProgram handle )
    throws ResourceException
  {
    if ( !Raktek.shouldCheckSProgramLinkAtAllocate() )
    {
      checkProgramLink( handle );
    }
  }

  private void checkProgramLink( @Nonnull final WebGLProgram program )
  {
    if ( Raktek.shouldCheckProgramLink() )
    {
      final WebGL2RenderingContext gl = gl();
      final Any parameter = gl.getProgramParameter( program, WebGL2RenderingContext.LINK_STATUS );
      assert null != parameter;
      if ( !parameter.asBoolean() )
      {
        _error = gl.getProgramInfoLog( program );
        gl.deleteProgram( program );
        throw new ResourceException( ErrorCode.PROGRAM_LINK_FAILED, gl.getError(), _error );
      }
    }
  }
}
