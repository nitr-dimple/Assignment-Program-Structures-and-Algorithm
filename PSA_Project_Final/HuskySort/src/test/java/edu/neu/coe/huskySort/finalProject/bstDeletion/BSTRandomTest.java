package edu.neu.coe.huskySort.finalProject.bstDeletion;

import edu.neu.coe.huskySort.util.PrivateMethodTester;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class BSTRandomTest {
    @Test
    public void testSetRoot1() throws Exception {
        BST<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        System.out.println(bst);
    }

    @Test
    public void testSetRoot2() throws Exception {
        BST<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node nodeX = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTRandomDeletion.Node nodeY = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTRandomDeletion.Node nodeZ = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        System.out.println(bst);
    }

    @Test
    public void testPut0() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        assertEquals(0, bst.size());
        bst.put("X", 42);
        assertEquals(1, bst.size());
    }

    @Test
    public void testPut1() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 99);
        BSTRandomDeletion.Node root = (BSTRandomDeletion.Node) tester.invokePrivate("getRoot");
        assertEquals("X", root.key);
        assertEquals("Y", root.larger.key);
        assertNull(root.smaller);
        assertEquals(2, bst.size());
    }

    @Test
    public void testPut2() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        BSTRandomDeletion.Node root = (BSTRandomDeletion.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertEquals("X", root.smaller.key);
        assertEquals("Z", root.larger.key);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPut3() throws Exception {
        BstDetail<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        bst.put("Y", 42);
        BSTRandomDeletion.Node root = (BSTRandomDeletion.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertNull(root.smaller);
        assertNull(root.larger);
        bst.put("X", 99);
        assertEquals("X", root.smaller.key);
        bst.put("Z", 37);
        assertEquals("Z", root.larger.key);
        System.out.println(bst.toString());
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutN() throws Exception {
        BstDetail<String, Integer> bst = new BSTRandomDeletion<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        System.out.println(bst);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutAll() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 3);
        map.put("Goodbye", 5);
        map.put("Ciao", 6);
        BstDetail<String, Integer> bst = new BSTRandomDeletion<>();
        bst.putAll(map);
        System.out.println(bst);
        assertEquals(map.size(), bst.size());
    }

    @Test
    public void testDelete1() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.delete("X");
        assertNull(bst.root);
        assertEquals(0, bst.size());
    }

    @Test
    public void testDelete2() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y",57);
        assertNotNull(bst.root.larger);
        bst.delete("Y");
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
        assertEquals(1, bst.size(node));
    }

    @Test
    public void testDelete3() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("W");
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
        assertEquals(1, bst.size(bst.root));
    }

    @Test
    public void testDelete4() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node node = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("A");
        assertEquals(2, bst.size(bst.root));
    }

    @Test
    public void testSize1() {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        for (int i = 0; i < 100; i++) bst.put(Integer.toString(i), i);
        assertEquals(100, bst.size());
    }

    @Test
    public void testSize2() {
        Random random = new Random(0L);
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        for (int i = 0; i < 100; i++) bst.put(Integer.toString(random.nextInt(200)), i);
        assertEquals(79, bst.size());
    }

    @Test
    public void testDepthKey1() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node nodeX = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTRandomDeletion.Node nodeY = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTRandomDeletion.Node nodeZ = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(1, bst.depth("X"));
        assertEquals(0, bst.depth("Y"));
        assertEquals(1, bst.depth("Z"));
        assertEquals(-1, bst.depth("A"));
    }

    @Test
    public void testDepthKey2() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(0, bst.depth("Hello"));
        assertEquals(1, bst.depth("Goodbye"));
        assertEquals(2, bst.depth("Ciao"));
    }

    @Test
    public void testDepth1() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        PrivateMethodTester tester = new PrivateMethodTester(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BSTRandomDeletion.Node nodeX = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BSTRandomDeletion.Node nodeY = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BSTRandomDeletion.Node nodeZ = (BSTRandomDeletion.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(2, bst.depth());
    }

    @Test
    public void testDepth2() throws Exception {
        BSTRandomDeletion<String, Integer> bst = new BSTRandomDeletion<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(3, bst.depth());
    }
}
