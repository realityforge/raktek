package raktek;

import grim.annotations.OmitSymbol;

@SuppressWarnings( "PointlessBooleanExpression" )
public final class Raktek
{
  private Raktek()
  {
  }

  /**
   * Return true if should check whether the shader compile was successful during allocation phase.
   *
   * @return true if should check whether the shader compile was successful during allocation phase.
   */
  @OmitSymbol
  public static boolean shouldCheckShaderCompileAtAllocate()
  {
    return shouldCheckShaderCompile() && true;
  }

  /**
   * Return true if should check whether the shader compile was successful or not.
   *
   * @return true if should check whether the shader compile was successful or not.
   */
  @OmitSymbol
  public static boolean shouldCheckShaderCompile()
  {
    return true;
  }
}
