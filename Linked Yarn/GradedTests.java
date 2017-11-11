// package linked_yarn;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;

public class GradedTests {


    // =================================================
    // Test Configuration
    // =================================================

    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    // Used as the basic empty LinkedLinkedYarn to test; the @Before
    // method is run before every @Test
    LinkedYarn ball;
    @Before
    public void init () {
        ball = new LinkedYarn();
    }


    // =================================================
    // Unit Tests
    // =================================================
    // For grading purposes, every method has ~3 tests,
    // weighted equally and totaled for the score.
    // The tests increase in difficulty such that the
    // basics are unlabeled and harder tiers are tagged
    // t1, t2, t3, ...


    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testInit() {
        assertTrue(ball.isEmpty());
        assertEquals(0, ball.getSize());

    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testIsEmpty() {
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());

    }
    @Test
    public void testIsEmpty_t1() {
        ball.insert("a");
        ball.insert("a");
        ball.remove("a");
        ball.remove("a");
        assertTrue(ball.isEmpty());

    }
    @Test
    public void testIsEmpty_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("a");
        ball.removeAll("a");
        assertFalse(ball.isEmpty());
        ball.removeAll("b");
        assertTrue(ball.isEmpty());

    }

    @Test
    public void testGetSize() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getSize(), 2);
        ball.insert("unique");
        assertEquals(ball.getSize(), 3);

    }
    @Test
    public void testGetSize_t1() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getSize(), 3);

    }
    @Test
    public void testGetSize_t2() {
        ball.insert("u1");
        ball.insert("u2");
        ball.insert("u3");
        assertEquals(ball.getSize(), 3);

    }

    @Test
    public void testGetUniqueSize() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getUniqueSize(), 1);
        ball.insert("unique");
        assertEquals(ball.getUniqueSize(), 2);

    }
    public void testGetUniqueSize_t1() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getUniqueSize(), 1);

    }
    @Test
    public void testGetUniqueSize_t2() {
        ball.insert("u1");
        ball.insert("u2");
        ball.insert("u3");
        assertEquals(ball.getUniqueSize(), 3);

    }
    @Test
    public void testGetUniqueSize_t3() {
        ball.insert("u1");
        ball.insert("u2");
        ball.remove("u1");
        assertEquals(ball.getUniqueSize(), 1);
        ball.remove("u2");
        assertEquals(ball.getUniqueSize(), 0);

    }

    //  Manipulation Tests
    // -------------------------------------------------
    @Test
    public void testInsert() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));

    }
    @Test
    public void testInsert_t1() {
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("dup");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertEquals(ball.getSize(), 3);
        assertEquals(ball.getUniqueSize(), 2);

    }
    @Test
    public void testInsert_t2() {
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("dup");
        ball.remove("unique");
        assertTrue(ball.contains("dup"));
        assertFalse(ball.contains("unique"));
        assertEquals(ball.getSize(), 2);
        assertEquals(ball.getUniqueSize(), 1);

    }
    @Test
    public void testInsert_t3() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("dup2");
        ball.insert("dup3");
        ball.insert("dup3");
        ball.remove("dup2");
        assertEquals(ball.getSize(), 4);
        assertEquals(ball.getUniqueSize(), 2);

    }
    @Test
    public void testInsert_t4() {
        for (int i = 0; i < 105; i++) {
            ball.insert("" + i);
        }
        assertEquals(ball.getSize(), 105);
        assertEquals(ball.getUniqueSize(), 105);

    }
    @Test
    public void testInsert_t5() {
        for (int i = 0; i < 101; i++) {
            ball.insert("SAMESIES");
        }
        assertEquals(ball.getSize(), 101);
        assertEquals(ball.getUniqueSize(), 1);

    }

    @Test
    public void testRemove() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.getSize(), 2);
        assertEquals(ball.getUniqueSize(), 1);
        int dups = ball.remove("dup");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);
        assertEquals(dups, 1);

    }
    @Test
    public void testRemove_t1() {
        ball.insert("uni1");
        int dups = ball.remove("uni1");
        assertEquals(ball.getSize(), 0);
        assertEquals(dups, 0);
        assertFalse(ball.contains("uni1"));

    }
    @Test
    public void testRemove_t2() {
        ball.remove("uni1");
        ball.insert("uni1");
        ball.remove("uni");
        assertEquals(ball.getSize(), 1);
        assertTrue(ball.contains("uni1"));
        ball.insert("uni2");
        ball.insert("uni3");
        ball.remove("uni1");
        assertEquals(ball.getSize(), 2);
        assertFalse(ball.contains("uni1"));

    }
    @Test
    public void testRemove_t3() {
        ball.insert("dup1");
        ball.insert("dup1");
        ball.insert("dup2");
        ball.insert("dup2");
        ball.insert("uni1");
        ball.remove("uni1");
        assertEquals(ball.getSize(), 4);
        assertEquals(ball.getUniqueSize(), 2);
        ball.insert("uni2");
        ball.insert("uni3");
        int dups = ball.remove("dup1");
        assertEquals(dups, 1);
        dups = ball.remove("dup1");
        assertEquals(dups, 0);
        assertEquals(ball.getSize(), 4);
        assertEquals(ball.getUniqueSize(), 3);
        assertFalse(ball.contains("dup1"));

    }

    @Test
    public void testRemoveAll() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(ball.getSize(), 3);
        assertEquals(ball.getUniqueSize(), 2);
        ball.removeAll("dup");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);

    }
    @Test
    public void testRemoveAll_t1() {
        ball.removeAll("uni1");
        ball.insert("uni1");
        ball.removeAll("uni1");
        assertEquals(ball.getSize(), 0);
        assertFalse(ball.contains("uni1"));

    }
    @Test
    public void testRemoveAll_t2() {
        ball.insert("uni1");
        ball.insert("uni2");
        ball.insert("uni3");
        ball.removeAll("uni1");
        ball.removeAll("uni2");
        assertEquals(ball.getSize(), 1);
        assertFalse(ball.contains("uni1"));

    }
    @Test
    public void testRemoveAll_t3() {
        ball.insert("dup1");
        ball.insert("dup1");
        ball.insert("dup2");
        ball.insert("dup2");
        ball.insert("uni1");
        ball.removeAll("dup1");
        assertEquals(ball.getSize(), 3);
        assertEquals(ball.getUniqueSize(), 2);
        assertFalse(ball.contains("dup1"));
        ball.removeAll("dup2");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);
        ball.removeAll("uni1");
        assertEquals(ball.getSize(), 0);
        assertEquals(ball.getUniqueSize(), 0);

    }

    @Test
    public void testCount() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(ball.count("dup"), 2);
        assertEquals(ball.count("unique"), 1);
        assertEquals(ball.count("forneymon"), 0);
    }

    @Test
    public void testCount_t1() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(ball.count("dup"), 3);
        ball.removeAll("dup");
        assertEquals(ball.count("dup"), 0);
    }

    @Test
    public void testCount_t2() {
        ball.insert("dup");
        ball.insert("dup2");
        ball.insert("dup");
        ball.insert("dup2");
        assertEquals(ball.count("dup"), 2);
        assertEquals(ball.count("dup2"), 2);
        ball.removeAll("dup");
        assertEquals(ball.count("dup"), 0);
        ball.remove("dup2");
        assertEquals(ball.count("dup2"), 1);

    }

    @Test
    public void testContains() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));

    }
    // This is tested pretty much everywhere so...

    @Test
    public void testGetMostCommon() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals(ball.getMostCommon(), "dup");
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));

    }
    @Test
    public void testGetMostCommon_t1() {
        assertEquals(ball.getMostCommon(), null);

    }
    @Test
    public void testGetMostCommon_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("c");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("a") || mc.equals("b") || mc.equals("c"));

    }

    // Inter-LinkedYarn Tests
    // -------------------------------------------------
    @Test
    public void testClone() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        LinkedYarn dolly = ball.clone();
        assertEquals(dolly.count("dup"), 2);
        assertEquals(dolly.count("unique"), 1);
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));

    }
    @Test
    public void testClone_t1() {
        LinkedYarn dolly = ball.clone();
        ball.insert("a");
        assertFalse(dolly.contains("a"));

    }
    @Test
    public void testClone_t2() {
        ball.insert("a");
        LinkedYarn dolly = ball.clone();
        dolly.insert("b");
        LinkedYarn superDolly = dolly.clone();
        superDolly.insert("c");
        assertTrue(superDolly.contains("a"));
        assertTrue(superDolly.contains("b"));
        assertFalse(dolly.contains("c"));

    }

    @Test
    public void testSwap() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));

    }
    @Test
    public void testSwap_t1() {
        LinkedYarn y2 = new LinkedYarn();
        ball.insert("a");
        y2.swap(ball);
        assertTrue(ball.isEmpty());
        assertFalse(y2.isEmpty());

    }
    @Test
    public void testSwap_t2() {
        LinkedYarn y2 = new LinkedYarn();
        LinkedYarn y3 = new LinkedYarn();
        y2.insert("a");
        ball.insert("b");
        y2.swap(ball);
        y3.swap(y2);
        assertTrue(y2.isEmpty());
        assertTrue(ball.contains("a"));
        assertTrue(y3.contains("b"));
        ball.insert("c");
        assertFalse(y2.contains("c"));
    }
    @Test
    public void testSwap_t3() {
        ball.insert("a");
        ball.swap(ball);
        assertTrue(ball.contains("a"));
        assertEquals(ball.getSize(), 1);

    }

    // Static Method Tests
    // -------------------------------------------------
    @Test
    public void testKnit() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.knit(y1, y2);
        assertEquals(y3.count("dup"), 3);
        assertEquals(y3.count("unique"), 1);
        assertEquals(y3.count("cool"), 1);
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));

    }
    @Test
    public void testKnit_t1() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("a");
        y1.insert("b");
        LinkedYarn y3 = LinkedYarn.knit(ball, y1);
        assertEquals(y3.getSize(), 2);
        assertEquals(y3.getUniqueSize(), 2);

    }
    @Test
    public void testKnit_t2() {
        ball.insert("a");
        ball.insert("a");
        ball = LinkedYarn.knit(ball, ball);
        assertEquals(ball.getSize(), 4);
        assertEquals(ball.getUniqueSize(), 1);

    }

    @Test
    public void testTear() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.tear(y1, y2);
        assertEquals(y3.count("dup"), 1);
        assertEquals(y3.count("unique"), 1);
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));

    }
    @Test
    public void testTear_t1() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("a");
        y1.insert("b");
        LinkedYarn y2 = LinkedYarn.tear(ball, y1);
        assertEquals(y2.getSize(), 0);
        assertFalse(y2.contains("a"));

    }
    @Test
    public void testTear_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("a");
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("a");
        y1.insert("a");
        LinkedYarn y2 = LinkedYarn.tear(ball, y1);
        assertEquals(y2.getSize(), 1);
        assertFalse(y2.contains("a"));

    }

    @Test
    public void testSameLinkedYarn() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(LinkedYarn.sameYarn(y1, y2));
        assertTrue(LinkedYarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(LinkedYarn.sameYarn(y1, y2));

    }
    @Test
    public void testSameLinkedYarn_t1() {
        LinkedYarn y1 = new LinkedYarn();
        assertTrue(LinkedYarn.sameYarn(ball, y1));
        assertTrue(LinkedYarn.sameYarn(y1, y1));
        y1.insert("a");
        assertTrue(LinkedYarn.sameYarn(y1, y1));

    }
    @Test
    public void testSameLinkedYarn_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("b");
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("b");
        y1.insert("a");
        assertFalse(LinkedYarn.sameYarn(ball, y1));
        ball.removeAll("b");
        y1.removeAll("b");
        assertTrue(LinkedYarn.sameYarn(ball, y1));

    }

}
