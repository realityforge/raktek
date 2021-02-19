package raktek.util.geometries;

import elemental3.core.Float32Array;
import elemental3.gl.DrawPrimitiveType;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.lang.JsArray;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.intellij.lang.annotations.MagicConstant;
import raktek.util.Accessor;
import raktek.util.Attribute;
import raktek.util.Buffer;
import raktek.util.Geometry;
import raktek.util.Program;

public final class CubeGeometryFactory
{
  /**
   * If specified the factory should create vertex normals.
   */
  public static final int NORMALS = 0x01;
  /**
   * If specified the factory should create vertex uvs.
   */
  public static final int UVS = 0x02;
  /**
   * If specified the factory should create vertex colors.
   */
  public static final int COLORS = 0x04;
  // We use JsArray of doubles as this will autogrow and contains native js numbers
  @Nonnull
  private final JsArray<Double> _positions = new JsArray<>();
  @Nullable
  private final JsArray<Double> _normals;
  @Nullable
  private final JsArray<Double> _uvs;
  @Nullable
  private final JsArray<Double> _colors;
  @Nonnull
  private final Geometry _geometry;

  @Nonnull
  public static Geometry create( @Nonnull final WebGL2RenderingContext gl,
                                 @Nonnull final Program program,
                                 final double length )
  {
    return create( gl, program, length, UVS | COLORS );
  }

  @Nonnull
  public static Geometry create( @Nonnull final WebGL2RenderingContext gl,
                                 @Nonnull final Program program,
                                 final double length,
                                 @MagicConstant( flags = { NORMALS, UVS, COLORS } ) final int options )
  {
    return new CubeGeometryFactory( gl, program, WebGL2RenderingContext.TRIANGLES, length, options )._geometry;
  }

  private CubeGeometryFactory( @Nonnull final WebGL2RenderingContext gl,
                               @Nonnull final Program program,
                               @DrawPrimitiveType final int mode,
                               final double length,
                               @MagicConstant( flags = { NORMALS, UVS, COLORS } ) final int options )
  {
    _uvs = UVS == ( UVS & options ) ? new JsArray<>() : null;
    _colors = COLORS == ( COLORS & options ) ? new JsArray<>() : null;
    _normals = NORMALS == ( NORMALS & options ) ? new JsArray<>() : null;

    buildData( length );

    final List<Attribute> attributes = new ArrayList<>();
    attributes.add( new Attribute( "position",
                                   new Buffer( gl,
                                               new Float32Array( _positions ),
                                               WebGL2RenderingContext.ARRAY_BUFFER ),
                                   new Accessor( 3 ) ) );
    if ( null != _colors )
    {
      attributes.add( new Attribute( "color",
                                     new Buffer( gl, new Float32Array( _colors ), WebGL2RenderingContext.ARRAY_BUFFER ),
                                     new Accessor( 4 ) ) );
    }
    if ( null != _uvs )
    {
      attributes.add( new Attribute( "uv",
                                     new Buffer( gl, new Float32Array( _uvs ), WebGL2RenderingContext.ARRAY_BUFFER ),
                                     new Accessor( 2 ) ) );
    }
    if ( null != _normals )
    {
      attributes.add( new Attribute( "normal",
                                     new Buffer( gl,
                                                 new Float32Array( _normals ),
                                                 WebGL2RenderingContext.ARRAY_BUFFER ),
                                     new Accessor( 3 ) ) );
    }
    _geometry = new Geometry( gl, program, mode, 0, 36, null, attributes.toArray( new Attribute[ 0 ] ) );
  }

