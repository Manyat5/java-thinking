package sort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ������
 * 
 * @author Administrator
 *
 */
public class Heap  {
	int val;
	Heap left;
	Heap right;
	Heap parent;
	
	public Heap(int val) {
		this.val=val;
	}
	/**
	 * ȷ�������Ϊ��
	 * @param root
	 */
	private static void ensureHeap(Heap root){
		if(root==null) return;
		if(root.left!=null&&root.val<root.left.val) {
			exchangeVal(root, root.left);
			ensureHeap(root.parent);
		}
		ensureHeap(root.left);
		if(root.right!=null&&root.val<root.right.val) {
			exchangeVal(root, root.right);
			ensureHeap(root.parent);
		}
		ensureHeap(root.right);
	}
	
	/**
	 * ������������ֵ
	 */
	private static void exchangeVal(Heap h1,Heap h2) {
		int temp=h1.val;
		h1.val=h2.val;
		h2.val=temp;
	}
	/**
	 * ����һ����
	 */
	public static Heap arrayToHeap(int[] nums) {
		Heap root = arrayToHeap(nums, 0);
		ensureHeap(root);
		return root;
	}
	
	private static Heap arrayToHeap(int[] nums,int i) {
		if(i>=nums.length||i<0) return null;
		Heap root = new Heap(nums[i]);
		root.left=arrayToHeap(nums, 2*i+1);
		if(root.left!=null) root.left.parent=root;
		root.right=arrayToHeap(nums, 2*i+2);
		if(root.right!=null) root.right.parent=root;
		return root;
	}
	
	/**
	 * �����������ӡֵ
	 * @param
	 */
	public static void CengXu(Heap root) {
		Queue<Heap> queue=new LinkedList<Heap>();
		queue.add(root);
		Heap left;Heap right;
		while(!queue.isEmpty()) {
			Heap node = queue.poll();
			System.out.print(" "+node.val);
			if((left=node.left)!=null) queue.add(left);
			if((right=node.right)!=null) queue.add(right);
		}
	}
	
	/**
	 * ȡ���������ֵ���������µ�ֵȡ����
	 */
	public int poll() {
		int result=val;
		//�����һ��node�ƶ���root��
		Heap bottom=getBottom();
		if(bottom.parent!=null) {//�Ƴ�ָ��
			if(bottom.parent.left!=null&&bottom.parent.left.equals(bottom)) 
				bottom.parent.left=null;
			else if(bottom.parent.right!=null&&bottom.parent.right.equals(bottom)) 
				bottom.parent.right=null;
		}
		bottom.parent=null;
		this.val=bottom.val;
		bottom=null;
		ensureHeap(this);
		return result;
	}
	
	/**
	 * ������ֵŲ��root
	 */
	public Heap getBottom() {
		if(right!=null) return right.getBottom();
		if(left!=null) return left.getBottom();
		Heap result=this;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Heap temp=(Heap)obj;
		return this.val==temp.val;
	}
	/**
	 * ���������
	 */
	public static void heapSort(int[] nums) {
		Heap root=arrayToHeap(nums);
		for (int i = nums.length-1; i>=0; i--) {
			nums[i]=root.poll();
		}
	}
	/**
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		int[] nums=new int[] {1,6,8,2,43,78,23};
		heapSort(nums);
		for (int i = 0; i < nums.length; i++) {
			System.out.print(" "+nums[i]);
		}
	}
}
