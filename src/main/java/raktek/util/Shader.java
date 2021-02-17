package raktek.util;

import elemental3.gl.GLSL;
import elemental3.gl.ShaderType;
import elemental3.gl.WEBGL_debug_shaders;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLShader;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Any;
import raktek.Raktek;

public final class Shader
  extends Resource<WebGLShader>
{
  @Nullable
  private final String _name;
  @ShaderType
  private final int _type;
  @GLSL
  @Nonnull
  private final String _source;
  /**
   * A cache of the derived name set the first time it is derived.
   */
  @Nullable
  private String _derivedName;
  @Nullable
  private String _error;

  public Shader( @Nonnull final WebGL2RenderingContext gl,
                 @Nullable final String name,
                 @ShaderType final int type,
                 @Nonnull final String source )
  {
    super( gl, false );
    ShaderType.Validator.assertValid( type );
    _name = name;
    _type = type;
    _source = Objects.requireNonNull( source );
  }

  @Nonnull
  public String getName()
  {
    return deriveName();
  }

  @Nonnull
  public String getLabel()
  {
    return ( WebGL2RenderingContext.FRAGMENT_SHADER == getType() ? "fragment" : "vertex" ) + " shader " + getName();
  }

  @ShaderType
  public int getType()
  {
    return _type;
  }

  @GLSL
  @Nonnull
  public String getSource()
  {
    return _source;
  }

  public boolean hasError()
  {
    return null != _error;
  }

  /**
   * Get the shader source translated for the platform's API.
   *
   * @return the translated shader source.
   */
  @Nonnull
  public String getTranslatedSource()
  {
    if ( !isAllocated() )
    {
      return "Translated source unavailable: Shader not allocated";
    }
    else if ( hasError() )
    {
      return "Translated source unavailable: Error compiling shader";
    }
    else
    {
      //TODO: This extension lookup should be cached
      final WEBGL_debug_shaders extensions = (WEBGL_debug_shaders) gl().getExtension( WEBGL_debug_shaders.NAME );
      if ( null != extensions )
      {
        final WebGLShader handle = getHandle();
        return extensions.getTranslatedShaderSource( handle );
      }
      else
      {
        return "Translated source unavailable: WEBGL_debug_shaders extension not present";
      }
    }
  }

  /**
   * Return the an error description when the shader could not be allocated.
   * This is only set after the {@link #allocate()} or {@link #verify()} methods are called and
   * only if an error occurred. This error is the raw form emitted by the underlying API.
   * If you want an error message formatted in a more developer friendly manner use {@link #getFormattedErrorLog()}.
   *
   * @return the error message resulting from the last attempt to allocate the shader.
   */
  @Nullable
  public String getError()
  {
    return _error;
  }

  /**
   * Return the an error description when the shader could not be allocated.
   * This is only set after the allocate() call is invoked and only if an error occurred.
   * This error is formatted to be easier to consume by the developer compared to #{@link #getError()}.
   *
   * @return the error message resulting from the last attempt to allocate the shader.
   * @see <a href="https://github.com/wwwtyro/gl-format-compiler-error">gl-format-compiler-error library that inspired this capability</a>
   * @see <a href="https://luma.gl/docs/api-reference/webgl/shader">luma.gl Shader that this method mirrors</a>
   */
  @Nullable
  public String getFormattedErrorLog()
  {
    return null == _error ?
           null :
           ShaderUtil.formatInfoLog( _error, "GLSL compilation error in " + getLabel() + ":\n", _source );
  }

  @Override
  public String toString()
  {
    if ( Raktek.shouldSupportDebugToString() )
    {
      return ( WebGL2RenderingContext.FRAGMENT_SHADER == getType() ? "Fragment" : "Vertex" ) +
             "Shader[" + getName() + "]";
    }
    else
    {
      return super.toString();
    }
  }

  @Nonnull
  @Override
  protected WebGLShader allocateResource()
    throws ResourceException
  {
    _error = null;
    final WebGL2RenderingContext gl = gl();
    final WebGLShader shader = gl.createShader( _type );
    if ( null == shader )
    {
      throw new ResourceException( ErrorCode.SHADER_CREATE_FAILED, gl.getError() );
    }

    gl.shaderSource( shader, _source );
    // compileShader is a request to start compiling shader but if the checkCompileStatus is invoked
    // it will request the compile status of the shader and block until compile completes.
    gl.compileShader( shader );
    if ( Raktek.shouldCheckShaderCompileAtAllocate() )
    {
      checkCompileStatus( shader );
    }
    return shader;
  }

  @Override
  protected void verifyResource( @Nonnull final WebGLShader handle )
    throws ResourceException
  {
    if ( !Raktek.shouldCheckShaderCompileAtAllocate() )
    {
      checkCompileStatus( handle );
    }
  }

  void checkCompileStatus()
  {
    if ( isAllocated() )
    {
      doCheckCompileStatus( getHandle(), false );
    }
  }

  private void checkCompileStatus( @Nonnull final WebGLShader shader )
  {
    if ( Raktek.shouldCheckShaderCompile() )
    {
      doCheckCompileStatus( shader, true );
    }
  }

  private void doCheckCompileStatus( @Nonnull final WebGLShader shader, final boolean generateExceptionOnError )
  {
    final WebGL2RenderingContext gl = gl();
    final Any parameter = gl.getShaderParameter( shader, WebGL2RenderingContext.COMPILE_STATUS );
    assert null != parameter;
    if ( !parameter.asBoolean() )
    {
      _error = gl.getShaderInfoLog( shader );
      release();
      if ( generateExceptionOnError )
      {
        throw new ResourceException( ErrorCode.SHADER_COMPILE_FAILED, gl.getError(), _error );
      }
    }
  }

  @Override
  protected void releaseResource( @Nonnull final WebGLShader handle )
  {
    gl().deleteShader( handle );
    //TODO: Assert context has no errors in debug mode
  }

  @Nonnull
  private String deriveName()
  {
    if ( null != _name )
    {
      return _name;
    }
    else if ( null != _derivedName )
    {
      return _derivedName;
    }
    else
    {
      final String declaredName = ShaderUtil.extractName( _source );
      _derivedName = null != declaredName ? declaredName : "(unnamed)";
      return _derivedName;
    }
  }
}
