package myUtils;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * sorted linked list implementation, it is required that inserted elements implements Comparable interface
 * <br> to iterate through the elements of this list you have to do this:
 * call beginIteration() method to initiate the iteration then get the objects using next() method
 * this was done to isolate the user from how the list is lineked or even works
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MySortedList{
    protected Node head;
    private Node curNode;
     public MySortedList(){
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
      * the inserted object must implement Comparable interface or a runtime exception ClassCastException will rise
      * @param obj Object
      * @return Object
      */
     public Object insert(Object obj){
         if(head==null){ //first element
             head = new Node(obj, null);
             return obj;
         }

         //check if it's less than the first element
         if((((Comparable)obj).compareTo(head.obj))<=0){
             Node newNode=new Node(obj,head);
             head=newNode;
             return obj;
         }

         Node temp=head;
         while(temp.next!=null){
             if((((Comparable)obj).compareTo(temp.next.obj))<=0){
                 Node newNode=new Node(obj,temp.next);
                 temp.next=newNode;
                 return obj;
             }
             temp=temp.next;
         }

         temp.next=new Node(obj,null); //final element
         return obj;
     }

     /**
      * not implemented yet
      * @param obj Object
      * @return Object
      */
     public Object remove(Object obj){
         return obj;
     }

     /**
      * starts the iteration from the head of this list
      */
     public void beginIteration(){
         curNode=head;
     }

     /**
      * get next object in the current iteration
      * @return Object
      */
     public Object next(){
         Object obj=null;
         if(curNode!=null){
             obj=curNode.obj;
             curNode=curNode.next;
         }
         return obj;
     }

     /**
      * find an object in the list, the passed object must implement Comparable interface
      * the returned object is the first the equals() this object in the list
      * @param obj Object
      * @return Object
      */
     public Object find(Object obj){
         Node temp=head;
         while(temp!=null){
             if(temp.obj.equals(obj))
                 return temp.obj;
             temp=temp.next;
         }
         return null;
     }

     /**
      * do a nice concatenation
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
}
