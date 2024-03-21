package hungrystudents.core;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 * a student knows all about him self, his own info and his bill, and his school too
 * <br> it also can draw it self
 * <br> this class implements Comparable interface because it will be added to a MySortedList object
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class Student implements Comparable{
    public String name;
    public int ouncesSalad;
    public String entreeName;
    public String dessertName;
    public School school; //reference school
    private double bill=0; //$

    //coordinates of the graphical figure
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    Color color=new Color(220,204,202);

    public Student() {
    }

    /**
     * compareing two students using names (in the same school)
     * @param obj Object
     * @return int
     */
    public int compareTo(Object obj){
        return name.compareTo(((Student)obj).name);
    }

    /**
     * view it in  a fine way
     * @return String
     */
    public String toString(){
        return "\t"+name+" --->["+entreeName+","+dessertName+","+ouncesSalad+"]";
    }

    /**
     * depending on his requested food the bill is calculated
     * @return double
     */
    public double calcBill(){
        if(entreeName.equalsIgnoreCase("chicken"))
            bill+=Prices.chicken;
        else if(entreeName.equalsIgnoreCase("fishsticks"))
            bill+=Prices.fishsticks;
        else if(entreeName.equalsIgnoreCase("lasagna"))
            bill+=Prices.lasagna;

        if(dessertName.equalsIgnoreCase("cheesecake"))
            bill+=Prices.cheesecake;
        else if(dessertName.equalsIgnoreCase("pudding"))
            bill+=Prices.pudding;

        bill+=Prices.salad*ouncesSalad;
        return bill;
    }

    /**
     * return the bill value [call it after calculation]
     * @return double
     */
    public double getBill(){
        return bill;
    }

    /**
     * draw a student in his space
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
       g.setColor(color);
       g.fill3DRect(x,y,width,height,true);
       g.setColor(Color.BLACK);
       g.drawString(""+name,x,(int)(y+height/4));
       g.drawString(""+school.name,x,(int)(y+height/2));
       //draw colors
       int colorX=x;
       int colorY=y+height*2/3;
       int colorWidth=width/2;
       int colorHeight=height/3;
       g.setColor(school.colors[0]);
       g.fill3DRect(colorX,colorY,colorWidth,colorHeight,true);
       g.setColor(school.colors[1]);
       g.fill3DRect(colorX+colorWidth,colorY,colorWidth,colorHeight,true);
   }

   /**
    * control access to coords private data
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
    }

}
