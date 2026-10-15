package dsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DSUTest {

    @Test
    void initiallyElementsAreSeparate() {
        DSU dsu = new DSU(5);

        assertNotEquals(
                dsu.find(0),
                dsu.find(1)
        );
    }

    @Test
    void unionConnectsTwoElements() {
        DSU dsu = new DSU(5);

        assertTrue(dsu.union(0, 1));

        assertTrue(dsu.connected(0, 1));
    }

    @Test
    void repeatedUnionDoesNotCreateNewConnection() {
        DSU dsu = new DSU(5);

        assertTrue(dsu.union(0, 1));
        assertFalse(dsu.union(0, 1));
    }

    @Test
    void transitiveConnectionWorks() {
        DSU dsu = new DSU(5);

        dsu.union(0, 1);
        dsu.union(1, 2);

        assertTrue(dsu.connected(0, 2));
    }
}