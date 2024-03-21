package myUtils;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * elements are addred in the tail side and dequeued on the head side
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MyQueue {
    protected Node head;
    protected Node tail;
    private int length=0;
    public MyQueue() {
        head = null;
        tail = null;
    }

    /**
     * add to tail
     * @param obj Object
     * @return Object
     */
    public Object queue(Object obj) {
        if(tail==null){ //initial item
            tail=new Node(obj,null);
            head=tail;
        }
        else{
            tail.next=new Node(obj,null);
            tail=tail.next;
       //     tail=new Node(obj,tail);
        }
        length++;
        return obj;
    }

    /**
     * remove from head
     * @return Object
     */
    public Object dequeue() {
        Node temp=head;
        if(length>0)
            length--;
        if(head!=null){
            head=head.next;
        }
        else
            return null;
        return temp.obj;
    }

    /**
     * just get head without removing it
     * @return Object
     */
    public Object peek() {
        return head.obj;
    }

    /**
     * check content availability
     * @return boolean
     */
    public boolean isEmpty(){
        if(head==null)
            return true;
        return false;
    }

    /**
     * method not required in Java, since the GC does it's work just fine
     * but it's added for formality of the probelm
     */
    public void destroyStack() {

    }

    /**
     * we maintain a count of the stack elements
     * @return int
     */
    public int getLength(){
        return length;
    }

    /**
     * nicely concatenate it's internal element toString returns
     * @return String
     */
    public String toString() {
        StringBuffer content = new StringBuffer();
        Node temp = head;
        while (temp != null) {
            content.append("\n");
            content.append(temp.obj.toString());
            temp = temp.next;
        }

        return content.toString();
    }

}
