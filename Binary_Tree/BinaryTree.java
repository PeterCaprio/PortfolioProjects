import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.util.Random;

import org.w3c.dom.Node;

public class BinaryTree implements TreeStructure {

    // The Node class, each node has a value, 2 children and booleans
    public class BinaryTreeNode {

        public BinaryTreeNode() {
        };

        public BinaryTreeNode(long value) {
            this.value = value;
        }

        // various variables
        public long value = 0;
        public long insertionTime = 0;

        public BinaryTreeNode leftChild;
        public BinaryTreeNode rightChild;
        public BinaryTreeNode parent;

        public boolean visited = false;

        // getters and setters for the node's children
        public BinaryTreeNode getLeftChild() {
            return leftChild;
        }

        public BinaryTreeNode getRightChild() {
            return rightChild;
        }

        public void setLeftChild(BinaryTreeNode child) {
            leftChild = child;
        }

        public void setRightChild(BinaryTreeNode child) {
            rightChild = child;
        }

        // public void setParent(BinaryTreeNode parent) {
        // parent = parent;
        // }

        // booleans for determining if a node has children
        public boolean hasRightChild() {
            return (rightChild != null);
        }

        public boolean hasLeftChild() {
            return (leftChild != null);
        }

        public boolean hasOneChild() {
            return ((hasLeftChild() && !hasRightChild()) || (!hasLeftChild()) && hasRightChild());
        }

        public boolean isLeaf() {
            return (leftChild != null && rightChild != null);
        }

        // public void nullify() {
        // leftChild = null;
        // rightChild = null;
        // value = null;
        // insertionTime = null;
        // }
    }

    // setting up our root node
    BinaryTreeNode root;

    public BinaryTree() {
        this.root = new BinaryTreeNode(0);
    }

    public BinaryTree(long value) {
        this.root = new BinaryTreeNode(value);
    }

    public void insert(Integer num) {
        insert(root, num);
    }

    // recvursively takes in a node and a num to insert, finds the correct location
    // based on the natural ordering of a BST
    public void insert(BinaryTreeNode node, Integer num) {
        if (num < node.value) {
            if (node.hasLeftChild()) {
                insert(node.leftChild, num);
            }

            else {
                node.leftChild = new BinaryTreeNode((long) num);
                node.leftChild.insertionTime = System.nanoTime();
            }
        }

        if (num > node.value) {
            if (node.hasRightChild()) {
                insert(node.rightChild, num);
            }

            else {
                node.rightChild = new BinaryTreeNode((long) num);
                node.rightChild.insertionTime = System.nanoTime();
            }
        }

    }

    public Boolean remove(Integer num) {
        // Cases:
        // removing a leaf node

        // removing an internal node with 1 child

        // removing an internal node with 2 children
        // ^The leftmost node of the right subtree^ --> smallest thing in the right
        // subtree,
        // has the rest of the right subtree to its right,
        // and the left subtree that is still smaller than it because it was in the
        // right subtree

        // Successor is a node that is the leftmost of the right subtree during a
        // remove()

        removeHelper(num, root);

        return false;
    }

    // a bool to say whether or not we have "found" the node
    public boolean match(long nodeValue, Integer num) {
        return (nodeValue == num);
    }

    // recursive helper function that finds the leftmost node given a starting node
    public BinaryTreeNode leftMostNode(BinaryTreeNode node) {
        if (node.hasLeftChild()) {
            return leftMostNode(node.leftChild);
        }
        return node;
    }

    public Boolean removeHelper(Integer num, BinaryTreeNode node) {
        // TODO:

        if (node == null) {
            return false;
        }

        // BASE CASE 1: If the node is a leaf and num matches node.value
        if (node.isLeaf() && match(node.value, num)) {
            node = null;
            return true;
        }

        // BASE CASE 2: If the node has a single child and match
        if (node.hasOneChild() && match(node.value, num)) {

            // if the only child is the left, swaps it and its parent node, then clears the
            // reference
            if (node.hasLeftChild()) {
                node = node.leftChild;
                node.leftChild = null;
                return true;
            }

            if (node.hasRightChild()) {
                node = node.rightChild;
                node.rightChild = null;
                return true;
            }
        }

        // BASE CASE 3: If the node has 2 children and match
        if ((node.hasLeftChild() && node.hasRightChild()) && (match(node.value, num))) {
            // TODO: Some Fuck shit, searching down and finding the leftmost successor on
            // the right subtree

            BinaryTreeNode tempNode = new BinaryTreeNode();

            // setting the parent to the leftmost child of the right subtree
            node = leftMostNode(node);
        }
        // OTHERWISE, WE LOOK AND COMPARE OUR BASE CASES TO THE NEXT NODE
        // heading down the right subtree
        if (num > node.value) {
            removeHelper(num, node.rightChild);
        }

        // heading down the right subtrees
        if (num < node.value) {
            removeHelper(num, node.leftChild);
        }

        return false;

    }

    // recursive get function that traverses the tree to find a value, num, passed
    // in
    // search for thing to remove
    // if No r found
    // return false

