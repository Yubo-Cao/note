package ST

@Suppress("UNCHECKED_CAST")
class BinarySearchST<K : Comparable<K>, V>(capacity: Int) : SortedST<K, V> {
    private var keys: Array<K?> = Array<Comparable<Any>?>(capacity) { null } as Array<K?>
    private var values: Array<V?> = Array<Any?>(capacity) { null } as Array<V?>

    override fun set(key: K, value: V?) {
        val i = rank(key)
        if (i < size && keys[i]!!.compareTo(key) == 0) {
            // key exists, so replace value
            values[i] = value
        } else {
            ensureCapacity(size + 1)
            // Move all the elements from i to size - 1 to the right
            for (j in size downTo i + 1) {
                keys[j] = keys[j - 1]
                values[j] = values[j - 1]
            }
            // Insert the new element
            keys[i] = key
            values[i] = value
        }
    }

    override fun get(key: K, default: V?): V {
        if (isEmpty()) throw NoSuchElementException()
        val i = rank(key)
        if (i < size && keys[i]!!.compareTo(key) == 0)
            return values[i]!!
        throw NoSuchElementException()
    }

    override fun min(): K {
        if (isEmpty()) throw NoSuchElementException()
        return keys[0]!!
    }

    override fun max(): K {
        if (isEmpty()) throw NoSuchElementException()
        return keys[size - 1]!!
    }

    override fun floor(key: K): K {
        if (isEmpty()) throw NoSuchElementException()
        val i = rank(key)
        if (i == 0)
            throw NoSuchElementException()
        return keys[i - 1]!!
    }

    override fun delete(key: K) {
        if (isEmpty()) throw NoSuchElementException()
        var i = rank(key)
        if (keys[i]?.compareTo(key)?.equals(0) ?: throw NoSuchElementException()) {
            while (i < size) {
                keys[i] = keys[i + 1]
                values[i] = values[i + 1]
                i++
            }
            keys[size - 1] = null
            values[size - 1] = null
            size--
        }
    }

    override fun ceiling(key: K): K {
        return keys[rank(key)]!!
    }

    override fun select(rank: Int): K {
        return keys[rank] ?: throw NoSuchElementException()
    }

    override fun keys(lo: K, hi: K): Iterator<K> {
        val l = rank(lo)
        val h = rank(hi)
        val q = MutableList(h - l) { keys[l + it]!! }
        if (hi in this) {
            q.add(hi)
        }
        return q.iterator()
    }

    override fun rank(key: K): Int {
        return rank(key, 0, size - 1)
    }

    private fun rank(key: K, lo: Int, hi: Int): Int {
        if (hi < lo)
            return lo
        val mid = lo + (hi - lo) / 2
        // keys[mid] must not be null because mid must in between lo and hi, which
        // are 0 and size
        val cmp = key.compareTo(keys[mid]!!)
        // depend on result, return proper halve or rank
        return if (cmp < 0)
            rank(key, lo, mid - 1)
        else if (cmp > 0)
            rank(key, mid + 1, hi)
        else
            mid
    }

    private fun rapidRank(key: K): Int {
        var lo = 0
        var hi = size - 1
        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            val cmp = key.compareTo(keys[mid]!!)
            if (cmp < 0) hi = mid - 1
            else if (cmp > 0) lo = mid + 1
            else return mid
        }
        return lo
    }

    private fun ensureCapacity(capacity: Int) {
        if (capacity > keys.size) {
            resizeArray(capacity * 2)
        }
    }

    private fun resizeArray(capacity: Int) {
        keys = keys.copyOf(capacity)
        values = values.copyOf(capacity)
    }

    override var size: Int = 0
}