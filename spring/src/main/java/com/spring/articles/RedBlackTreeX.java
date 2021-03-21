package com.spring.articles;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author Akash Upadhyay
 * Generic red black tree which is Serializable
 * @param <T>
 */
public  class RedBlackTreeX<T extends Comparable<T>> implements Serializable{

	/**
	 * Using static class as node to be added in the tree
	 */
	
	private static class Node<T extends Comparable<T>> implements Serializable{
		Node<T> parent, left, right;
		Color color;
		public T data;
		public String title;
		/** Constructor to initialize the node object 
		 * 
		 * @param parent
		 * @param data
		 * @param color
		 * 
		 * */
		public Node(Node<T> parent, T data, Color color,String title) {
			this.parent = parent;
			this.data=data;
			this.color = color;
			this.title=title;
		}

			
	}
	
	/**
	 * root of the tree defining separately
	 */
	private Node<T> root;
	
	
	
	/**
	 * setting root which will get update on rotations
	 * @param parent
	 * @param data
	 * @param color
	 */
	public void setRoot(T data) {
		this.root=new Node<T>(null, data, Color.BLACK,null);
		
	}
	
	/**
	 * public method to add node to the tree
	 * 
	 * @param data
	 * @return true if the tree does not contain the data specified else false
	 */
	public boolean add(T data,String name) {
		
		Node<T> nodeToBeAdded=new Node<T>(null, data, Color.RED,name);
		Node<T> addedNode=addWithReturnNode(root, nodeToBeAdded);
		if(addedNode==null)
			return false;
		this.root=checkForRedRedissue(addedNode);
		return true;
	}
	/**
	 * Checking for the red black tree no red-red parent child relation
	 * and returns the added node
	 * @param node
	 * @return 
	 */
	private  Node<T> checkForRedRedissue(Node<T> node) {

		// if the parent is not null and color of node.parent and node is red
		if (node.parent != null && node.parent.color == node.color && node.color == Color.RED) {
			// sibling is referring to node.parent.parent children
			Node<T> otherSibling = getOtherSibling(node.parent);
			if (otherSibling == null || otherSibling.color == Color.BLACK) {
				// 1. check if the other sibling is null or sibling is black
				// if it is then rotation is required one of the 4 types

				// implement the rest of the other sibling
				// Believe in yourself, you can do it.
				
				if (isLeftChild(node)) {
					// added red node is left child of its parent
					if (isLeftChild(node.parent)) {
						// parent of added node is left child of its parent
						// left child(node) and left parent(node.parent)
						// rotate right(with color change)
						node = node.parent;

						rightRotate(node, true);

					} else {
						// parent of added node is right child of its parent
						// left child(node) and right parent(node.parent)
						// rotate right and then left(with color change)
						node = node.parent;

						
						rightRotate(node.left, false);
						node = node.parent;
						leftRotate(node, true);
					}
				} else {// 45 330 43 2 54 55 333 32 31 63 3344 21 22 23
						// added red node is right child of its parent
					if (isLeftChild(node.parent)) {
						// parent of added node is left child of its parent
						// right child(node) and left parent(node.parent)
						// rotate left and then right(with color change)
						node = node.parent;

						leftRotate(node.right, false);
						node = node.parent;
						rightRotate(node, true);

					} else {
						// parent of added node is right child of its parent
						// right child(node) and right parent(node.parent)
						// rotate left (with color change)
						node = node.parent;
						leftRotate(node, true);

					}
				}
			} else {
				// if the above condition is false which means the sibling is red
				// therefore color change and again function call is required
				node.parent.parent.left.color = Color.BLACK;
				node.parent.parent.right.color = Color.BLACK;
				// if the node.parent.parent is not the main top root of the tree
				if (node.parent.parent.parent != null) {
					node.parent.parent.color = Color.RED;
					return checkForRedRedissue(node.parent.parent);
				} else {
					return node.parent.parent;
				}

			}
		}

		// now we have to return the root
		while (node.parent != null)
			node = node.parent;

		return node;
	}
	/**
	 * Returns true if the root is left or right child of its parent
	 * @param root
	 * @return
	 */
	private boolean isLeftChild(Node<T> root) {
		if (root.parent.left != null && root.parent.left.data == root.data)
			return true;
		else
			return false;
	}

	
	/**
	 * makes a one left rotation in the tree
	 * @param root
	 * @param colorChangeNeed
	 */
	private void leftRotate(Node<T> root,boolean colorChangeNeed) {
		Node<T> parent=root.parent;
		if(parent.parent!=null) {
			root.parent=parent.parent;
			if(parent.parent.right==parent)
				parent.parent.right=root;
			else
				parent.parent.left=root;
		}
		else {
			root.parent=null;
		}
		
		Node<T> left=root.left;
		root.left=parent;
		parent.parent=root;
		parent.right=left;
		if(left!=null)
			left.parent=parent;
		if(colorChangeNeed) {
			root.color=Color.BLACK;
			parent.color=Color.RED;
		}
		
	}
	/**
	 * makes a one right rotation in the tree
	 * @param root
	 * @param colorChangeNeed
	 */
	private void rightRotate(Node<T> root,boolean colorChangeNeed) {
		Node<T> parent=root.parent;
		if(parent.parent!=null) {
			root.parent=parent.parent;
			if(parent.parent.right==parent) 
				parent.parent.right=root;
			
			else 
				parent.parent.left=root;
			
		}
		else {
			root.parent=null;
		}
		
		Node<T> right=root.right;
		root.right=parent;
		parent.parent=root;
		parent.left=right;
		if(right!=null)
			right.parent=parent;
		if(colorChangeNeed) {
			root.color=Color.BLACK;
			parent.color=Color.RED;
		}
	}
	
