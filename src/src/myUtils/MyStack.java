package myUtils;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * Stack class provides general class structure and functionality storing
 * objects of any kind and maintaining them as a linked list
 *<br> elements of the stack class are Node objects, how ever they are not gonna
 * be dealt with directly, instead they'll remain hidden and the internal data
 * objects are the ones to be stored and retieved
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MyStack{
    protected Node head;
    private int length=0;
    public MyStack(){
        head=null;
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
     * on the top, push the passed object
     * @param obj Object
     * @return Object
     */
    public Object push(Object obj){
        Node newNode=new Node(obj,head);
        head=newNode;
        length++;
        return obj;
    }

    /**
     * pop the top out of the stack
     * @return Object
     */
    public Object pop(){
        if(head==null)
            return head;

        Node top=head;
        head=head.next;
        if(length>0)
            length--;
        return top.obj;
    }

    /**
     * just see it without poping it
     * @return Object
     */
    public Object peek(){
        return head.obj;
    }

    /**
     * method not required in Java, since the GC does it's work just fine
     * but it's added for formality of the probelm
     */
    public void destroyStack(){

    }

    /**
     * nicely concatenate it's elements
     * @return String
     */
    public String toString(){
        StringBuffer content=new StringBuffer();
        Node temp=head;
        while(temp!=null){
            content.append("\n");
            content.append(temp.obj.toString());
            temp=temp.next;
        }

        return content.toString();
    }

    /**
     * count of elements
     * @return int
     */
    public int getLength(){
        return length;
    }
}


/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * objects of this class will holds a node information which is in this case
 * a formal object which could be any type, thus allowing reusing this code
 * polymorphisim will do it's work when adding the object and casting back is required
 * when retrieving the object back
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
class Node{
    Object obj;
    Node next;
    Node(Object obj,Node next){
        this.obj=obj;
        this.next=next;
    }
}
