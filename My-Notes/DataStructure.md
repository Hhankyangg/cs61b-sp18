# Data Structure

## Disjoint Sets

**Algorithm Development.** Developing a good algorithm is an iterative process. We create a model of the problem, develop an algorithm, and revise the performance of the algorithm until it meets our needs. This lecture serves as an example of this process.

**The Dynamic Connectivity Problem.** The ultimate goal of this lecture was to develop a data type that support the following operations on a fixed number $N$ of objects:

- `connect(int p, int q)` (called `union` in our optional textbook)
- `isConnected(int p, int q)` (called `connected` in our optional textbook)

We do not care about finding the actual path between `p` and `q`. We care only about their connectedness. A third operation we can support is very closely related to `connected()`:

- `find(int p)`: The `find()` method is defined so that `find(p) == find(q)` if `connected(p, q)`. We did not use this in class, but it’s in our textbook.

**Key observation: Connectedness is an equivalence relation.** Saying that two objects are connected is the same as saying they are in an equivalence class. This is just fancy math talk for saying “every object is in exactly one bucket, and we want to know if two objects are in the same bucket”. When you connect two objects, you’re basically just pouring everything from one bucket into another.

**Quick find.** This is the most natural solution, where each object is given an explicit number. Uses an array `id[]` of length $N$, where `id[i]` is the bucket number of object `i` (which is returned by `find(i)`). To connect two objects `p` and `q`, we set every object in `p`’s bucket to have `q`’s number.

- `connect`: May require many changes to `id`. Takes $\Theta (N)$ time, as algorithm must iterate over the entire array.
- `isConnected` (and `find`): take constant time.

Performing $M$ operations takes $\Theta (MN)$ time in the worst case. If $M$ is proportional to $N$, this results in a $\Theta (N^2)$ runtime.

**Quick union.** An alternate approach is to change the meaning of our `id` array. In this strategy, `id[i]` is the parent object of object `i`. An object can be its own parent. The `find()` method climbs the ladder of parents until it reaches the root (an object whose parent is itself). To connect `p` and `q`, we set the root of `p` to point to the root of `q`.

- `connect`: Requires only one change to `id[]`, but also requires root finding (worst case $\Theta (N)$ time).
- `isConnected` (and `find`): Requires root finding (worst case $\Theta (N)$ time).

Performing $M$ operations takes $\Theta (NM)$ time in the worst case. Again, this results in quadratic behavior if $M$ is proportional to $N$.

**Weighted quick union.** Rather than `connect(p, q)` making the root of `p` point to the root of `q`, we instead make the root of the smaller tree point to the root of the larger one. The tree’s *size* is the *number* of nodes, not the height of the tree. Results in tree heights of $\log N$.

- `connect`: Requires only one change to `id`, but also requires root finding (worst case $\log N$ time).
- `isConnected` (and `find`): Requires root finding (worst case $\log N$ time).

