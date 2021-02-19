package raktek.util;

import elemental3.HTMLImageElement;
import elemental3.Image;
import elemental3.gl.AttributeIndex;
import elemental3.gl.TextureMagnificationFilter;
import elemental3.gl.TextureMinificationFilter;
import elemental3.gl.TextureWrapMode;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.gl.WebGLProgram;
import elemental3.gl.WebGLTexture;
import elemental3.gl.WebGLUniformLocation;
import elemental3.promise.Promise;
import javax.annotation.Nonnull;

// TODO: All of these methods should take a onError handler that is invoked when unexpected error
//  occurs. We should then follow this up with throwing a runtime error to rollback state. Both of
//  these actions should be done after we cleanup any allocated resources.
public final class GL
{
  private GL()
  {
  }

  @Nonnull
  public static WebGLTexture prepareTexture( @Nonnull final WebGL2RenderingContext gl,
                                             @Nonnull final HTMLImageElement image,
                                             final boolean flipYCoord,
                                             @TextureMagnificationFilter final int magFilter,
                                             @TextureMinificationFilter final int minFilter,
                                             @TextureWrapMode final int wrapS,
                                             @TextureWrapMode final int wrapT )
  {
    // Bind texture1 texture buffer to the TEXTURE_2D gate/channel and send data across
    final WebGLTexture texture = gl.createTexture();
    assert null != texture;
    gl.bindTexture( WebGL2RenderingContext.TEXTURE_2D, texture );

    if ( flipYCoord )
    {
      gl.pixelStorei( WebGL2RenderingContext.UNPACK_FLIP_Y_WEBGL, 1 );
    }

    // This is the call that pushes data across to GPU so will be "slow"
    gl.texImage2D( WebGL2RenderingContext.TEXTURE_2D,
                   0,
                   WebGL2RenderingContext.RGB,
                   WebGL2RenderingContext.RGB,
                   WebGL2RenderingContext.UNSIGNED_BYTE,
                   image );

    // Make sure we specify how perform interpolation between texture coordinates

    gl.texParameteri( WebGL2RenderingContext.TEXTURE_2D,
                      WebGL2RenderingContext.TEXTURE_MAG_FILTER,
                      magFilter );
    gl.texParameteri( WebGL2RenderingContext.TEXTURE_2D,
                      WebGL2RenderingContext.TEXTURE_MIN_FILTER,
                      minFilter );
    gl.texParameteri( WebGL2RenderingContext.TEXTURE_2D,
                      WebGL2RenderingContext.TEXTURE_WRAP_S,
                      wrapS );
    gl.texParameteri( WebGL2RenderingContext.TEXTURE_2D,
                      WebGL2RenderingContext.TEXTURE_WRAP_T,
                      wrapT );
    return texture;
  }

  public static void bindTexture( @Nonnull final WebGL2RenderingContext gl,
                                  @Nonnull final WebGLUniformLocation uniform,
                                  @Nonnull final WebGLTexture texture,
                                  final int index )
  {
    gl.activeTexture( WebGL2RenderingContext.TEXTURE0 + index );
    gl.bindTexture( WebGL2RenderingContext.TEXTURE_2D, texture );
    gl.uniform1i( uniform, index );
  }

  @Nonnull
  public static Promise<WebGLTexture> loadTexture( @Nonnull final WebGL2RenderingContext gl,
                                                   @Nonnull final String src )
  {
    return loadTexture( gl, src, false );
  }

  @Nonnull
  public static Promise<WebGLTexture> loadTexture( @Nonnull final WebGL2RenderingContext gl,
                                                   @Nonnull final String src,
                                                   final boolean flipYCoord )
  {
    return loadTexture( src, image -> prepareTexture( gl,
                                                      image,
                                                      flipYCoord,
                                                      WebGL2RenderingContext.LINEAR,
                                                      WebGL2RenderingContext.LINEAR,
                                                      WebGL2RenderingContext.CLAMP_TO_EDGE,
                                                      WebGL2RenderingContext.CLAMP_TO_EDGE ) );
  }

  public interface PrepareTextureFn
  {
    @Nonnull
    WebGLTexture prepareTexture( @Nonnull HTMLImageElement image );
  }

  @Nonnull
  public static Promise<WebGLTexture> loadTexture( @Nonnull final String src,
                                                   @Nonnull final PrepareTextureFn prepareImageFn )
  {
    return new Promise<>( ( resolveFn, rejectFn ) -> {
      final HTMLImageElement image = new Image();
      image.src = src;
      image.onload = e -> resolveFn.resolve( prepareImageFn.prepareTexture( image ) );
      image.onerror = ( e, s, l, c, o ) -> rejectFn.reject( e );
    } );
  }

