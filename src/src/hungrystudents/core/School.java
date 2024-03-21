package hungrystudents.core;
import myUtils.*;
import java.awt.Graphics2D;
import java.awt.Color;
/**
 * <p>Title: </p>
 *
 * <p>Description:
 * a school has a name and a sorted list of students
 * <br> since schools are added to list them selves they should implement Comparable interface
 * <br> a school knows how to draw it self
 * <br> every school has two colors
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class School implements Comparable{
    public StudentSoretedList studentList=new StudentSoretedList();
    public String name;
    public Color[] colors=new Color[2];
    private double bill;

    //coordinates of the graphical figure
   private int x = 0;
   private int y = 0;
   private int width = 0;
   private int height = 0;
   private int margin=2;

    public School() {
    }

    /**
     * construct school by name only, colors are set to default tell they are set from else where
     * @param name String
     */
    public School(String name){
        this.name=name;
        colors[0]=Color.white;
        colors[0]=Color.white;
    }

    /**
     * control access to coords private data and update student list coords too
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void updateCoords(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        //update children
        studentList.updateCoords(x,y+20,width+margin*2,height-20,(height-20)/4);
    }

    /**
     * equality of two schools will depend only on school name
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj){
        if(obj instanceof School){
            if (((School)obj).name.equalsIgnoreCase(name))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * comparision of two schools depends on their names
     * @param obj Object
     * @return int
     */
    public int compareTo(Object obj){
        return name.compareTo(((School)obj).name);
    }

    /**
     * bill of a school is the sum of it's students bills
     * @return double
     */
    public double calcBill(){
        studentList.beginIteration();
        Student std = null;
        while ((std = (Student) studentList.next()) != null) {
            bill+=std.calcBill();
        }

        return bill;
    }

    /**
     * control access to bill private datda
     * you must call calcBill() first
     * @return double
     */
    public double getBill(){
        return bill;
    }

    /**
     * concatenate the school name and it's student inf too
     * @return String
     */
    public String toString(){
        return name+":"+studentList;
    }

    /**
     * draw school space and call student list to draw it self
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
      g.setColor(Color.LIGHT_GRAY);
      g.fillRoundRect(x,y,width,height,20,20);
      g.setColor(Color.BLACK);
      g.drawString(name,x+5,y+15);
      if(studentList!=null)
          studentList.draw(g);
  }


}
