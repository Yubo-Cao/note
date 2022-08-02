package ST

import kotlin.math.ceil

/**
 * 二叉查找树的递归实现。实际使用的程序往往使用非递归的实现。
 */
class BST<K : Comparable<K>, V> : SortedST<K, V> {
    private var root: Node? = null

    private inner class Node(val key: K, var value: V?, var left: Node?, var right: Node?, var size: Int) {
        override fun toString(): String {
            return "Node(key=$key, value=$value, left=$left, right=$right)"
        }
    }

    override fun set(key: K, value: V?) {
        root = set(root, key, value)
    }

    /**
     * 调用前的代码也是向下行走，直到找到一个合适的位置（改变值），或者到达了最底部（插入新节点）
     * 调用后的代码向上回溯，每次更新 size 计数器的值。
     *
     * 随着二叉树的生长，每次查找使用的比较次数相较于整个树的大小会减少。
     * 最好情况下，lgN 次比较就能找到目标节点。（也就是，完全平衡的二叉树）
     * 最坏情况下，所有节点都在一条路径上，就和 Sequential Search 一样。
     */
    private fun set(node: Node?, key: K, value: V?): Node {
        if (node == null) return Node(key, value, null, null, 1)
        when {
            key < node.key -> node.left = set(node.left, key, value)
            key > node.key -> node.right = set(node.right, key, value)
            else -> node.value = value
        }
        node.size = size(node.left) + size(node.right) + 1
        return node
    }


    override fun get(key: K, default: V?): V {
        return get(root, key, default)
    }

    /**
     * 可以认为调用前的代码是沿着二叉树向下行走，而调用后的代码是沿着二叉树向上回溯(return)。
     */
    private fun get(node: Node?, key: K, default: V?): V {
        if (node == null) return default ?: throw NoSuchElementException()
        return when {
            key < node.key -> get(node.left, key, default)
            key > node.key -> get(node.right, key, default)
            else -> node.value ?: default ?: throw NoSuchElementException()
        }
    }

    /**
     * 从左子树向下走即可
     */
    override fun min(): K {
        return min(root)?.key ?: throw NoSuchElementException()
    }

    private fun min(node: Node?): Node? {
        if (node == null) return null
        return if (node.left == null) node else min(node.left)
    }

    override fun max(): K {
        return max(root)?.key ?: throw NoSuchElementException()
    }

    private fun max(node: Node?): Node? {
        if (node == null) return null
        return if (node.right == null) node else max(node.right)
    }

    override fun select(rank: Int): K {
        if (rank !in 0 until size)
            throw IllegalArgumentException("Invalid rank")
        return select(root, rank)
    }

    private fun select(node: Node?, rank: Int): K {
        // 这不应该发生，因为 rank 经过检查，在 0 until size 之间
        if (node == null)
            throw NoSuchElementException()
        val lsz = size(node.left)
        return when {
            // 在左子树中寻找
            lsz > rank -> select(node.left, lsz)
            lsz == rank -> node.key
            // 在右子树中，减去了左子树和根节点的排名中寻找
            else -> select(node.right, rank - lsz - 1)
        }
    }

    override fun keys(lo: K, hi: K): Iterator<K> {
        val queue = ArrayDeque<K>()
        keys(root, queue, lo, hi)
        return queue.iterator()
    }

    private fun keys(node: Node?, queue: ArrayDeque<K>, lo: K, hi: K) {
        if (node == null)
            return
        // 中序遍历
        // 先遍历左子树
        // 如果 key 比 lo 更大，看看有没有更小的。如果 key 已经比 lo 小，不要这部分
        if (lo < node.key) keys(node.left, queue, lo, hi)
        // 如果 key 在 lo 和 hi 之间，可以加入。在从 lo 回溯和 hi 回溯的过程中，这一步干活
        if (lo <= node.key && hi >= node.key) queue.add(node.key)
        // 最后，如果有 hi，造出一堆回溯加入 hi
        if (node.key > hi) keys(node.right, queue, lo, hi)
    }

    override fun rank(key: K): Int {
        return rank(root, key)
    }