  /**
   * Describe specified WebGL2 type, returning "UNKNOWN" if supplied type matches no known value.
   *
   * @param type the type.
   * @return the string label for type.
   */
  @Nonnull
  public static String describeType( final int type )
  {
    switch ( type )
    {
      case WebGL2RenderingContext.FLOAT:
        return "FLOAT";
      case WebGL2RenderingContext.FLOAT_VEC2:
        return "FLOAT_VEC2";
      case WebGL2RenderingContext.FLOAT_VEC3:
        return "FLOAT_VEC3";
      case WebGL2RenderingContext.FLOAT_VEC4:
        return "FLOAT_VEC4";
      case WebGL2RenderingContext.INT:
        return "INT";
      case WebGL2RenderingContext.INT_VEC2:
        return "INT_VEC2";
      case WebGL2RenderingContext.INT_VEC3:
        return "INT_VEC3";
      case WebGL2RenderingContext.INT_VEC4:
        return "INT_VEC4";
      case WebGL2RenderingContext.BOOL:
        return "BOOL";
      case WebGL2RenderingContext.BOOL_VEC2:
        return "BOOL_VEC2";
      case WebGL2RenderingContext.BOOL_VEC3:
        return "BOOL_VEC3";
      case WebGL2RenderingContext.BOOL_VEC4:
        return "BOOL_VEC4";
      case WebGL2RenderingContext.FLOAT_MAT2:
        return "FLOAT_MAT2";
      case WebGL2RenderingContext.FLOAT_MAT3:
        return "FLOAT_MAT3";
      case WebGL2RenderingContext.FLOAT_MAT4:
        return "FLOAT_MAT4";
      case WebGL2RenderingContext.SAMPLER_2D:
        return "SAMPLER_2D";
      case WebGL2RenderingContext.SAMPLER_CUBE:
        return "SAMPLER_CUBE";
      case WebGL2RenderingContext.UNSIGNED_INT:
        return "UNSIGNED_INT";
      case WebGL2RenderingContext.UNSIGNED_INT_VEC2:
        return "UNSIGNED_INT_VEC2";
      case WebGL2RenderingContext.UNSIGNED_INT_VEC3:
        return "UNSIGNED_INT_VEC3";
      case WebGL2RenderingContext.UNSIGNED_INT_VEC4:
        return "UNSIGNED_INT_VEC4";
      case WebGL2RenderingContext.FLOAT_MAT2x3:
        return "FLOAT_MAT2x3";
      case WebGL2RenderingContext.FLOAT_MAT2x4:
        return "FLOAT_MAT2x4";
      case WebGL2RenderingContext.FLOAT_MAT3x2:
        return "FLOAT_MAT3x2";
      case WebGL2RenderingContext.FLOAT_MAT3x4:
        return "FLOAT_MAT3x4";
      case WebGL2RenderingContext.FLOAT_MAT4x2:
        return "FLOAT_MAT4x2";
      case WebGL2RenderingContext.FLOAT_MAT4x3:
        return "FLOAT_MAT4x3";
      case WebGL2RenderingContext.SAMPLER_3D:
        return "SAMPLER_3D";
      case WebGL2RenderingContext.SAMPLER_2D_SHADOW:
        return "SAMPLER_2D_SHADOW";
      case WebGL2RenderingContext.SAMPLER_2D_ARRAY:
        return "SAMPLER_2D_ARRAY";
      case WebGL2RenderingContext.SAMPLER_2D_ARRAY_SHADOW:
        return "SAMPLER_2D_ARRAY_SHADOW";
      case WebGL2RenderingContext.SAMPLER_CUBE_SHADOW:
        return "SAMPLER_CUBE_SHADOW";
      case WebGL2RenderingContext.INT_SAMPLER_2D:
        return "INT_SAMPLER_2D";
      case WebGL2RenderingContext.INT_SAMPLER_3D:
        return "INT_SAMPLER_3D";
      case WebGL2RenderingContext.INT_SAMPLER_CUBE:
        return "INT_SAMPLER_CUBE";
      case WebGL2RenderingContext.INT_SAMPLER_2D_ARRAY:
        return "INT_SAMPLER_2D_ARRAY";
      case WebGL2RenderingContext.UNSIGNED_INT_SAMPLER_2D:
        return "UNSIGNED_INT_SAMPLER_2D";
      case WebGL2RenderingContext.UNSIGNED_INT_SAMPLER_3D:
        return "UNSIGNED_INT_SAMPLER_3D";
      case WebGL2RenderingContext.UNSIGNED_INT_SAMPLER_CUBE:
        return "UNSIGNED_INT_SAMPLER_CUBE";
      case WebGL2RenderingContext.UNSIGNED_INT_SAMPLER_2D_ARRAY:
        return "UNSIGNED_INT_SAMPLER_2D_ARRAY";
      default:
        return "UNKNOWN";
    }
  }

  @AttributeIndex
  public static int getAttribLocation( @Nonnull final WebGL2RenderingContext gl,
                                       @Nonnull final WebGLProgram program,
                                       @Nonnull final String name )
  {
    final int location = gl.getAttribLocation( program, name );
    assert WebGL2RenderingContext.INVALID_INDEX != location;
    return location;
  }

  @Nonnull
  public static WebGLUniformLocation getUniformLocation( @Nonnull final WebGL2RenderingContext gl,
                                                         @Nonnull final WebGLProgram program,
                                                         @Nonnull final String name )
  {
    final WebGLUniformLocation location = gl.getUniformLocation( program, name );
    assert null != location;
    return location;
  }
}
