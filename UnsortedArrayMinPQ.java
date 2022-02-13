package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        items.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        PriorityNode<T> node = new PriorityNode(item, 0);
        return items.contains(node);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = items.get(0).priority();
        PriorityNode<T> node = items.get(0);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).priority() < minPriority) {
                minPriority = items.get(i).priority();
                node = items.get(i);
            }
        }
        return node.item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double minPriority = items.get(0).priority();
        PriorityNode<T> node = items.get(0);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).priority() < minPriority) {
                minPriority = items.get(i).priority();
                node = items.get(i);
            }
        }
        items.remove(node);
        return node.item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = items.indexOf(item);
        PriorityNode<T> node = new PriorityNode<>(item, priority);
        items.set(index, node);
    }

    @Override
    public int size() {
        return items.size();
    }
}
