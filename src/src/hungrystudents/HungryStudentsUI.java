package hungrystudents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import hungrystudents.core.*;
import javax.swing.UIManager;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class HungryStudentsUI extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JButton loadBtn = new JButton();
    JPanel northPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JButton stopBtn = new JButton();

    public HungryStudentsUI() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(729, 480));
        setTitle("hungry students simulation");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new
                                        HungryStudentsUI_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         HungryStudentsUI_jMenuHelpAbout_ActionAdapter(this));
        loadBtn.setText("load data and begin simulation");
        loadBtn.addActionListener(new HungryStudentsUI_jButton1_actionAdapter(this));
        northPanel.setBackground(UIManager.getColor("textInactiveText"));
        stopBtn.setText("stop simulation");
        stopBtn.addActionListener(new HungryStudentsUI_stopBtn_actionAdapter(this));
        jLabel1.setText("time step(millis)");
        timeStep.setPreferredSize(new Dimension(50, 20));
        timeStep.setText("500");
        centerPanel.setBackground(Color.black);
        viewBill.setText("view bill");
        viewBill.addActionListener(new HungryStudentsUI_viewBill_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        northPanel.add(jLabel1);
        northPanel.add(timeStep);
        northPanel.add(viewBill);
        northPanel.add(stopBtn);
        northPanel.add(loadBtn);
        contentPane.add(northPanel, java.awt.BorderLayout.NORTH);
        contentPane.add(centerPanel, java.awt.BorderLayout.CENTER);
        setJMenuBar(jMenuBar1);
    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        HungryStudentsUI_AboutBox dlg = new HungryStudentsUI_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
    }

    Controller control=null;
    JTextField timeStep = new JTextField();
    JLabel jLabel1 = new JLabel();
    JButton viewBill = new JButton();
    public void jButton1_actionPerformed(ActionEvent actionEvent) {
        JFileChooser openDlg=new JFileChooser();
        int res=openDlg.showOpenDialog(this);
        if(res==openDlg.APPROVE_OPTION){
            Dimension dim=centerPanel.getSize();
            control=new Controller(openDlg.getSelectedFile().getPath(),dim.width,dim.height,(Graphics2D)(centerPanel.getGraphics()));
            try{
            control.step=Long.parseLong(timeStep.getText());
            control.ui=this;
            }
            catch(Exception ex){
                System.out.println("Error in time step[reset to default]");
                control.step=500;
            }
            control.viewSchoolsData();
            control.beginSimulation();
        }
    }


    public void paint(Graphics g){
        super.paint(g);
        if(Controller.image!=null){
             AffineTransformOp op=new AffineTransformOp(new AffineTransform(),AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            ((Graphics2D)centerPanel.getGraphics()).drawImage(Controller.image,op,0,0);
        }
    }

    public void stopBtn_actionPerformed(ActionEvent actionEvent) {
        if(control!=null)
            control.enableSwitching=!control.enableSwitching;
    }

    public void viewBill_actionPerformed(ActionEvent actionEvent) {
        if(control!=null)
            control.billSchools("bill.txt");
    }

}


class HungryStudentsUI_viewBill_actionAdapter implements ActionListener {
    private HungryStudentsUI adaptee;
    HungryStudentsUI_viewBill_actionAdapter(HungryStudentsUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.viewBill_actionPerformed(actionEvent);
    }
}


class HungryStudentsUI_stopBtn_actionAdapter implements ActionListener {
    private HungryStudentsUI adaptee;
    HungryStudentsUI_stopBtn_actionAdapter(HungryStudentsUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.stopBtn_actionPerformed(actionEvent);
    }
}


class HungryStudentsUI_jButton1_actionAdapter implements ActionListener {
    private HungryStudentsUI adaptee;
    HungryStudentsUI_jButton1_actionAdapter(HungryStudentsUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButton1_actionPerformed(actionEvent);
    }
}


class HungryStudentsUI_jMenuFileExit_ActionAdapter implements ActionListener {
    HungryStudentsUI adaptee;

    HungryStudentsUI_jMenuFileExit_ActionAdapter(HungryStudentsUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class HungryStudentsUI_jMenuHelpAbout_ActionAdapter implements ActionListener {
    HungryStudentsUI adaptee;

    HungryStudentsUI_jMenuHelpAbout_ActionAdapter(HungryStudentsUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
