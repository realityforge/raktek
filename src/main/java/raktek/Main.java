package raktek;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import elemental3.Console;
import elemental3.Document;
import elemental3.Global;
import elemental3.HTMLCanvasElement;
import elemental3.core.Float32Array;
import elemental3.gl.WebGL2RenderingContext;
import elemental3.promise.Promise;
import javax.annotation.Nonnull;
import org.realityforge.vecmath.Matrix4d;
import org.realityforge.vecmath.Vector3f;
import raktek.util.Camera;
import raktek.util.CanvasUtil;
import raktek.util.FMesh;
import raktek.util.GL;
import raktek.util.MathUtil;
import raktek.util.Matrix4fvUniform;
import raktek.util.PerspectiveViewport;
import raktek.util.Program;
import raktek.util.ResourceDirectory;
import raktek.util.ResourceException;
import raktek.util.ResourceTypes;
import raktek.util.Shader;
import raktek.util.Vector3fvUniform;
import raktek.util.controls.FirsPersonControl;
import raktek.util.geometries.CubeGeometryFactory;

public final class Main
  implements EntryPoint
{
  @Nonnull
  private final Matrix4d _modelMatrix = new Matrix4d();
  @Nonnull
  private final Camera _camera = new Camera();
  @Nonnull
  private final Light _light = new Light();
  private Mesh _mesh;
  private FMesh _lightMesh;
  private double _angle;
  private boolean _sentToGpu;
  private float _time;
  private FirsPersonControl _control;
  private PerspectiveViewport _viewport;
  private boolean _printed;
  private String _boxVertexShaderSource;
  private String _boxFragmentShaderSource;
  private String _lightVertexShaderSource;
  private String _lightFragmentShaderSource;

  @Override
  public void onModuleLoad()
  {
    final String moduleBaseURL = GWT.getModuleBaseURL();
    final ResourceDirectory directory =
      new ResourceDirectory( moduleBaseURL.substring( 0, moduleBaseURL.length() - 1 ) );
    Promise.all( directory
                   .loadText( ResourceTypes.VERTEX_SHADER, "box" )
                   .thenAccept( resource -> _boxVertexShaderSource = resource ),
                 directory
                   .loadText( ResourceTypes.FRAGMENT_SHADER, "box" )
                   .thenAccept( resource -> _boxFragmentShaderSource = resource ),
                 directory
                   .loadText( ResourceTypes.VERTEX_SHADER, "light" )
                   .thenAccept( resource -> _lightVertexShaderSource = resource ),
                 directory
                   .loadText( ResourceTypes.FRAGMENT_SHADER, "light" )
                   .thenAccept( resource -> _lightFragmentShaderSource = resource ) )
      .thenAccept( o -> startApplication() );
  }

  private void startApplication()
  {
    final HTMLCanvasElement canvas = CanvasUtil.createCanvas();
    final WebGL2RenderingContext gl = CanvasUtil.getWebGL2RenderingContext( canvas );
    _viewport = new PerspectiveViewport( gl, canvas );
    _viewport.setFovY( MathUtil.degreesToRadians( 45 ) );
    _viewport.setZNear( 0.1 );
    _viewport.setZFar( 10.0 );

    try
    {
      final Program program = new Program( gl,
                                           "Mesh",
                                           new Shader( gl,
                                                       null,
                                                       WebGL2RenderingContext.VERTEX_SHADER,
                                                       _boxVertexShaderSource ),
                                           new Shader( gl,
                                                       null,
                                                       WebGL2RenderingContext.FRAGMENT_SHADER,
                                                       _boxFragmentShaderSource ) );

      _mesh = new Mesh( gl,
                        program,
                        CubeGeometryFactory.create( gl,
                                                    program,
                                                    0.5,
                                                    CubeGeometryFactory.NORMALS |
                                                    CubeGeometryFactory.UVS |
                                                    CubeGeometryFactory.COLORS ) );
      final Program lmProgram =
        new Program( gl,
                     "LightMesh",
                     new Shader( gl, null, WebGL2RenderingContext.VERTEX_SHADER, _lightVertexShaderSource ),
                     new Shader( gl, null, WebGL2RenderingContext.FRAGMENT_SHADER, _lightFragmentShaderSource ) );

      _lightMesh = new FMesh( lmProgram, CubeGeometryFactory.create( gl, lmProgram, 0.2, 0 ) );
    }
    catch ( final ResourceException e )
    {
      Console.log( "Error", e );
    }

    final Document document = Global.document();
    _control = new FirsPersonControl( _camera, document );

    renderLoop( gl );
  }

  private void renderLoop( @Nonnull final WebGL2RenderingContext gl )
  {
    Global.requestAnimationFrame( t -> renderLoop( gl ) );
    _viewport.resize();
    renderFrame( gl );
  }

  private void renderFrame( @Nonnull final WebGL2RenderingContext gl )
  {
    if ( null == _mesh.getTexture1() || null == _mesh.getTexture2() )
    {
      return;
    }
    else if ( !_sentToGpu )
    {
      // Have to send to GPU here as otherwise texture data has not loaded
      try
      {
        _mesh.getVertexArrayObject().allocate();
      }
      catch ( final ResourceException e )
      {
        Console.log( "Error", e );
      }

      _mesh.getProgram().bind();
      GL.bindTexture( gl, _mesh.getTextureData0(), _mesh.getTexture1(), 0 );
      GL.bindTexture( gl, _mesh.getTextureData1(), _mesh.getTexture2(), 1 );
      _sentToGpu = true;
    }

    gl.clearColor( 0, 0, 0, 1 );
    gl.clear( WebGL2RenderingContext.COLOR_BUFFER_BIT | WebGL2RenderingContext.DEPTH_BUFFER_BIT );
    gl.enable( WebGL2RenderingContext.DEPTH_TEST );

    // UpdateCamera should be done in the sim loop ... but we are inlining in render loop
    _control.updateCamera();

    final Vector3f position = _light.getPosition();
    position.y = (float) ( 2 * Math.sin( 0.1 * ( _time + position.y ) ) );
    position.x = (float) ( -2 * Math.sin( 0.1 * ( _time + position.x ) ) );

    final Matrix4d viewMatrix = _camera.getViewMatrix();

    _mesh.getProgram().bind();

    if ( !_printed )
    {
      Console.log( _mesh.getProgram().getDescriptor().toString() );
      _printed = true;
    }

    // ModelMatrix should be calculated in the simulation loop rather than render loop
    // but they are effectively the same in out app so we can just recalculate in render loop
    _modelMatrix.setTranslation( 0, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    final Matrix4d projectionMatrix = _viewport.getProjectionMatrix();

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( 3, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( -3, 0, -7 );
    _modelMatrix.rotateY( _angle );
    _modelMatrix.rotateX( 0.25 );

    _mesh.render( gl, _modelMatrix, viewMatrix, projectionMatrix, _light, _camera );

    _modelMatrix.setTranslation( position.x, position.y, position.z );
    _modelMatrix.scale( 0.2 );

    // These unbinds are not really necessary but adding until we get an easier to track framework
    _mesh.getProgram().unbind();

    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "modelMatrix" ) ).setValues( new Float32Array( _modelMatrix.toArray() ) );
    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "viewMatrix" ) ).setValues( new Float32Array( viewMatrix.toArray() ) );
    ( (Matrix4fvUniform) _lightMesh.getUniformByName( "projectionMatrix" ) )
      .setValues( new Float32Array( projectionMatrix.toArray() ) );
    ( (Vector3fvUniform) _lightMesh.getUniformByName( "color" ) )
      .setValues( new Float32Array( _light.getColor().toArray() ) );
    _lightMesh.render( gl );

    _angle += 0.01;
    _time += 0.1;
  }
}
