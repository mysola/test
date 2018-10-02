package start;
//二叉排序树
public class BinaryTree{

	public static void main(String[] args) throws Exception {
		int[] a = { 1, 5, 6, 8, 4, 2, 6, 4, 3 };
		Node n = null;
		for (int i : a) {
			n = insertTree(n, i);
		}
		findCommonParNode(n,2,8);
//		perOrderTran(n);
//		String perOrder = "12473568";
//		String InOrder = "47215386";
//		n = createTree(perOrder, InOrder);
//		perOrderTran(n);
//		System.out.println("------");
//		InOrderTran(n);
	}

	private static int deep1 = 0;
	private static int deep2 = 0;

	public static Node findCommonParNode(Node root, int target1, int target2){
		if (root == null)
			return null;
		if(root.val ==target1||root.val ==target2){
			System.out.println(deep1);
			return root;
		}
		deep1++;
		Node left = findCommonParNode(root.lchild,target1,target2);
		Node right = findCommonParNode(root.rchild,target1,target2);
		deep1--;
		if(left!=null&&right!=null){
			System.out.println(deep1);
			return root;
		}
		return left==null?right:left;
	}



	public static void perOrderTran(Node n) {
		if (n == null)
			return;
		System.out.println(n.val);
		perOrderTran(n.lchild);
		perOrderTran(n.rchild);
		return;
	}

	public static void InOrderTran(Node n) {
		if (n == null)
			return;
		InOrderTran(n.lchild);
		System.out.println(n.val);
		InOrderTran(n.rchild);
		return;
	}

	public static Node insertTree(Node n, int val) {
		if (n == null) {
			Node n1 = new Node();
			n1.lchild = n1.rchild = null;
			n1.val = val;
			return n1;
		}
		if (val > n.val) {
			n.rchild = insertTree(n.rchild, val);
		} else {
			n.lchild = insertTree(n.lchild, val);
		}
		return n;
	}

	public static Node createTree(String perOrder, String inOrder)
			throws Exception {
		if (perOrder == null || inOrder == null)
			throw new Exception("");
		if (perOrder.length() == 0 && inOrder.length() == 0)
			return null;
		if (perOrder.length() != inOrder.length())
			throw new Exception("");
		char rootValue = perOrder.charAt(0);
		Node root = new Node();
		root.lchild = root.rchild = null;
		root.val = Integer.parseInt(String.valueOf(rootValue));
		int rootInOrder = inOrder.indexOf(rootValue);
		if (rootInOrder > 0) {
			root.lchild = createTree(perOrder.substring(1, rootInOrder+1),
					inOrder.substring(0, rootInOrder));
		}
		if (rootInOrder < inOrder.length() - 1) {
			root.rchild = createTree(
					perOrder.substring(rootInOrder + 1, perOrder.length()),
					inOrder.substring(rootInOrder + 1, inOrder.length()));
		}
		return root;
	}
}

class Node {
	Node lchild;
	Node rchild;
	int val;
}
