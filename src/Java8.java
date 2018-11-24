import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Java8 {
  public static void totalSelectedValues(List<Integer> values,
    Predicate<Integer> selector) {

    values.stream()
      .filter(selector)
      .map(e->e*e)
      .forEach(System.out::println);

    values.stream()
      .filter(isGreaterThan.apply(5))
      .map(e->e*e)
      .forEach(System.out::println);
  }

  public static void main(String[] args) {
    totalSelectedValues(Arrays.asList(1, 2, 3,6,4), get());
  }

  public static Predicate<Integer> get(){
    Predicate<Integer> predicate= (e) -> e % 2 == 0;
    Predicate<Integer> predicate1= (e) -> e % 3 == 0;
    Predicate<Integer> predicate2= (e) -> e % 4 == 0;
    return predicate.and(predicate1).or(predicate2);
  }
  //输入integer，输出Predicate<Integer>
  static Function<Integer, Predicate<Integer>> isGreaterThan = pivot -> candidate -> candidate > pivot;
}
