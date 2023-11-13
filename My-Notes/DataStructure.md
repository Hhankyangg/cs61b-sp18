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

#### AVL Trees

#### B-Trees

#### Red-Black Trees

