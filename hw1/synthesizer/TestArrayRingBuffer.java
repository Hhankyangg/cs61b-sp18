package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {

    public ArrayRingBuffer makeArrayRingBuffer() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        return arb;
    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer<Integer> arb = makeArrayRingBuffer();
        arb.enqueue(4);
        assertEquals(5, arb.fillCount());
        try {
            arb.enqueue(5);
        } catch (RuntimeException aRuntimeException) {
            assertEquals("Ring buffer overflow", aRuntimeException.getMessage());
        }
    }

    @Test
    public void testDequeue() {
        ArrayRingBuffer<Integer> arb = makeArrayRingBuffer();
        assertEquals(0, (int) arb.dequeue());
        assertEquals(3, arb.fillCount());
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        assertEquals(0, arb.fillCount());
        try {
            arb.dequeue();
        } catch (RuntimeException aRuntimeException) {
            assertEquals("Ring buffer underflow", aRuntimeException.getMessage());
        }
    }

    @Test
    public void testPeek() {
        ArrayRingBuffer<Integer> arb = makeArrayRingBuffer();
        assertEquals(0, (int) arb.peek());
        assertEquals(4, arb.fillCount());
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = makeArrayRingBuffer();
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        assertTrue(arb.isFull());
        Integer[] expected = {2, 3, 4, 5, 6};
        int i = 0;
        for (Integer num : arb) {
            assertEquals(expected[i], num);
            i += 1;
        }
    }
} 