    private fun rank(node: Node?, key: K): Int {
        if (node == null) return 0
        return when {
            // 小于，对着左子树继续计算
            node.key < key -> rank(node.left, key)
            // 大于，根节点加左子树和右子树排名
            node.key > key -> 1 + size(node.left) + rank(node.right, key)
            // 相等，返回左子树节点总数
            else -> size(node.left)
        }
    }

    override fun ceiling(key: K): K {
        return ceiling(root, key) ?: throw NoSuchElementException()
    }

    private fun ceiling(node: Node?, key: K): K? {
        if (node == null)
            return null
        return when {
            // If too large, find someone larger
            node.key > key -> ceiling(node.right, key)
            node.key.compareTo(key) == 0 -> key
            // if too small, find someone smaller
            else -> ceiling(node.left, key)
        }
    }

    override fun floor(key: K): K {
        return floor(root, key) ?: throw NoSuchElementException()
    }

    private fun floor(node: Node?, key: K): K? {
        if (node == null)
            return null
        return when {
            node.key < key -> floor(node.left, key) ?: node.key
            node.key.compareTo(key) == 0 -> key
            else -> floor(node.right, key) ?: node.key
        }
    }

    private fun size(node: Node?): Int {
        if (node == null) return 0
        return node.size
    }

    override fun deleteMin() {
        deleteMin(root ?: throw NoSuchElementException())
    }

    private fun deleteMin(node: Node): Node? {
        // If node left does not exist, return right and replace previous left with right
        node.left = deleteMin(node.left ?: return node.right)
        node.size = size(node.left) + size(node.right) + 1
        return node
    }

    override fun delete(key: K) {
        super.delete(key)
    }

    /**
     * 删除节点之后用后继节点填补其位置
     *  - 后继节点是右子树的最小节点。借此，这个节点 大于左子树中任何节点，并且小于右子树中任何节点，保证了树的有序性
     *  - 被删除节点和后继节点之间不存在任何其他的键。
     *      - val deleted = 被删除节点
     *      - val substitute = min(deleted.right)
     *      - substitute.right = deleteMin(deleted.right) 将原本的二叉树指向最小节点
     *      - substitute.left = deleted.left 将原本的左节点放回去
     *  - 假定 node 包含左节点，右节点。在全过程中，除了 deleteMin 可能出现 null, 应该没有问题
     *  - Hibbard 的实现在大多数时候都没什么问题，对于大规模应用可能会有一点问题
     */
    private fun delete(node: Node?, key: K): Node? {
        if (node == null)
            return null // node: Node
        when {
            // Iterate until we find node
            node.key < key -> node.right = delete(node.right, key)
            node.key > key -> node.left = delete(node.left, key)
            else -> {
                // 命中
                // 处理只有一个节点的情况
                if (node.right == null) return node.left
                if (node.left == null) return node.right
                // 此时，min 必须非 null
                val sub = min(node.right)
                sub!!.right = deleteMin(node.right!!)
                sub.left = node.left
            }
        }
        // 更新计数器
        node.size = size(node.left) + size(node.right) + 1
        return node
    }

    override fun deleteMax() {
        if (isEmpty())
            throw NoSuchElementException()
        // Since not empty, must have root
        deleteMax(root!!)
    }

    private fun deleteMax(node: Node): Node? {
        // 一直向 node.right 行走，直至找到 node.right == null，此时将其父节点的右子树替换为其左子树
        // 孤零零的节点本身将被垃圾回收
        node.right = deleteMax(node.right ?: return node.left)
        node.size = size(node.left) + size(node.right) + 1
        // 返回修改过的节点
        return node
    }

    override fun toString(): String {
        return toString(root)
    }

    private fun toString(node: Node?, depth: Int = 0): String {
        if (node == null)
            return ""
        return buildString {
            append(toString(node.left, depth + 1))
            append(" ".repeat(4).repeat(depth))
            append(node.key.toString())
            append("\n")
            append(toString(node.right, depth + 1))
        }
    }

    override var size: Int = 0
        get() = size(root)
}