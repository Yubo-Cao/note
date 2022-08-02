package ST

interface SortedST<K : Comparable<K>, V> : ST<K, V> {
    // min key
    fun min(): K

    // max key
    fun max(): K

    // largest key that <= k
    fun floor(key: K): K

    // smallest key that >= k
    fun ceiling(key: K): K

    // return ranking of k inside sheet
    fun rank(key: K): Int

    // select a key with ranking
    fun select(rank: Int): K

    // delete smallest key
    fun deleteMin() = delete(min())

    // delete largest key
    fun deleteMax() = delete(max())

    // amount of key between lo, hi
    fun size(lo: K, hi: K) = (rank(hi) - rank(lo) + (if (hi in this) 1 else 0)).coerceAtLeast(0)

    // iterator to iterate from lo to hi
    fun keys(lo: K, hi: K): Iterator<K>

    override fun keys(): Iterator<K> = keys(min(), max())

    override operator fun iterator(): Iterator<K> = keys().iterator()
}