package raktek.util;

import elemental3.gl.GLSL;
import elemental3.gl.ShaderType;
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
    _name = name;
    _type = type;
    _source = Objects.requireNonNull( source );
  }

  @Nonnull
  public String getName()
  {
    return deriveName();
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
   * Return the an error description when the shader could not be allocated.
   * This is only set after the allocate() call is invoked and only if an error occurred.
   * This error is the raw form emitted by the underlying API. If you want an error message
   * formatted in a more developer friendly manner use {@link #getFormattedErrorLog()}.
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
    return null == _error ? null : ShaderUtil.formatInfoLog( _error, deriveName(), _type, _source );
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
      throw new ResourceException( ResourceException.SHADER_CREATE_FAILED, gl.getError() );
    }

    gl.shaderSource( shader, _source );
    gl.compileShader( shader );
    if ( Raktek.shouldCheckShaderCompile() )
    {
      final Any parameter = gl.getShaderParameter( shader, WebGL2RenderingContext.COMPILE_STATUS );
      assert null != parameter;
      if ( !parameter.asBoolean() )
      {
        _error = gl.getShaderInfoLog( shader );
        gl.deleteShader( shader );
        throw new ResourceException( ResourceException.SHADER_COMPILE_FAILED, gl.getError(), _error );
      }
    }
    return shader;
  }

  @Override
  protected void releaseResource( @Nonnull final WebGLShader handle )
  {
    gl().deleteShader( getHandle() );
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