    // else
    // find replacement : i
    // build a search(node, val) method
    public Long get(Integer num) {
        return get(num, root);
    }

    static public Long get(Integer num, BinaryTreeNode node) {

        if (num == node.value) {
            return node.insertionTime;
        }

        else if (num < node.value && node.leftChild != null) {
            return get(num, node.leftChild);
        }

        else if (num > node.value && node.rightChild != null) {
            return get(num, node.rightChild);
        }

        return (long) -1;
    }

    // Implement the DFS traversal algorithm using recursion.
    // This will involve traversing the tree, keeping track of the depth at each
    // node, and updating the maximum depth as needed.
    // Make sure to handle any edge cases, such as an empty tree or a tree with only
    // one node.

    public Integer findMaxDepth() {
        // starting our depth at 0
        int depth = 0;

        return findMaxDepthHelper(root, depth);
    }

    public Integer findMaxDepthHelper(BinaryTreeNode node, int depth) {

        if (node.hasLeftChild() && node.hasRightChild()) {

            if (findMaxDepthHelper(node.leftChild, depth + 1) > findMaxDepthHelper(node.rightChild, depth + 1)) {
                return findMaxDepthHelper(node.leftChild, depth + 1);
            }

            else {
                return findMaxDepthHelper(node.rightChild, depth + 1);
            }

        }
        // Base Case: If node IS null, return 0
        if (node.isLeaf()) {
            return depth;
        }

        if (node.hasLeftChild()) {
            return findMaxDepthHelper(node.leftChild, depth + 1);
        }

        if (node.hasRightChild()) {
            return findMaxDepthHelper(node.rightChild, depth + 1);
        }

        return depth;

    }

    public Integer findMinDepth() {
        return findMinDepthHelper(root, 0);
    }

    // recursive helper method that finds the shortest path from the root to a leaf
    public Integer findMinDepthHelper(BinaryTreeNode node, int depth) {
        if (node.hasLeftChild() && node.hasRightChild()) {

            if (findMinDepthHelper(node.leftChild, depth + 1) > findMinDepthHelper(node.rightChild, depth + 1)) {
                return findMinDepthHelper(node.rightChild, depth + 1);
            }

            else {
                return findMinDepthHelper(node.leftChild, depth + 1);
            }

        }
        // Base Case: If node IS null, return 0
        if (node.isLeaf()) {
            return depth;
        }

        if (node.hasLeftChild()) {
            return findMinDepthHelper(node.leftChild, depth + 1);
        }

        if (node.hasRightChild()) {
            return findMinDepthHelper(node.rightChild, depth + 1);
        }

        return depth;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // BinaryTree Driver Code
        // File file = new File(args[0]);
        // FileReader fReader = new FileReader(file);
        // BufferedReader bufferedReader = new BufferedReader(fReader);

        // TreeStructure tree = new BinaryTree();
        // Random rng = new Random(42);

        // ArrayList<Integer> nodesToRemove = new ArrayList<>();
        // ArrayList<Integer> nodesToGet = new ArrayList<>();
        // String line = bufferedReader.readLine();

        // while (line != null) {
        // // LineInt is the int we get from the line
        // // Integer lineInt = Integer.parseInt(line);
        // Integer lineInt = rng.nextInt();

        // // passes it to the tree, insert will handle where it goes
        // tree.insert(lineInt);

        // // randomly adds or removes the node from the tree
        // Integer rand = rng.nextInt(10);
        // if (rand < 5)
        // nodesToRemove.add(lineInt);
        // else if (rand >= 5)
        // nodesToGet.add(lineInt);
        // line = bufferedReader.readLine();
        // }

        // bufferedReader.close();

        // for (int i = 0; i < 10; i++) {
        // System.out.println(nodesToGet.get(i) + " inserted at " +
        // tree.get(nodesToGet.get(i)));
        // }
        // System.out.println("Max depth: " + tree.findMaxDepth());
        // System.out.println("Min depth: " + tree.findMinDepth());
        // for (Integer num : nodesToRemove) {
        // tree.remove(num);
        // }

        // for (int i = 0; i < 10; i++) {
        // System.out.println(nodesToGet.get(i) + " inserted at " +
        // tree.get(nodesToGet.get(i)));
        // }

        // System.out.println("Max depth: " + tree.findMaxDepth());
        // System.out.println("Min depth: " + tree.findMinDepth());

        Random rand = new Random();

        BinaryTree tree = new BinaryTree(50);

        // for (int i = 0; i < 50000; i++) {
        // tree.insert(rand.nextInt(50000));
        // }

        tree.root = new BinaryTreeNode(20);
        tree.leftChild = new BinaryTreeNode(10);

        // System.out.println(tree.root.value);
        // System.out.println(tree.root.leftChild.value);
        // System.out.println(tree.root.rightChild.value);

        System.out.println("FIND MAX DEPTH " + tree.findMaxDepth());
        System.out.println("FIND MIN DEPTH " + tree.findMinDepth());

    }
}
