package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    private int size;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        items.add(null);
        size = 0;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        size++;
        items.add(new PriorityNode<>(item, priority));
        swim(size);
        itemToIndex.put(item, size);
    }

    @Override
    public boolean contains(T item) {
        for (int i = 1; i <= size; i++) {
            if(items.get(i).equals(new PriorityNode<>(item, 0))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(1).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T min = peekMin();
        swap(1, size);
        items.remove(size);
        size--;
        itemToIndex.remove(min);
        sink(1);
        return min;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = items.indexOf(new PriorityNode<>(item, 0));
        items.get(index).setPriority(priority);
        swim(index);
        sink(index);
        int index1 = items.indexOf(new PriorityNode<>(item, 0));
        itemToIndex.put(item, index1);
    }

    @Override
    public int size() {
        return size;
    }

    private void swim(int index) {
        while (index > 1) {
            int parentIndex = index / 2;
            if (needSwap(parentIndex, index)) {
                swap(parentIndex, index);
            }
            index = parentIndex;
        }
    }

    private boolean needSwap(int i, int p) {
        boolean condition1 = 0 < i && i <= size;
        boolean condition2 = 0 < p && p <= size;
        if (condition1 || condition2) {
            return condition1;
        } else if (condition1 && condition2) {
            return items.get(i).priority() > items.get(p).priority();
        } else {
            return false;
        }
    }

    private void swap(int i, int p) {
        itemToIndex.put(items.get(i).item(), p);
        itemToIndex.put(items.get(p).item(), i);
        PriorityNode current = items.get(i);
        items.set(i, items.get(p));
        items.set(p, current);
    }

    private void sink(int index) {
        int indicator = 1 * index;
        while (indicator <= size) {
            int left = indicator;
            int right = left + 1;
            int current = left;
            if (right <= size && needSwap(left, right)) {
                current = right;
            }
            if (needSwap(index, current)) {
                swap(index, current);
            }
            index = current;
        }
    }
}
