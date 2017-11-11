// package yarn;

import static org.junit.Assert.*;
import org.junit.Test;

public class YarnTests {

	@Test
	public void testYarn() {
		Yarn ball = new Yarn();
	}

	@Test
	public void testIsEmpty() {
		Yarn ball = new Yarn();
		assertTrue(ball.isEmpty());
		ball.insert("not_empty");
		assertFalse(ball.isEmpty());
		ball.remove("not empty");
		assertTrue(ball.isEmpty());
	}

	@Test
	public void testGetSize() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		assertEquals(ball.getSize(), 2);
		ball.insert("unique");
		assertEquals(ball.getSize(), 3);
		ball.remove("unique");
		assertEquals(ball.getSize(), 2);
		ball.remove("dup");
		assertEquals(ball.getSize(), 1);
	}

	@Test
	public void testGetUniqueSize() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		assertEquals(ball.getUniqueSize(), 2);

		Yarn tree = new Yarn();
		tree.insert("dup");
		tree.insert("dup");
		tree.insert("soup");
		assertEquals(tree.getUniqueSize(), 2);
		tree.remove("soup");
		assertEquals(tree.getUniqueSize(), 1);
		tree.insert("Fake soup");
		Yarn knitted = Yarn.knit(ball, tree);
		assertEquals(knitted.getUniqueSize(), 3);

	}

	@Test
	public void testInsert() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		assertTrue(ball.contains("dup"));
		assertTrue(ball.contains("unique"));
		ball.insert("");
		assertTrue(ball.contains(""));
	}

	@Test
	public void testRemove() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		assertEquals(ball.getSize(), 2);
		assertEquals(ball.getUniqueSize(), 1);
		ball.remove("dup");
		assertEquals(ball.getSize(), 1);
		assertEquals(ball.getUniqueSize(), 1);
		ball.insert("heyyy");
		ball.remove("heyyy");
		ball.remove("heyyy"); //Should to ensure no errors would occur if .remove() is used for a String not in the Entry[].
	}

	@Test
	public void testRemoveAll() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		assertEquals(ball.getSize(), 3);
		assertEquals(ball.getUniqueSize(), 2);
		ball.removeAll("dup");
		assertEquals(ball.getSize(), 1);
		assertEquals(ball.getUniqueSize(), 1);
		ball.removeAll("unique");
		assertEquals(ball.getSize(), 0);
		assertEquals(ball.getUniqueSize(), 0);
	}

	@Test
	public void testCount() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		assertEquals(ball.count("dup"), 2);
		assertEquals(ball.count("unique"), 1);
		assertEquals(ball.count("forneymon"), 0);
		ball.insert("unique");
		ball.insert("unique");
		ball.insert("unique");
		assertEquals(ball.count("unique"), 4);
		ball.remove("unique");
		assertEquals(ball.count("unique"), 3);
	}

	@Test
	public void testContains() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		assertTrue(ball.contains("dup"));
		assertTrue(ball.contains("unique"));
		assertFalse(ball.contains("forneymon"));
		ball.remove("unique");
		assertFalse(ball.contains("unique"));
	}

	@Test
	public void testGetNth() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		ball.insert("cool");
		assertEquals(ball.getNth(3), "cool");
		ball.remove("dup");
		assertEquals(ball.getNth(0), "dup");
		assertEquals(ball.getNth(2), "cool");
	}

	@Test
	public void testGetMostCommon() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		ball.insert("cool");
		assertEquals(ball.getMostCommon(), "dup");
		ball.insert("cool");
		String mc = ball.getMostCommon();
		assertTrue(mc.equals("dup") || mc.equals("cool"));
		ball.remove("dup");
		assertEquals(ball.getMostCommon(), "cool");
	}

	@Test
	public void testClone() {
		Yarn ball = new Yarn();
		ball.insert("dup");
		ball.insert("dup");
		ball.insert("unique");
		Yarn dolly = ball.clone();
		assertEquals(dolly.count("dup"), 2);
		assertEquals(dolly.count("unique"), 1);
		dolly.insert("cool");
		assertFalse(ball.contains("cool"));
	}

	@Test
	public void testSwap() {
		Yarn y1 = new Yarn();
		y1.insert("dup");
		y1.insert("dup");
		y1.insert("unique");
		Yarn y2 = new Yarn();
		y2.insert("yo");
		y2.insert("sup");
		y1.swap(y2);
		assertTrue(y1.contains("yo"));
		assertTrue(y1.contains("sup"));
		assertTrue(y2.contains("dup"));
		assertTrue(y2.contains("unique"));
		assertFalse(y1.contains("dup"));
		y2 = Yarn.knit(y1, y2);
		y2.swap(y1);
		assertEquals(y1.getSize(), 5);

	}

	@Test
	public void testKnit() {
		Yarn y1 = new Yarn();
		y1.insert("dup");
		y1.insert("dup");
		y1.insert("unique");
		Yarn y2 = new Yarn();
		y2.insert("dup");
		y2.insert("cool");
		Yarn y3 = Yarn.knit(y1, y2);
		assertEquals(y3.count("dup"), 3);
		assertEquals(y3.count("unique"), 1);
		assertEquals(y3.count("cool"), 1);
		y3.insert("test");
		assertFalse(y1.contains("test"));
		assertFalse(y2.contains("test"));
	}

	@Test
	public void testTear() {
		Yarn y1 = new Yarn();
		y1.insert("dup");
		y1.insert("dup");
		y1.insert("unique");
		Yarn y2 = new Yarn();
		y2.insert("dup");
		y2.insert("cool");
		Yarn y3 = Yarn.tear(y1, y2);
		assertEquals(y3.count("dup"), 1);
		assertEquals(y3.count("unique"), 1);
		assertFalse(y3.contains("cool"));
		y3.insert("test");
		assertFalse(y1.contains("test"));
		assertFalse(y2.contains("test"));
	}

	@Test
	public void testSameYarn() {
		Yarn y1 = new Yarn();
		y1.insert("dup");
		y1.insert("dup");
		y1.insert("unique");
		Yarn y2 = new Yarn();
		y2.insert("unique");
		y2.insert("dup");
		y2.insert("dup");
		assertTrue(Yarn.sameYarn(y1, y2));
		assertTrue(Yarn.sameYarn(y2, y1));
		y2.insert("test");
		assertFalse(Yarn.sameYarn(y1, y2));
		y2.remove("test");
		assertTrue(Yarn.sameYarn(y1, y2));
	}

}