  private void buildData( final double length )
  {
    final double[] positions = new double[]
      {

        -length, -length, -length,
        length, -length, -length,
        length, length, -length,
        length, length, -length,
        -length, length, -length,
        -length, -length, -length,

        -length, -length, length,
        length, -length, length,
        length, length, length,
        length, length, length,
        -length, length, length,
        -length, -length, length,

        -length, length, length,
        -length, length, -length,
        -length, -length, -length,
        -length, -length, -length,
        -length, -length, length,
        -length, length, length,

        length, length, length,
        length, length, -length,
        length, -length, -length,
        length, -length, -length,
        length, -length, length,
        length, length, length,

        -length, -length, -length,
        length, -length, -length,
        length, -length, length,
        length, -length, length,
        -length, -length, length,
        -length, -length, -length,

        // Left face
        -length, length, -length,
        length, length, -length,
        length, length, length,
        length, length, length,
        -length, length, length,
        -length, length, -length
      };
    for ( double position : positions )
    {
      _positions.push( position );
    }
    if ( null != _uvs )
    {
      final double[] textureCoordinates = new double[]
        {

          0.0, 1.0,
          1.0, 1.0,
          1.0, 0.0,
          1.0, 0.0,
          0.0, 0.0,
          0.0, 1.0,

          0.0, 1.0,
          1.0, 1.0,
          1.0, 0.0,
          1.0, 0.0,
          0.0, 0.0,
          0.0, 1.0,

          1.0, 1.0,
          1.0, 0.0,
          0.0, 0.0,
          0.0, 0.0,
          0.0, 1.0,
          1.0, 1.0,

          1.0, 1.0,
          1.0, 0.0,
          0.0, 0.0,
          0.0, 0.0,
          0.0, 1.0,
          1.0, 1.0,

          0.0, 0.0,
          1.0, 0.0,
          1.0, 1.0,
          1.0, 1.0,
          0.0, 1.0,
          0.0, 0.0,

          // Left face
          0.0, 0.0,
          1.0, 0.0,
          1.0, 1.0,
          1.0, 1.0,
          0.0, 1.0,
          0.0, 0.0
        };
      for ( double textureCoordinate : textureCoordinates )
      {
        _uvs.push( textureCoordinate );
      }
    }
    if ( null != _colors )
    {
      final double[] colors = new double[]
        {
          // Front face
          1.0, 0.0, 0.0, 1.0,
          1.0, 0.0, 0.0, 1.0,
          1.0, 0.0, 0.0, 1.0,
          1.0, 0.0, 0.0, 1.0,
          1.0, 0.0, 0.0, 1.0,
          1.0, 0.0, 0.0, 1.0,

          // Back face
          0.0, 1.0, 0.0, 1.0,
          0.0, 1.0, 0.0, 1.0,
          0.0, 1.0, 0.0, 1.0,
          0.0, 1.0, 0.0, 1.0,
          0.0, 1.0, 0.0, 1.0,
          0.0, 1.0, 0.0, 1.0,

          // Top face
          0.0, 0.0, 1.0, 1.0,
          0.0, 0.0, 1.0, 1.0,
          0.0, 0.0, 1.0, 1.0,
          0.0, 0.0, 1.0, 1.0,
          0.0, 0.0, 1.0, 1.0,
          0.0, 0.0, 1.0, 1.0,

          // Bottom face
          1.0, 1.0, 0.0, 1.0,
          1.0, 1.0, 0.0, 1.0,
          1.0, 1.0, 0.0, 1.0,
          1.0, 1.0, 0.0, 1.0,
          1.0, 1.0, 0.0, 1.0,
          1.0, 1.0, 0.0, 1.0,

          // Right face
          1.0, 0.0, 1.0, 1.0,
          1.0, 0.0, 1.0, 1.0,
          1.0, 0.0, 1.0, 1.0,
          1.0, 0.0, 1.0, 1.0,
          1.0, 0.0, 1.0, 1.0,
          1.0, 0.0, 1.0, 1.0,

          // Left face
          0.0, 1.0, 1.0, 1.0,
          0.0, 1.0, 1.0, 1.0,
          0.0, 1.0, 1.0, 1.0,
          0.0, 1.0, 1.0, 1.0,
          0.0, 1.0, 1.0, 1.0,
          0.0, 1.0, 1.0, 1.0
        };
      for ( double value : colors )
      {
        _colors.push( value );
      }
    }
    if ( null != _normals )
    {
      final double[] normals = new double[]
        {
          // Front face
          0.0, 0.0, -1.0,
          0.0, 0.0, -1.0,
          0.0, 0.0, -1.0,
          0.0, 0.0, -1.0,
          0.0, 0.0, -1.0,
          0.0, 0.0, -1.0,

          0.0, 0.0, 1.0,
          0.0, 0.0, 1.0,
          0.0, 0.0, 1.0,
          0.0, 0.0, 1.0,
          0.0, 0.0, 1.0,
          0.0, 0.0, 1.0,

          -1.0, 0.0, 0.0,
          -1.0, 0.0, 0.0,
          -1.0, 0.0, 0.0,
          -1.0, 0.0, 0.0,
          -1.0, 0.0, 0.0,
          -1.0, 0.0, 0.0,

          1.0, 0.0, 0.0,
          1.0, 0.0, 0.0,
          1.0, 0.0, 0.0,
          1.0, 0.0, 0.0,
          1.0, 0.0, 0.0,
          1.0, 0.0, 0.0,

          0.0, -1.0, 0.0,
          0.0, -1.0, 0.0,
          0.0, -1.0, 0.0,
          0.0, -1.0, 0.0,
          0.0, -1.0, 0.0,
          0.0, -1.0, 0.0,

          0.0, 1.0, 0.0,
          0.0, 1.0, 0.0,
          0.0, 1.0, 0.0,
          0.0, 1.0, 0.0,
          0.0, 1.0, 0.0,
          0.0, 1.0, 0.0
        };
      for ( double value : normals )
      {
        _normals.push( value );
      }
    }
  }
}
