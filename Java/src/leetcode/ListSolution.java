package leetcode;

public class ListSolution {

	/**
	 * 检测链表中是否有
	 * @param head 链表的头
	 * @return 是否有cycle
	 */
	public static boolean hasCycle(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Given a sorted linked list, delete all duplicates such that each element appear only once.
	 * @param head the head of a sorted list
	 * @return a list that each element only appears once
     */
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null){
			return  head;
		}
		while (head.next != null && head.next.val == head.val) {
			head.next = head.next.next;
		}
		deleteDuplicates(head.next);
		return head;
	}

	public void printList(ListNode head){
		if (head == null)
			return;
		System.out.print(head.val+"\t");
		printList(head.next);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListSolution listSolution = new ListSolution();
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(1);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(3);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = null;
		System.out.print("before list:\t");
		listSolution.printList(head);
		System.out.print("\nafter list:\t");
		listSolution.printList(listSolution.deleteDuplicates(head));
	}

}

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) {
      val = x;
      next = null;
   }
}