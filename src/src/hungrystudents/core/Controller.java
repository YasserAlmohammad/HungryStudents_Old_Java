package hungrystudents.core;

import myUtils.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JFrame;
/**
 * <p>Title: </p>
 *
 * <p>Description:
 * this class assembles all other objects to work together and simulate the cafeteria
 * <br>the controller has: hungry students queues, tray stacks, a schools list and musch more
 * <br> all graphical info is held her
 * <br> drawing is done using double buffering, and clip repaint only so:
 * if a tray stack is changed it's alone is redrawn and nothing else, so motion is very smooth and consumes
 * the less proccessing there is.
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class Controller {
    StudentQueue[] hungryStds;
    TrayStack[] trays;
    SchoolSortedList schools;
    long randNumSeed = 0;
    public JFrame ui=null;

    public boolean enableSwitching = true;
    public long step=500; //500 millis by default
    public Graphics2D targetGraphics;
    public int width; //width and height could be updated automatically upon changing the window size of the surface drawn against
    public int height;
    public static BufferedImage image = null;
    public Graphics2D img = null; //imageGraphics


    /**
     * pass a file path to load data from, width and height of the target surface and it's graphics
     * @param dataPath String
     * @param width int
     * @param height int
     * @param g Graphics2D
     */
    public Controller(String dataPath, int width, int height, Graphics2D g) {
        this.width = width;
        this.height = height;
        CafeteriaCoords.calcCoords(width, height);
        hungryStds = new StudentQueue[2];
        hungryStds[0] = new StudentQueue(CafeteriaCoords.hungryStds[0].x,
                                         CafeteriaCoords.hungryStds[0].y,
                                         CafeteriaCoords.hungryStds[0].width,
                                         CafeteriaCoords.hungryStds[0].height);
        hungryStds[1] = new StudentQueue(CafeteriaCoords.hungryStds[1].x,
                                         CafeteriaCoords.hungryStds[1].y,
                                         CafeteriaCoords.hungryStds[1].width,
                                         CafeteriaCoords.hungryStds[1].height);

        trays = new TrayStack[4];
        for (int i = 0; i < 4; i++)
            trays[i] = new TrayStack(CafeteriaCoords.trays[i].x,
                                     CafeteriaCoords.trays[i].y,
                                     CafeteriaCoords.trays[i].width,
                                     CafeteriaCoords.trays[i].height,
                                     CafeteriaCoords.trays[i].width / 60);

        schools = new SchoolSortedList(CafeteriaCoords.schools.x,
                                       CafeteriaCoords.schools.y,
                                       CafeteriaCoords.schools.width,
                                       CafeteriaCoords.schools.height,
                                       CafeteriaCoords.schools.width / 6);
        School s = schools.insert(new School("Eastville"));
        s.colors[0] = Color.BLUE;
        s.colors[1] = Color.YELLOW;

        s = schools.insert(new School("Westburg"));
        s.colors[0] = Color.RED;
        s.colors[1] = Color.GREEN;

        s = schools.insert(new School("Northton"));
        s.colors[0] = Color.ORANGE;
        s.colors[1] = Color.PINK;

        s = schools.insert(new School("Southport"));
        s.colors[0] = Color.ORANGE;
        s.colors[1] = Color.BLUE;

        s = schools.insert(new School("Jahunga"));
        s.colors[0] = Color.RED;
        s.colors[1] = Color.BLUE;

        s = schools.insert(new School("Podunk"));
        s.colors[0] = Color.YELLOW;
        s.colors[1] = Color.GREEN;

        this.targetGraphics = g;
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img = image.createGraphics();

        loadSchoolsData(dataPath);

    }

    /**
     * we assume this: initially students are considered hungry, so they are all added to the hungry students queue
     * later when a student is served he's marked as SERVED and removed from the hungry students queue and added back into
     * his school
     * this method loads the data and calles the tray generation method
     * file is expected to hold information of all students and their meals in the following format:
     *    SeedNumber [ first line only as a seed for the random numbers generation]
     *    StudentName
     *    SchoolName
     *    Number
     *    EntreeName
     *    DessertName
     *    .
     *    .
      *   Done      [ final line only]
      *
     * @param filename String
     */
    public void loadSchoolsData(String filename) {
        /* file is expected to hold information of all students and their meals in the following format:
         SeedNumber [ first line only as a seed for the random numbers generation]
         StudentName
         SchoolName
         Number
         EntreeName
         DessertName
         .
         .
         Done      [ final line only]
         */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            line = reader.readLine();
            //first line: get the seed
            if (line != null) {
                randNumSeed = Integer.parseInt(line);
            }

            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase("DONE"))
                    break;

                Student std = new Student();
                std.name = line;
                String schoolName = reader.readLine();
                std.ouncesSalad = Integer.parseInt(reader.readLine());
                std.entreeName = reader.readLine();
                std.dessertName = reader.readLine();
                std.school = (School) schools.find(new School(schoolName));
                //add student to the prober school and set it's reference
                if (std.school == null) {
                    System.out.println("ERROR:Student without school");
                    return;
                }
                //     std.school.studentList.insert(std); //initially we assume they are in the hungry student queue

                //add the student to the hungry students queue
                if (hungryStds[0].getLength() > hungryStds[1].getLength())
                    hungryStds[1].queue(std);
                else
                    hungryStds[0].queue(std);
            }
        }

        catch (FileNotFoundException ex) {
            System.out.println("file: [" + filename + "] not found");
        } catch (IOException ex) {
            System.out.println("IO ERROR");
        } catch (Exception ex) {
            System.out.println(
                    "Error occured for some reason, input file is corrupted or something");
        }

        generateTrays(randNumSeed);
    }
    /**
     * this method will accept a seed of a random number generator, which will be used to generate a number from 0 and less than 6
     * and choose one of 6 colors and 20 trays of each color to add to the tray stack, which in turn :
     * there are 4 tray stacks in the cafeteria, each will have 30 random trays to hold all trays
     * @param seed long
     */
    public void generateTrays(long seed) {
        /*randomly generate trays 20 of each color
             we got 6 colors, 20 trays of each, 4 stacks of trays, each will have 30 trays so we randomly generate a color and count what we got
         */
        Color[] colors = new Color[6];
        int[] count = new int[6];
        int[] stacks = new int[4];
        Random rand = new Random(seed);
        double randNum = 0;
        colors[0] = Color.BLUE;
        colors[1] = Color.YELLOW;
        colors[2] = Color.RED;
        colors[3] = Color.GREEN;
        colors[4] = Color.ORANGE;
        colors[5] = Color.PINK;
        for (int i = 0; i < 6; i++)
            count[i] = 0;

        //for each stack randomly generate
        for (int i = 0; i < 4; i++) {
            stacks[i] = 0;
            while (stacks[i] < 30) {
                randNum = rand.nextDouble() * 6;
                if ((randNum >= 0) && (randNum < 1) && (count[0] <= 20)) {
                    trays[i].push(new Tray(colors[0]));
                    stacks[i]++;
                } else if ((randNum >= 1) && (randNum < 2) && (count[1] <= 20)) {
                    trays[i].push(new Tray(colors[1]));
                    stacks[i]++;
                } else if ((randNum >= 2) && (randNum < 3) && (count[2] <= 20)) {
                    trays[i].push(new Tray(colors[2]));
                    stacks[i]++;
                } else if ((randNum >= 3) && (randNum < 4) && (count[3] <= 20)) {
                    trays[i].push(new Tray(colors[3]));
                    stacks[i]++;
                } else if ((randNum >= 4) && (randNum < 5) && (count[4] <= 20)) {
                    trays[i].push(new Tray(colors[4]));
                    stacks[i]++;
                } else if ((randNum >= 5) && (randNum < 6) && (count[5] <= 20)) {
                    trays[i].push(new Tray(colors[5]));
                    stacks[i]++;
                }
            }

        }
    }

    /**
     * view school data on the standard output
     */
    public void viewSchoolsData() {
        System.out.println("Schools listed:");
        System.out.println(schools);

        System.out.println("Students in hungry queue[0]");
        System.out.println(hungryStds[0]);
        System.out.println("Students in hungry queue[1]");
        System.out.println(hungryStds[1]);

        /*       System.out.println("Students in each school:");
               schools.beginIteration();
               School s=null;
               while((s=(School)schools.next())!=null){
                   System.out.println(s.name+" "+s.studentList);
               }
         */
    }

    /**
     * fire a new thread to begin the simulation and view motion on the screen
     */
    public void beginSimulation() {
        (new SimulationThread()).start();
    }

    /**
     * each school bills it self
     * @param fileName String
     */
    public void billSchools(String fileName) {
        File out = new File(fileName);
        try {
            PrintWriter print = new PrintWriter(out);

            schools.beginIteration();
            School s = null;
            while ((s = (School) schools.next()) != null) {
                System.out.println("University of:" + s.name);
                //      print.println("University of:" + s.name);
                s.calcBill();
                s.studentList.beginIteration();
                Student std = null;
                while ((std = (Student) s.studentList.next()) != null) {
                    System.out.println("\t"+std.name + ":" + std.getBill());
                    //     print.println(s.name + ":" + s.getBill());
                }
                System.out.println("Total:" + s.getBill());
//                print.println("Total:" + s.getBill());
            }

        } catch (FileNotFoundException ex) {
        }

    }

    /**
     *
     * <p>Title: </p>
     *
     * <p>Description:
     * when this thread runs it does the simulation of the cafeteria tell there are no hungry students left
     * the thread can be stopped by setting the enableSwitching variable of the parent Controller object
     * and view speed can be controlled by the step value of the same object
     * </p>
     *
     * <p>Copyright: Copyright (c) 2006</p>
     *
     * <p>Company: FIT</p>
     *
     * @author Mahmood Shomer
     * @version 1.0
     */
    class SimulationThread extends Thread {
        //accessing the graphics will be syncronized
        AffineTransformOp op = new AffineTransformOp(new AffineTransform(),
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        /**
         * initially we draw the cafeteria it self
         * @param g Graphics2D
         * @param width int
         * @param height int
         */
        public void drawCaf(Graphics2D g, int width, int height) {
            g.setBackground(new Color(0, 0, 0));
            if (hungryStds[0] != null)
                hungryStds[0].draw(g);
            if (hungryStds[1] != null)
                hungryStds[1].draw(g);

            g.setColor(Color.lightGray);
            g.fillRoundRect(CafeteriaCoords.foodPlace.x, CafeteriaCoords.foodPlace.y,
                       CafeteriaCoords.foodPlace.width,
                       CafeteriaCoords.foodPlace.height,30,30);

            for (int i = 0; i < 4; i++) {
                if (trays[i] != null)
                    trays[i].draw(g);
            }

            schools.draw(g);
        }

        /**
         * all changes to the image draw is commited to the screen now
         */
        public void commitScene() {
            targetGraphics.drawImage(image, op, 0, 0);
        }

        /**
         * do the trick
         */
        public void run() {
            drawCaf(img, width, height);
            commitScene();
            step();

            Raw[] raws = new Raw[2];
            raws[0] = new Raw(hungryStds[0], trays[0], trays[1]);
            raws[1] = new Raw(hungryStds[1], trays[2], trays[3]);
            while (enableSwitching) {
                if (raws[0].stdQueue.isEmpty() && raws[1].stdQueue.isEmpty())
                    break;
                for (int i = 0; i < 2; i++) {
                    if (raws[i].stdQueue.isEmpty())
                        continue;
                    if (raws[i].trays1.isEmpty() && raws[i].trays2.isEmpty()) {
                        System.out.println("WAAAAAAAAAAAAAAAAAR");
                        return;
                    }
                    //peek and pop trays tell found
                    Student std = (Student) raws[i].stdQueue.peek();
                    TrayStack first;
                    TrayStack second;
                    //pick the taller stack
                    if (raws[i].trays1.getLength() >= raws[i].trays2.getLength()) {
                        first = raws[i].trays1;
                        second = raws[i].trays2;
                    } else {
                        first = raws[i].trays2;
                        second = raws[i].trays1;
                    }

                    int round = 0;
                    //get a matching tray
                    while (true) {
                        if (first.isEmpty()) {
                            //reverse
                            TrayStack temp = first;
                            first = second;
                            second = temp;
                            if (round == 1) {
                                //pick the top tray and get the hell out of here
                                first.pop();
                                std.school.studentList.insert(std);
                                first.draw(img);
                                std.school.draw(img); //redraw one school
                                step();
                                break;
                            }
                            round = 1;
                        } else {
                            Tray tray = (Tray) first.peek();
                            if (tray.color.equals(std.school.colors[0]) ||
                                tray.color.equals(std.school.colors[1])) {
                                //match found, pop tray away and send student back to school
                                first.pop();
                                std.school.studentList.insert(raws[i].stdQueue.dequeue());
                                raws[i].stdQueue.draw(img);
                                first.draw(img);
                                std.school.draw(img); //redraw one school
                                step();
                                break;
                            } else {
                                second.push(first.pop());
                                first.draw(img);
                                second.draw(img);
                                step();
                            }
                        }
                    }

                }
            }

        }

        /**
         * one step by sleeping for a while and updating the figure drawn
         */
        public void step() {
            try {
                sleep(step);
            } catch (InterruptedException ex) {
            }
            commitScene();
            //the next line is bad, but i had to put it for some troubles in my Sys. [??]
            ui.repaint(0,0,1,1);

        }
    }
}

