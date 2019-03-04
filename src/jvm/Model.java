package jvm;

import java.util.Objects;

public class Model {
  private int anInt;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Model model = (Model) o;
    return anInt == model.anInt &&
      bnInt == model.bnInt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(anInt, bnInt);
  }

  private int bnInt;

}
