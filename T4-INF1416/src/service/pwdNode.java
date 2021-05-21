package service;

import model.User;

public class pwdNode {
	
	public static boolean verificationResult = false;

 public static class Node {
     public String key;
     public Node left, center, right;

     public Node(String key)
     {
         this.key = key;
         left = null;
         center = null;
         right = null;
     }
     
     boolean isLeaf() {
         return left == null ? right == null : false;
       }
 }
 
 public static void insertPwdNode(Node node, String[] onLeft, String[] onCenter, String[] onRight, int length, int index) {
	   	if(length == 0) return;
	   	
	   	node.left = new Node(onLeft[index]);
	   	node.right = new Node(onRight[index]);
	   	node.center = new Node(onCenter[index]);
	   	
	   	insertPwdNode(node.left, onLeft, onCenter, onRight, length-1, index+1);
		insertPwdNode(node.right, onLeft, onCenter, onRight, length-1, index+1);
		insertPwdNode(node.center, onLeft, onCenter, onRight, length-1, index+1);
 }
 
 public static void printNode(Node pwdNode)
 {
     if (pwdNode == null)
         return;
     System.out.println(pwdNode.key);
     
     if (pwdNode.left == null || pwdNode.center == null || pwdNode.right == null) {
     	return;
     }
         
 	System.out.print(pwdNode.left.key);
 	System.out.print(pwdNode.center.key);
 	System.out.print(pwdNode.right.key);
 	System.out.println("\n");
 	printNode(pwdNode.left);
 	printNode(pwdNode.center);
 	printNode(pwdNode.right);
 }
 
 public static void checkPwdNodes(Node node, String st, User user) {
	    
	    if (node == null) {
	    	if(dbConnect.checkUserPassword(st, user)) {
	    		// System.out.println("\nAchou a senha");
	    		verificationResult = true;
	    	}
	    	
	    	return;
	    }
	    st += node.key;
	    
	    checkPwdNodes(node.left, st, user);
	    checkPwdNodes(node.center, st, user);
	    checkPwdNodes(node.right, st, user);
	    
 }
	 
}

