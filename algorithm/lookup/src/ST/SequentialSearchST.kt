package ST

class SequentialSearchST<K, V> : ST<K, V> {
    private var first: Node? = null
    override var size: Int = 0

    inner class KeyIterator : Iterator<K> {
        var current: Node? = first
        override fun hasNext(): Boolean {
            return current != null
        }

        override fun next(): K {
            val item = current!!.key
            current = current!!.next
            return item
        }
    }

    inner class Node(val key: K, var value: V, var next: Node?)

    override fun set(key: K, value: V?) {
        var x = first
        val v = value ?: throw IllegalArgumentException("Value must not be null")
        while (x != null) {
            if (x.key == key) {
                x.value = v // 命中，更新值
                return
            }
            x = x.next
        }
        first = Node(key, v, first) // 未命中，插入新节点
        size += 1
    }

    override fun get(key: K, default: V?): V {
        var x = first
        while (x != null) {
            if (key == x.key) {
                return x.value
            }
            x = x.next
        }
        return default ?: throw NoSuchElementException("Key $key not found")
    }

    /**
     * 即时 delete
     */
    override fun delete(key: K) {
        var x = first
        var prev: Node? = null
        while (x != null) {
            if (x.key == key) {
                if (prev != null)
                    prev.next = x.next
                else
                    first = x.next
            }
            prev = x
            x = x.next
        }
        throw NoSuchElementException("Key $key not found")
    }


    override fun keys(): Iterator<K> {
        return KeyIterator()
    }
}