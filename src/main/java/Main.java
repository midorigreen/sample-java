import com.google.common.collect.Lists;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by midori on 2017/02/15.
 */
public class Main {

	public static void main(String[] args) {
		List<Integer> listInt = Lists.newArrayList(
				1, 2, 3, 4, 5, 6, 7, 8, 9, 10
		);
		List<String> listStr = Lists.newArrayList(
				"1", "2", "3", "4", "5"
		);

		Predicate<Integer> biggerThanZero = x -> x > 0;
		Predicate<Integer> biggerThanFive = x -> x > 5;

		/**
		 * allMatch(Predicate<T>)
 		 */
		System.out.println(
				listInt.stream().allMatch(biggerThanZero) //true
		);
		System.out.println(
				listInt.stream().allMatch(biggerThanFive) //false
		);

		/**
		 * anyMatch(Predicate<T>)
 		 */
		System.out.println(
				listInt.stream().anyMatch(x -> x > 3) // true
		);

		/**
		 * collect(Collector<? super T,A,R> collector)
		 */
		System.out.println(
				listStr.stream().collect(Collectors.joining()) // 12345
		);
		System.out.println(
				listStr.stream().collect(new CommaJoiner()) // 1.2.3.4.5.
		);

	}

	private static class CommaJoiner implements Collector<String, StringBuilder, String> {

		@Override
		public Supplier<StringBuilder> supplier() {
			return StringBuilder::new;
		}

		@Override
		public BiConsumer<StringBuilder, String> accumulator() {
			return (sb, s) -> {
				sb.append(s);
				sb.append(".");
			};
		}

		@Override
		public BinaryOperator<StringBuilder> combiner() {
			return (sb1, sb2) -> {
				sb1.append(sb2);
				return sb1;
			};
		}

		@Override
		public Function<StringBuilder, String> finisher() {
			return sb -> sb.toString();
		}

		@Override
		public Set<Characteristics> characteristics() {
			return EnumSet.noneOf(Characteristics.class);
		}
	}
}
