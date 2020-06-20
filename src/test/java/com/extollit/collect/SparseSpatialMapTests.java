package com.extollit.collect;

import com.extollit.linalg.immutable.IntAxisAlignedBox;
import com.extollit.linalg.immutable.Vec3i;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.*;

public class SparseSpatialMapTests {
    private SparseSpatialMap<String> map;

    @Before
    public void setup() {
        this.map = new SparseSpatialMap<String>(3);
    }

    @Test
    public void cull1() {
        map.put(new Vec3i(10, 10, 1), "alpha");
        map.put(new Vec3i(10, 10, 120), "beta");
        map.put(new Vec3i(10, 10, 70), "gamma");
        map.put(new Vec3i(50, 70, 10), "delta");
        map.put(new Vec3i(10, 60, 70), "epsilon");
        map.put(new Vec3i(70, 80, 90), "kappa");
        map.put(new Vec3i(50, 90, 80), "lambda");
        map.put(new Vec3i(80, 60, 60), "mu");

        final IntAxisAlignedBox box = new IntAxisAlignedBox(50, 50, 50, 100, 100, 100);

        this.map.cullOutside(box);

        assertEquals(3, map.size());
        final Set<String> values = new HashSet<String>(map.values());

        assertEquals(new HashSet<String>(Arrays.asList("kappa", "lambda", "mu")), values);
    }

    @Test
    public void cull2() {
        map.put(new Vec3i(20, 10, 11), "alpha");
        map.put(new Vec3i(50, 90, 10), "beta");
        map.put(new Vec3i(90, 70, 50), "gamma");
        map.put(new Vec3i(70, 50, 90), "delta");
        map.put(new Vec3i(50, 20, 80), "epsilon");
        map.put(new Vec3i(20, 50, 40), "kappa");
        map.put(new Vec3i(80, 70, 20), "lambda");
        map.put(new Vec3i(10, 10, 70), "mu");

        final IntAxisAlignedBox box = new IntAxisAlignedBox(40, 20, 10, 80, 70, 90);

        this.map.cullOutside(box);

        assertEquals(3, map.size());
        final Set<String> values = new HashSet<String>(map.values());

        assertEquals(new HashSet<String>(Arrays.asList("delta", "epsilon", "lambda")), values);
    }

    @Test
    public void cullIntegrity() {
        final Vec3i key = new Vec3i(50, 90, 10);
        map.put(key, "alpha");
        assertNotNull(map.get(key));

        final IntAxisAlignedBox box = new IntAxisAlignedBox(40, 20, 10, 60, 80, 90);

        this.map.cullOutside(box);
        assertNull(map.get(key));
    }

    @Test
    public void putGetPutRemoveGet() {
        final Vec3i key = new Vec3i(50, 90, 10);
        assertNull(map.put(key, "alpha"));
        assertEquals("alpha", map.get(key));
        assertEquals("alpha", map.put(key, "beta"));
        assertEquals("beta", map.remove(key));
        assertNull(map.get(key));
    }
    @Test
    public void interlacedPutGetPutRemoveGet() {
        final Vec3i
                key1 = new Vec3i(50, 90, 10),
                key2 = new Vec3i(10, 250, 7);

        assertNull(map.put(key1, "alpha"));
        assertNull(map.put(key2, "beta"));

        assertEquals("alpha", map.get(key1));
        assertEquals("beta", map.get(key2));

        assertEquals("alpha", map.put(key1, "gamma"));
        assertEquals("beta", map.put(key2, "delta"));

        assertEquals("gamma", map.remove(key1));
        assertEquals("delta", map.remove(key2));

        assertNull(map.get(key1));
        assertNull(map.get(key2));
    }
}
