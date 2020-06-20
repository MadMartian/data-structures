package com.extollit.collect;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RingBufferTests {
    private RingBuffer<Integer> buffer;

    @Before
    public void setup() {
        this.buffer = new RingBuffer<Integer>(7);
    }

    private void fillTo(int i) {
        for (int c = 0; c < i; ++c)
            this.buffer.append(c);
    }

    @Test
    public void perfectFill() {
        fillTo(7);
        bufferEquals(0, 1, 2, 3, 4, 5, 6);
    }

    @Test
    public void overfill() {
        fillTo(10);
        bufferEquals(3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void insertAtFront() {
        fillTo(3);
        this.buffer.add(1, 42);

        bufferEquals(0, 42, 1, 2);
    }

    @Test
    public void fullInsertAtBack() {
        fillTo(10);
        this.buffer.add(1, 42);

        bufferEquals(4, 42, 5, 6, 7, 8, 9);
    }

    @Test
    public void fullInsertAtFront() {
        fillTo(10);
        this.buffer.add(5, 42);

        bufferEquals(4, 5, 6, 7, 42, 8, 9);
    }

    @Test
    public void fullInsertAtPivot() {
        fillTo(10);

        this.buffer.add(6, 42);

        bufferEquals(4, 5, 6, 7, 8, 42, 9);
    }

    @Test
    public void addAll() {
        fillTo(4);

        this.buffer.addAll(2, Arrays.asList(42, 29));

        bufferEquals(0, 1, 42, 29, 2, 3);
    }

    @Test
    public void addAllNoTrunc() {
        fillTo(3);

        this.buffer.addAll(1, Arrays.asList(42, 29));

        bufferEquals(0, 42, 29, 1, 2);
    }

    @Test
    public void addAllOverfill() {
        fillTo(10);

        this.buffer.addAll(3, Arrays.asList(42, 29));

        bufferEquals(5, 42, 29, 6, 7, 8, 9);
    }

    @Test
    public void addAllOverflow() {
        fillTo(4);

        this.buffer.addAll(3, Arrays.asList(42, 29, 69, 23, 81, 256, 512, 72));

        bufferEquals(69, 23, 81, 256, 512, 72, 3);
    }

    @Test
    public void addAllOvernifelheim() {
        fillTo(10);

        this.buffer.addAll(6, Arrays.asList(42, 29, 69, 23, 81, 256, 512, 72));

        bufferEquals(69, 23, 81, 256, 512, 72, 9);
    }

    @Test
    public void removeBack() {
        fillTo(10);

        assertTrue(this.buffer.remove(new Integer(5)));

        bufferEquals(3, 4, 6, 7, 8, 9);
    }

    @Test
    public void removeFront() {
        fillTo(10);

        assertTrue(this.buffer.remove(new Integer(8)));

        bufferEquals(3, 4, 5, 6, 7, 9);
    }

    @Test
    public void remove() {
        fillTo(3);

        assertTrue(this.buffer.remove(new Integer(1)));

        bufferEquals(0, 2);
    }

    @Test
    public void removeEnd() {
        fillTo(3);

        assertTrue(this.buffer.remove(new Integer(2)));

        bufferEquals(0, 1);
    }

    @Test
    public void removeBegin() {
        fillTo(3);

        assertTrue(this.buffer.remove(new Integer(0)));

        bufferEquals(1, 2);
    }

    @Test
    public void containsAll() {
        fillTo(5);

        assertTrue(this.buffer.containsAll(Arrays.asList(4, 3, 2, 1)));
    }

    @Test
    public void containsAllOverfill() {
        fillTo(10);

        assertTrue(this.buffer.containsAll(Arrays.asList(4, 5, 7, 8)));
    }

    @Test
    public void removeAll() {
        fillTo(4);

        assertTrue(this.buffer.removeAll(Arrays.asList(2, 1)));

        bufferEquals(0, 3);
    }

    @Test
    public void removeAllOverfill() {
        fillTo(10);

        assertTrue(this.buffer.removeAll(Arrays.asList(4, 8, 5)));

        bufferEquals(3, 6, 7, 9);
    }

    @Test
    public void retainAll() {
        fillTo(5);

        assertTrue(this.buffer.retainAll(Arrays.asList(2, 4)));

        bufferEquals(2, 4);
    }

    @Test
    public void retainAllOverfill() {
        fillTo(10);

        assertTrue(this.buffer.retainAll(Arrays.asList(4, 7)));

        bufferEquals(4, 7);
    }

    @Test
    public void indexOf() {
        fillTo(3);

        assertEquals(1, this.buffer.indexOf(1));
    }

    @Test
    public void indexOfFront() {
        fillTo(10);

        assertEquals(4, this.buffer.indexOf(7));
    }

    @Test
    public void indexOfBack() {
        fillTo(10);

        assertEquals(1, this.buffer.indexOf(4));
    }

    @Test
    public void clear() {
        fillTo(3);

        this.buffer.clear();

        bufferEquals();
    }

    @Test
    public void clearOverfill() {
        fillTo(10);

        this.buffer.clear();

        bufferEquals();
    }

    @Test
    public void get() {
        fillTo(4);

        assertEquals(new Integer(2), this.buffer.get(2));
    }

    @Test
    public void getFront() {
        fillTo(10);

        assertEquals(new Integer(8), this.buffer.get(5));
    }

    @Test
    public void getBack() {
        fillTo(10);

        assertEquals(new Integer(5), this.buffer.get(2));
    }

    @Test
    public void set() {
        fillTo(4);

        assertEquals(new Integer(2), this.buffer.set(2, 69));
        assertEquals(new Integer(69), this.buffer.get(2));
    }

    @Test
    public void setFront() {
        fillTo(10);

        assertEquals(new Integer(8), this.buffer.set(5, 69));
        assertEquals(new Integer(69), this.buffer.get(5));
    }

    @Test
    public void setBack() {
        fillTo(10);

        assertEquals(new Integer(5), this.buffer.set(2, 69));
        assertEquals(new Integer(69), this.buffer.get(2));
    }

    @Test
    public void successiveRemoves() {
        fillTo(5);

        assertEquals(new Integer(2), this.buffer.remove(2));
        assertEquals(new Integer(1), this.buffer.remove(1));
        assertEquals(new Integer(4), this.buffer.remove(2));
        bufferEquals(0, 3);
    }

    @Test
    public void successiveRemovesOverfill() {
        fillTo(10);

        assertEquals(new Integer(8), this.buffer.remove(5));
        assertEquals(new Integer(5), this.buffer.remove(2));
        assertEquals(new Integer(9), this.buffer.remove(4));
        bufferEquals(3, 4, 6, 7);
    }

    private void bufferEquals(Integer... ints) {
        assertArrayEquals(ints, this.buffer.toArray());
    }
}
