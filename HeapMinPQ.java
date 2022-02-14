package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

   @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        pq.add(new PriorityNode<T>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        PriorityNode<T> node = new PriorityNode<T>(item, 0);
        return pq.contains(node);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        assert pq.peek() != null;
        return pq.peek().item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> removed;
        removed = pq.poll();
        assert removed != null;
        return removed.item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        PriorityQueue<PriorityNode<T>> pq1 = pq;
        PriorityQueue<PriorityNode<T>> pq2 = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
        while (!pq1.isEmpty()) {
            PriorityNode<T> index = pq.poll();
            assert index != null;
            if(index.item() == item) {
                index.setPriority(priority);
            }
            pq2.add(index);
        }
        pq = pq2;
    }

    @Override
    public int size() {
        return pq.size();
    }
}
