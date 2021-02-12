package raktek;

import grim.annotations.OmitSymbol;

public final class Raktek
{
  private Raktek()
  {
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
