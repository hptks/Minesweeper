import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameBoard {
    private int height,width,mines;
    private JButton[][] board;
    private int[][] mb;
    private JPanel gamep;
    private final int di[]={+1, -1, 0, 0, -1, -1, +1, +1};
    private final int dj[]={0, 0, +1, -1, +1, -1, +1, -1};
    
    public GameBoard(int height,int width,int mines,JFrame frame) {
        this.mb=new int[height+1][width+1];
        this.height=height;
        this.width=width;
        this.mines=mines;
        this.board=new JButton[this.height+1][this.width+1];
        this.gamep=new JPanel(new GridLayout(this.height,this.width));
        Dimension d=null;
        if (height==9&&width==9) {
            d=new Dimension(360,360);
        }
        else if (height==16&&width==16) {
            d=new Dimension(660,660);
        }
        else if (height==30&&width==16) {
            d=new Dimension(700,1200);
        }
        this.gamep.setPreferredSize(d);
        makeStuff(frame);
    }
    
    public void makeStuff(JFrame frame) {
        for (int i=0;i<this.height;++i) {
            for (int j=0;j<this.width;++j) {
                this.board[i][j]=new JButton();
                this.mb[i][j]=0;
            }
        }
        
        for (int i=0;i<this.height;i++) {
            for (int j=0;j<this.width;j++) {
                this.gamep.add(this.board[i][j]);
            }
        }
        
        Random r=new Random();
        for (int i=0;i<this.mines;i++) {
            int x,y;
            do {
                x=r.nextInt(this.height);
                y=r.nextInt(this.width);
            } while (this.mb[x][y]!=0);
            this.mb[x][y]=9;
        }
        
        for (int i=0;i<this.height;i++) {
            for (int j=0;j<this.width;j++) {
                if (this.mb[i][j]!=9) {
                    int countMines=0;
                    for (int k=0;k<8;k++) {
                        int ni=i+di[k];
                        int nj=j+dj[k];
                        if (ni>=0&&ni<this.height&&nj>=0&&nj<this.width) {
                            if (mb[ni][nj]==9) {
                                countMines++;
                            }
                        }
                    }
                    this.mb[i][j]=countMines;
                }
            }
        }
        
        for (int i=0;i<this.height;i++) {
            for (int j=0;j<this.width;j++) {
                final int ii=i; final int jj=j;
                this.board[i][j].addMouseListener(new MouseListener() {
                    public void mouseClicked(MouseEvent me) {}
                    
                    public void mousePressed(MouseEvent me) {
                        if (me.getButton()==MouseEvent.BUTTON3) {
                            if (board[ii][jj].getText().equals("")) {
                                board[ii][jj].setText("F");
                            }
                            else board[ii][jj].setText("");
                        }
                        if (me.getButton()==MouseEvent.BUTTON1) {
                            JLabel text=new JLabel();
                            int pos;
                            if (height==30&&width==16) {
                                pos=ii*width+jj;
                            }
                            else {
                                pos=ii*height+jj;
                            }
                            if (!board[ii][jj].getText().equals("F")) {
                                if (mb[ii][jj]!=0) {
                                    if (mb[ii][jj]==9) {
                                        gamep.remove(board[ii][jj]);
                                        board[ii][jj]=null;
                                        gamep.revalidate();
                                        text.setText("      M");
                                        gamep.add(text,pos);
                                        for (int i=0;i<height;i++) {
                                            for (int j=0;j<width;j++) {
                                                if (board[i][j] instanceof JButton) {
                                                    gamep.remove(board[i][j]);
                                                    board[i][j]=null;
                                                    gamep.revalidate();
                                                    if (height==30&&width==16) {
                                                        gamep.add(new JButton(""),i*width+j);
                                                    }
                                                    else {
                                                        gamep.add(new JButton(""),i*height+j);
                                                    }
                                                    gamep.repaint();
                                                }
                                            }
                                        }
                                        JOptionPane.showMessageDialog(frame,"Game over!");
                                        gamep.repaint();
                                    }
                                    else {
                                        gamep.remove(board[ii][jj]);
                                        board[ii][jj]=null;
                                        gamep.revalidate();
                                        text.setText("      "+Integer.toString(mb[ii][jj]));
                                        gamep.add(text,pos);
                                        gamep.repaint();
                                    }
                                }
                                else {
                                    gamep.remove(board[ii][jj]);
                                    board[ii][jj]=null;
                                    gamep.revalidate();
                                    text.setText("");
                                    gamep.add(text,pos);
                                    gamep.repaint();
                                }
                            }
                        }
                    }

                    public void mouseReleased(MouseEvent me) {}

                    public void mouseEntered(MouseEvent me) {}

                    public void mouseExited(MouseEvent me) {}
                });
            }
        }
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public JPanel getBoard() {
        return this.gamep;
    }
}