/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * many main objects exists in the caf and need to be initially calculated here
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Mahmood Shomer
 * @version 1.0
 */
class CafeteriaCoords {
    public static Rectangle[] hungryStds = new Rectangle[2];
    public static Rectangle[] trays = new Rectangle[4];
    public static Rectangle schools = null;
    public static Rectangle foodPlace = null;
    public static Rectangle salad = null;
    public static Rectangle entree = null;
    public static Rectangle dessert = null;

    public static void calcCoords(int width, int height) {
        int x = 0;
        int y = 0;
        int curWidth = 0;
        int curHeight = 0;

        curWidth = (int) (width * 0.5);
        curHeight = (int) (height * 0.16);
        y = height / 3 - height / 16 - curHeight;
        hungryStds[0] = new Rectangle(x, y, curWidth, curHeight);

        y = height / 3 + height / 16;
        hungryStds[1] = new Rectangle(x, y, curWidth, curHeight);

        x = width / 2 + 4;
        y = height / 3 - height / 16 - (int) curHeight * 3 / 2;
        curHeight = height / 3 + height / 16 + (int) curHeight * 3 / 2;
        foodPlace = new Rectangle(x, y, curWidth, curHeight);

        x = x + 2;
        y = height / 3 - height / 16 - (int) (height * 0.20);
        curHeight = (int) (height * 0.08);
        curWidth = (int) (width * 0.5) - 4;
        trays[0] = new Rectangle(x, y, curWidth, curHeight);

        y = height / 3 - height / 16 - (int) (height * 0.04);
        trays[1] = new Rectangle(x, y, curWidth, curHeight);

        y = height / 3 + height / 16 - (int) (height * 0.035);
        trays[2] = new Rectangle(x, y, curWidth, curHeight);

        y = height / 3 + height / 16 + (int) (height * 0.12);
        trays[3] = new Rectangle(x, y, curWidth, curHeight);

        x = 0;
        y = height * 2 / 3 - 5;
        curHeight = height / 3;
        curWidth = (int) (width * 0.5);
        schools = new Rectangle(x, y, curWidth, curHeight);

    }
}

/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * simple class to represent a raw in the caf, a raw consists of a hungry student queue and two stacks of trays
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Mahmood Shomer
 * @version 1.0
 */
class Raw {
    StudentQueue stdQueue;
    TrayStack trays1;
    TrayStack trays2;
    public Raw(StudentQueue queue, TrayStack stack1, TrayStack stack2) {
        stdQueue = queue;
        trays1 = stack1;
        trays2 = stack2;
    }
}
