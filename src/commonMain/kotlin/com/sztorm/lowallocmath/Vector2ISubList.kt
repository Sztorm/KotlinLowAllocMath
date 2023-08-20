package com.sztorm.lowallocmath

/**
 * Represents a read-only view of the portion of [Vector2IList]. Changes in the original list are
 * reflected in this list.
 */
class Vector2ISubList internal constructor(
    private val array: Vector2IArray,
    private val fromIndex: Int,
    toIndex: Int
) : List<Vector2I>, RandomAccess {
    private val _size: Int

    init {
        _size = toIndex - fromIndex
    }

    override val size: Int
        get() = _size

    override fun containsAll(elements: Collection<Vector2I>): Boolean {
        for (v in elements) {
            if (!contains(v)) {
                return false
            }
        }
        return true
    }

    /**
     * Returns an element at the given [index] without boxing.
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this list.
     */
    fun elementAt(index: Int): Vector2I {
        checkElementIndex(index, _size)

        return array[index + fromIndex]
    }

    override fun indexOf(element: Vector2I): Int {
        val toIndex = fromIndex + _size

        for (index in fromIndex until toIndex) {
            if (element == array[index]) {
                return index - fromIndex
            }
        }
        return -1
    }

    override fun isEmpty(): Boolean = _size == 0

    override fun lastIndexOf(element: Vector2I): Int {
        var index: Int = fromIndex + _size

        while (--index >= fromIndex) {
            if (element == array[index]) {
                return index - fromIndex
            }
        }
        return -1
    }

    override fun listIterator(): Vector2IListIterator =
        ListIteratorImpl(array, fromIndex, _size, index = 0)

    override fun listIterator(index: Int): Vector2IListIterator {
        checkPositionIndex(index, _size)

        return ListIteratorImpl(array, fromIndex, _size, index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): Vector2ISubList {
        checkRangeIndexes(fromIndex, toIndex, _size)
        val thisFromIndex: Int = this.fromIndex

        return Vector2ISubList(
            array, thisFromIndex + fromIndex, thisFromIndex + toIndex
        )
    }

    /**
     * Returns 1st *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 1.
     */
    inline operator fun component1(): Vector2I = elementAt(0)

    /**
     * Returns 2nd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 2.
     */
    inline operator fun component2(): Vector2I = elementAt(1)

    /**
     * Returns 3rd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 3.
     */
    inline operator fun component3(): Vector2I = elementAt(2)

    /**
     * Returns 4th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 4.
     */
    inline operator fun component4(): Vector2I = elementAt(3)

    /**
     * Returns 5th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 5.
     */
    inline operator fun component5(): Vector2I = elementAt(4)

    override operator fun contains(element: Vector2I): Boolean = indexOf(element) >= 0

    override operator fun get(index: Int): Vector2I {
        checkElementIndex(index, _size)

        return array[index + fromIndex]
    }

    override operator fun iterator(): Vector2IListIterator =
        ListIteratorImpl(array, fromIndex, _size, index = 0)

    private class ListIteratorImpl(
        private val array: Vector2IArray,
        private val fromIndex: Int,
        private val size: Int,
        private var index: Int
    ) : Vector2IListIterator() {
        override fun hasNext(): Boolean = index < size

        override fun hasPrevious(): Boolean = index > 0

        override fun nextIndex(): Int = index

        override fun previousIndex(): Int = index - 1

        override fun nextVector2I(): Vector2I =
            if (!hasNext()) throw NoSuchElementException("$index")
            else array[index++ + fromIndex]

        override fun previousVector2I(): Vector2I =
            if (!hasPrevious()) throw NoSuchElementException("$index")
            else array[--index + fromIndex]
    }
}