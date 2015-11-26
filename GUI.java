import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame {
    private GameBoard b;
    
    public GUI() {
        super("Minesweeper");
        this.b=new GameBoard(9,9,10,this);
        setPreferredSize(new Dimension(390,500));
        createWidgets(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void createWidgets(JFrame frame) {
        JPanel center=new JPanel();
        JMenuBar menubar=new JMenuBar();
        JMenu menu=new JMenu("Settings");
        JMenuItem easy=new JMenuItem("Easy");
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setSize(390,500);
                b=new GameBoard(9,9,10,frame);
                center.removeAll();
                center.add(b.getBoard());
                center.revalidate();
                center.repaint();
            }
        });
        JMenuItem medium=new JMenuItem("Medium");
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setSize(690,800);
                b=new GameBoard(16,16,40,frame);
                center.removeAll();
                center.add(b.getBoard());
                center.revalidate();
                center.repaint();
            }
        });
        JMenuItem hard=new JMenuItem("Hard");
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setSize(720,1240);
                b=new GameBoard(30,16,99,frame);
                center.removeAll();
                center.add(b.getBoard());
                center.revalidate();
                center.repaint();
            }
        });
        center.add(b.getBoard());
        menu.add(easy);
        menu.add(medium);
        menu.add(hard);
        menubar.add(menu);
        JPanel panel=new JPanel(new BorderLayout());
        JPanel top=new JPanel(new GridLayout(2,1));
        JPanel toolbar=new JPanel();
        toolbar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JButton newb=new JButton("New");
        newb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                GameBoard bt=null;
                if (b.getHeight()==b.getWidth()&&b.getHeight()==9) {
                    bt=new GameBoard(9,9,10,frame);
                }
                else if (b.getHeight()==b.getWidth()&&b.getHeight()==16) {
                    bt=new GameBoard(16,16,40,frame);
                }
                else {
                    bt=new GameBoard(30,16,99,frame);
                }
                center.removeAll();
                center.add(bt.getBoard());
                center.revalidate();
                center.repaint();
            }
        });
        toolbar.add(newb);
        top.add(menubar);
        top.add(toolbar);
        panel.add(BorderLayout.NORTH,top);
        panel.add(BorderLayout.CENTER,center);
        add(panel);
        pack();
    }
    
    public static void main(String[] args) {
        new GUI();
    }
}