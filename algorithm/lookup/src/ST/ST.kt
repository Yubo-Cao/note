package ST

interface ST<K, V> : Iterable<K> {
    // 将键值对存储
    operator fun set(key: K, value: V?)

    // 根据键获取值
    operator fun get(key: K, default: V? = null): V

    // 根据键删除键值对
    fun delete(key: K) = set(key, null)

    // 是否包含键
    operator fun contains(key: K): Boolean {
        return try {
            get(key)
            true
        } catch (e: java.lang.IllegalArgumentException) {
            false
        }
    }

    // 是否为空
    fun isEmpty(): Boolean = size == 0;

    // 大小
    var size: Int

    // Support iteration
    override operator fun iterator(): Iterator<K> = keys().iterator()

    // 所有的键
    fun keys(): Iterator<K>
}