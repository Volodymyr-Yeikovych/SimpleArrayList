package org.example;

import java.util.*;
import java.util.function.Consumer;

public class SimpleArrayList<E> implements List<E> {

    private static final Object EMPTY_ELEMENT = null;

    private Object[] elements;
    private int size = 0;
    private int capacity;

    public SimpleArrayList() {
        elements = new Object[10];
        capacity = 10;
    }

    public SimpleArrayList(int capacity) {
        elements = new Object[capacity];
        this.capacity = capacity;
    }

    public SimpleArrayList(List<E> other) {
        capacity = other.size();
        elements = new Object[capacity];
        this.addAll(other);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return Arrays.stream(Arrays.copyOf(elements, size)).anyMatch(element -> element.equals(o));
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    @Deprecated
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    @Deprecated
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (size == capacity) {
            expand();
        }
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!this.contains(o)) return false;
        for (int i = 0; i < capacity; i++) {
            if (elements[i] == o) {
                elements[i] = EMPTY_ELEMENT;
                trim(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) return false;
        if (c.size() != this.size) return false;
        Object[] elementData = Arrays.copyOf(elements, size);
        for (Object o : elementData) {
            if (!c.contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) return false;

        int spaceLeft = capacity - size;
        int minCap = c.size();

        if (spaceLeft < minCap) {
            int minExpand = (int) (minCap * 1.5);
            expand(minExpand);
        }

        for (E e : c) {
            this.add(e);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) return false;
        checkIndexOutOfBounds(index);

        int i = index;
        int minSize = size + c.size();
        if (minSize >= capacity) {
            int minGrowth = (int) (minSize * 1.5);
            expand(minGrowth);
        }

        Object[] shifted = shiftRight(index, c.size());

        for (E e : c) {
            shifted[i] = e;
            i++;
            size++;
        }

        elements = shifted;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) return false;
        Object[] elementData = Arrays.copyOf(elements, size);
        for (Object o : elementData) {
            if (c.contains(o)) this.remove(o);
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        elements = new Object[capacity];
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndexOutOfBounds(index);
        return elementData(index);
    }

    @Override
    public E set(int index, E element) {
        Objects.requireNonNull(element);
        checkIndexOutOfBounds(index);
        E previous = elementData(index);
        elements[index] = element;
        return previous;
    }

    @Override
    public void add(int index, E element) {
        Objects.requireNonNull(element);
        checkIndexOutOfBounds(index);

        if (size + 1 == capacity) {
            expand();
        }

        Object[] shifted = shiftRight(index, 1);
        shifted[index] = element;
        elements = shifted;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndexOutOfBounds(index);
        E previous = elementData(index);
        elements[index] = EMPTY_ELEMENT;
        trim(index);
        size--;
        return previous;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == o) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i] == o) return i;
        }
        return -1;
    }

    @Override
    @Deprecated
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    @Deprecated
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndexOutOfBounds(fromIndex);
        checkIndexOutOfBounds(toIndex);

        int subListSize = toIndex - fromIndex;
        SimpleArrayList<E> subList = new SimpleArrayList<>(subListSize);

        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(elementData(i));
        }

        return subList;
    }

    @Override
    public String toString() {
        Iterator<E> itr = iterator();
        if (!itr.hasNext()) return "[]";

        StringBuilder builder = new StringBuilder("[");
        while (itr.hasNext()) {
            builder.append(itr.next());
            if (itr.hasNext()) builder.append(", ");
        }
        return builder.append("]").toString();
    }

    private void expand() {
        int defaultGrow = (int) (capacity * 1.5);
        expand(defaultGrow);
    }

    private void expand(int grow) {
        int newCapacity = capacity + grow;
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
        capacity = newCapacity;
    }

    private Object[] shiftRight(int indexFrom, int elementsToShift) {
        Object[] left = Arrays.copyOfRange(elements, 0, indexFrom);
        Object[] right = Arrays.copyOfRange(elements, indexFrom, size);

        Object[] newElements = new Object[capacity];
        System.arraycopy(left, 0, newElements, 0, left.length);
        System.arraycopy(right, 0, newElements, left.length + elementsToShift, right.length);
        return newElements;
    }

    private void trim(int index) {
        Object[] right = Arrays.copyOfRange(elements, index + 1, elements.length);

        System.arraycopy(right, 0, elements, index, right.length);
        elements[size] = EMPTY_ELEMENT;
    }

    private void checkIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of size{" + size + "}. Given index{" + index + "}");
        }
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elements[index];
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();

            cursor = i + 1;
            lastRet = i;
            return elementData(i);
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                SimpleArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = SimpleArrayList.this.size;
            int i = cursor;
            if (i < size) {
                lastRet = i - 1;
            }
        }

    }
}