	/**
	 * returns the sibling of the node
	 * @param node
	 * @return
	 */
	private Node<T> getOtherSibling(Node<T> node) {
		// if any one of the sibling is null then return that one
		if (node.parent.left == null)
			return null;
		if (node.parent.right == null)
			return null;

		// if both the siblings are not null means one of them is red or black
		if (node.parent.left.data == node.data) {
			return node.parent.right;
		} else
			return node.parent.left;
	}

	
	/**
	 * method for performing normal BST insertion
	 * @param nodeToBeAdded
	 * @return null if the tree contains the node already otherwise the added node
	 */
	private Node<T> addWithReturnNode(Node<T> root,Node<T> nodeToBeAdded) {
		//if the tree already contains the data then return null;
		if(nodeToBeAdded.data.compareTo(root.data)==0)return null;
		
		if(nodeToBeAdded.data.compareTo(root.data)>0) {
			// go to the right subtree
			if (root.right != null) {
				return addWithReturnNode(root.right,nodeToBeAdded);
			} else {
				nodeToBeAdded.parent = root;
				root.right = nodeToBeAdded;
				return root.right;
			}
		}
		else {
			// go to the left subtree
			if (root.left != null) {
				return addWithReturnNode(root.left, nodeToBeAdded);
			} else {
				nodeToBeAdded.parent = root;
				root.left = nodeToBeAdded;
				return root.left;
			}
		}
	}
	
	/**
	 * public method for displaying the tree
	 */
	public void inorderDisplay() {
		Node<T> root=this.root;
		inorderDisplaySaveRoot(root);
	}
	
	/**
	 * test method to display the tree in order
	 * @param root
	 */
	private void inorderDisplaySaveRoot(Node<T> root) {
		if(root==null)return;
		inorderDisplaySaveRoot(root.left);
		System.out.println(root.data.toString());
		inorderDisplaySaveRoot(root.right);
	}
	/**
	 * public method to get the root value
	 * @return
	 */
	public T getRoot() {
		return this.root.data;
	}

	/**
	 * public method for performing the binary search
	 * @param data
	 * @return
	 */
	public boolean binarySearch(T data) {
		boolean res= PerformBinarySearch(data, this.root);
		return res;
	}
	/**
	 * 
	 * @param data
	 * @param node
	 * @return true if tree contains the data else false
	 */
	private boolean PerformBinarySearch(T data,Node<T> node) {
		if(node==null)return false;
		if(node.data.compareTo(data)==0)return true;
		if(node.data.compareTo(data)>0)
			return PerformBinarySearch(data, node.left);
		else
			return PerformBinarySearch(data, node.right);
	}

	/**
	 * This method returns the tree in order (increasing) 
	 * @return Returns a list
	 */
	public List<T> getTreeAsList() {
		List<T> inorderList=new LinkedList<>();
		addToListInorder(this.root,inorderList);
		return inorderList;
	}
	/**
	 * Private method for adding the tree to the list
	 * @param root
	 */
	private List<T> addToListInorder(Node<T> root,List<T> inorderList) {
		if(root==null)return inorderList;
		addToListInorder(root.left,inorderList);
		inorderList.add(root.data);
		addToListInorder(root.right,inorderList);
		return inorderList;
	}
	
	/**
	 * public method to get the contents of the tree elements as pairs
	 * @return
	 */
	public List<HashMap<String, String>> getTreeContents() {
		List<HashMap<String,String>> inorderList=new LinkedList<>();
		addToListInorderContents(this.root,inorderList);
		return inorderList;
	}
	/**
	 * private method for adding tree elements contents in order to the list
	 * @param root
	 * @param inorderList
	 * @return
	 */
	private List<HashMap<String,String>> addToListInorderContents(Node<T> root,List<HashMap<String,String>> inorderList) {
		if(root==null)return inorderList;
		addToListInorderContents(root.left,inorderList);
		HashMap<String,String> hm=new HashMap<>();
		hm.put("data",root.data.toString());
		hm.put("title",root.title);
		hm.put("views", getViewsOnTheObject(root.data.toString())+"");
		if(root.title!=null)
			inorderList.add(hm);
		addToListInorderContents(root.right,inorderList);
		return inorderList;
	}
	
	private int getViewsOnTheObject(String data) {
		try {
			FileInputStream fin=new FileInputStream(data+".ser");
			ObjectInputStream inputStream=new ObjectInputStream(fin);
			article objArticle=(article)inputStream.readObject();
			return objArticle.getSizeOfViewsSet();
		}
		catch (Exception e) {
			return 0;
		}
	}
	
}