**Weighted quick union with path compression.** When `find` is called, every node along the way is made to point at the root. Results in nearly flat trees. Making $M$ calls to union and find with $N$ objects results in no more than $O (M \log ^∗ N)$ array accesses, not counting the creation of the arrays. For any reasonable values of $N$ in this universe that we inhabit, $\log ^∗ N$ is at most 5. It is possible to derive an even tighter bound, mentioned briefly in class (known as the [Ackerman function](https://en.wikipedia.org/wiki/Ackermann_function)).

## Binary Search Trees

### Normal Binary Search Trees (BSTs)

**Binary Trees**: in addition to the above requirements, also hold the binary property constraint. That is, each node has either 0, 1, or 2 children.

**Binary Search Trees**: in addition to all of the above requirements, also hold the property that For every node X in the tree:

- Every key in the left subtree is less than X’s key.
- Every key in the right subtree is greater than X’s key.

#### BSTs Operations

- **Search**
- **Insert** always insert at a leaf node
- **Delete** 
  - *has no children*
    delete the pointer
  - *has 1 child*
    reassign the parent's child pointer to the node's child and the node will eventually be garbage collected.
  - *has 2 children*
    take the right-most node in the left subtree or the left-most node in the right subtree. Then, replace the deleted node with either one you found and then remove the old one. (the old one must have no or 1 child)
#### BSTs as Sets and Maps
- We can use a BST to implement the `Set` ADT. If we use a BST, we can decrease the runtime of `contains` to $\log N$ because of the BST property which enables us to use binary search!
- We can also make a binary tree into a map by having each BST node hold `(key,value)` pairs instead of singular values. We will compare each element's key in order to determine where to place it within our tree.

### Balanced BSTs

关于查找效率，如果一棵树的高度为 $h$，在最坏的情况，查找一个关键字需要对比 $h$ 次，查找时间复杂度（也为平均查找长度 ASL，Average Search Length）不超过 $O(h)$。一棵理想的二叉搜索树所有操作的时间可以缩短到 $O(\log n)$（$n$ 是节点总数）。

然而 $O(h)$ 的时间复杂度仅为理想情况。在最坏情况下，搜索树有可能退化为链表。想象一棵每个结点只有右孩子的二叉搜索树，那么它的性质就和链表一样，所有操作（增删改查）的时间是 $O(n)$。

对于二叉搜索树来说，常见的平衡性的定义是指：以 T 为根节点的树，每一个结点的左子树和右子树高度差最多为 1。

#### AVL Trees

AVL 树，一种平衡的二叉搜索树。

- 空二叉树是一个 AVL 树
- 如果 T 是一棵 AVL 树，那么其左右子树也是 AVL 树，并且 $|h(ls) - h(rs)| \leq 1$，$h$ 是其左右子树的高度
- 树高为 $O(\log n)$

平衡因子：右子树高度 - 左子树高度

##### 旋转操作维护平衡
 左旋（Left Rotate 或者 zag） 和 右旋（Right Rotate 或者 zig）

![bst-rotate](bst-rotate.svg)

rotateLeft-G:
![rotateLeft(G)](rotateLeft-G.png)

We can also rotate on a non-root node. We just disconnect the node from the parent temporarily, rotate the subtree at the node, then reconnect the new root.

```java
// we are returning x, and other parts of our code will make use of this information to correctly update the parent node's pointer.
private Node rotateRight(Node h) {
    // assert (h != null) && isRed(h.left);
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    return x;
}

// make a right-leaning link lean to the left
private Node rotateLeft(Node h) {
    // assert (h != null) && isRed(h.right);
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    return x;
}
```

四种平衡被破坏的维护方式：
<grid>
![ll](bst-LL.svg)
![rr](bst-RR.svg)
![lr](bst-LR.svg)
![rl](bst-RL.svg)
</grid>

[AVL树可视化](https://www.cs.usfca.edu/~galles/visualization/AVLtree.html)

#### B-Trees

##### B树分裂操作

针对一棵高度为 $h$ 的 $m$ 阶 B树，插入一个元素时，首先要验证该元素在 B树中是否存在，如果不存在，那么就要在叶子节点中插入该新的元素，此时分 3 种情况：
1. 如果叶子节点空间足够，即该节点的关键字数小于 $m-1$，则直接插入在叶子节点的左边或右边；
2. 如果空间满了以至于没有足够的空间去添加新的元素，即该节点的关键字数已经有了 $m$ 个，则需要将该节点进行「分裂」，将一半数量的关键字元素分裂到新的其相邻右节点中，中间关键字元素上移到父节点中，而且当节点中关键元素向右移动了，相关的指针也需要向右移。
     - 从该节点的原有元素和新的元素中选择出中位数
    - 小于这一中位数的元素放入左边节点，大于这一中位数的元素放入右边节点，中位数作为分隔值。
    - 分隔值被插入到父节点中，这可能会造成父节点分裂，分裂父节点时可能又会使它的父节点分裂，以此类推。如果没有父节点（这一节点是根节点），就创建一个新的根节点（增加了树的高度）。

一个三阶 B树 插入 25，26：
![b-tree-1](b-tree-1.png)

> B-Trees with a limit of 3 items per node are also called 2-3-4 trees or 2-4 trees (a node can have 2, 3, or 4 children). Setting a limit of 2 items per node results in a 2-3 tree.

##### B树特征，用途

**Usage of b-tree**
B-Trees are used mostly in two specific contexts: first, with a small L(阶数) for conceptually balancing search trees, or secondly, with L in the thousands for *databases* and *file systems* with large records.

**B树性质**
- 每个节点最多有 $m$ 个子节点。
- 每一个非叶子节点（除根节点）最少有 $\lceil \dfrac{m}{2} \rceil$ 个子节点。
- 如果根节点不是叶子节点，那么它至少有两个子节点。
- 有 $k$ 个子节点的非叶子节点拥有 $k−1$ 个键，且升序排列，满足 $k[i] < k[i+1]$。
- 所有的叶子节点都在同一层（$\log n$）。

[B树可视化](https://www.cs.usfca.edu/~galles/visualization/BTree.html)

#### Red-Black Trees

We show that a link is a glue link by making it red. Normal links are black. Because of this, we call these structures **left-leaning red-black trees (LLRB)**.

![B-to-LLRB](B-to-LLRB.png)

Left-Leaning Red-Black trees have a **1-1 correspondence with** 2-3 trees. Every 2-3 tree has a unique LLRB red-black tree associated with it. 

##### Properties of LLRBs

- 1-1 correspondence with 2-3 trees.
- No node has 2 red links.
- There are no red right-links.
- Every path from root to leaf has the same - number of black links (because 2-3 trees have the same number of links to every leaf).
- Height is no more than `2 * height + 1` of the corresponding 2-3 tree. 
- The height of a red-black tree is proportional to the log of the number of entries.

##### Insertion of LLRBs

When inserting: Use a red link. Insert in the same way as inserting into a BST.
- If there is a right-leaning “3-node”, we have a Left Leaning Violation.
  - Rotate left the appropriate node to fix.
    ![LLRB-insert1](LLRB-insert1.png)
- If there are two consecutive left links, we have an Incorrect 4 Node Violation.
  - Rotate right the appropriate node to fix.
    ![LLRB-insert2](LLRB-insert2.png)
- If there are any nodes with two red children, we have a Temporary 4 Node.
  - Color flip the node to emulate the split operation.
    ![LLRB-insert3](LLRB-insert3.png)

在实现时，可将红色边作为红色结点 -> 插入元素时，默认先插入红色节点

```java
private Node put(Node h, Key key, Value val) {
    if (h == null) { return new Node(key, val, RED); }

    int cmp = key.compareTo(h.key);
    if (cmp < 0)      { h.left  = put(h.left,  key, val); }
    else if (cmp > 0) { h.right = put(h.right, key, val); }
    else              { h.val   = val;                    }

    if (isRed(h.right) && !isRed(h.left))      { h = rotateLeft(h);  }
    if (isRed(h.left)  &&  isRed(h.left.left)) { h = rotateRight(h); }
    if (isRed(h.left)  &&  isRed(h.right))     { flipColors(h);      } 

    return h;
}
```

- Binary search trees are simple, but they are subject to imbalance which leads to crappy runtime. 2-3 Trees (B Trees) are balanced, but painful to implement.
- LLRB insertion is simple to implement (deletion is a bit harder to implement).
  - Use three basic operations to maintain the balanced structure, namely `rotateLeft`, `rotateRight`, and `colorFlip`.
- LLRBs maintain correspondence with 2-3 trees, Standard Red-Black trees maintain correspondence with 2-3-4 trees.
  - Java’s `TreeMap` is a red-black tree that corresponds to 2-3-4 trees.
  - 2-3-4 trees allow glue links on either side.
  - More complex implementation, but faster.

## Hashing

**Brute force approach.** All data is just a sequence of bits. Can treat key as a gigantic number and use it as an array index. Requires exponentially large amounts of memory.

**Hashing.** Instead of using the entire key, represent entire key by a smaller value. In Java, we hash objects with a `hashCode()` method that returns an integer (32 bit) representation of the object.

**hashCode() to index conversion.** To use `hashCode()` results as an index, we must convert the `hashCode()` to a valid index. Modulus does not work since hashCode may be negative. Taking the absolute value then the modulus also doesn’t work since `Math.abs(Integer.MIN_VALUE)` is negative. Typical approach: use `hashCode & 0x7FFFFFFF` or `Math.floorMod(key.hashCode(), array.length)` instead before taking the modulus.

**Hash function.** Converts a key to a value between 0 and M-1. In Java, this means calling hashCode(), setting the sign bit to 0, then taking the modulus.

**Designing good hash functions.** Requires a blending of sophisticated mathematics and clever engineering; beyond the scope of this course. Most important guideline is to use all the bits in the key. If `hashCode()` is known and easy to invert, adversary can design a sequence of inputs that result in everything being placed in one bin. Or if `hashCode()` is just plain bad, same thing can happen.

**Collision resolution.** Two philosophies for resolving collisions discussed in class: Separate (a.k.a. external) chaining and ‘open addressing’.

**Separate-chaining hash table.** Key-value pairs are stored in a linked list of nodes of length M. Hash function tells us which of these linked lists to use. Get and insert both require potentially scanning through entire list.

**Resizing separate chaining hash tables.** Understand how resizing may lead to objects moving from one linked list to another. Primary goal is so that M is always proportional to N, 
$$
\rm load \ factor = \frac {size()} {array.length}
$$
i.e. maintaining a load factor bounded above by some constant.

**Performance of separate-chaining hash tables.** Cost of a given `get`, `insert`, or `delete` is given by number of entries in the linked list that must be examined.
- The expected amortized search and insert time (assuming items are distributed evenly) is N / M, which is no larger than some constant (due to resizing).

**Open-addressing hash tables.** 
- 线性探测
- 平方探测
- 多次哈希