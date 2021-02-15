package raktek;

import grim.annotations.OmitSymbol;

@SuppressWarnings( "PointlessBooleanExpression" )
public final class Raktek
{
  private Raktek()
  {
  }

  /**
   * Return true if toString() on various resources will emit a human understandable string.
   * This comes at a cors of increased code-size and is not recommended in production builds.
   *
   * @return true if toString() on various resources will emit a human understandable string.
   */
  @OmitSymbol
  public static boolean shouldSupportDebugToString()
  {
    return true;
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

  /**
   * Return true if should check whether the program link was successful during allocation phase.
   *
   * @return true if should check whether the program link was successful during allocation phase.
   */
  @OmitSymbol
  public static boolean shouldCheckSProgramLinkAtAllocate()
  {
    return shouldCheckProgramLink() && true;
  }

  /**
   * Return true if should check whether the program link was successful or not.
   *
   * @return true if should check whether the program link was successful or not.
   */
  @OmitSymbol
  public static boolean shouldCheckProgramLink()
  {
    return true;
  }
}
