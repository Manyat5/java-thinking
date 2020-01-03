package tree;

public class RBTrue {
	private final static boolean RED=false;
	private final static boolean BLACK=true;
	
	RBNode root;
	
	class RBNode {
		int val;
		RBNode parent;
		RBNode left;
		RBNode right;
		
		public RBNode(int val) {
			this.val = val;
		}

		public RBNode(int val, RBNode parent, RBNode left, RBNode right) {
			this(val);
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
	}
	
}
