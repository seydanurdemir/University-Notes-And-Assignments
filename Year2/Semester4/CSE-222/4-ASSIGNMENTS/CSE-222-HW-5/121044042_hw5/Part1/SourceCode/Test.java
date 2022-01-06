/**
 * Tests implemented MapIterator
 *
 * @author Seyda Nur DEMIR
 * @version 2.0
 * @since 2021-05-13
 */
public class Test {

	/**
	 * Tests map iterator with int-string pairs.
	 *
	 */
	public void testMapIterator() {
		MapIterator M = new MapIterator();
		M.add(0, "Value1");
		M.add(1, "Value2");
		M.add(2, "Value3");
		M.add(2, "Value4");
		M.add(2, "Value5");
		M.add(2, "Value6");
		M.add(2, "Value7");
		M.add(2, "Value8");
		M.add(2, "Value9");
		M.add(2, "Value10");

		while (M.hasNext()) {
			EntryNode item = M.next();
			System.out.println(item.key + ":" + item.value);
		}
	}
}
