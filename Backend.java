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
                            int pos;
                            if (height==30&&width==16) {
                                pos=ii*width+jj;
                            }
                            else {
                                pos=ii*height+jj;
                            }
                            if (mb[ii][jj]==0) {
                                rec(ii,jj);
                                for (int i=0;i<height;i++) {
                                    for (int j=0;j<width;j++) {
                                        int p=i*height+j;
                                        if (height==30&&width==16) {
                                            p=i*width+j;
                                        }
                                        reveal(i,j,p);
                                    }
                                }
                            } else {
                            JLabel text=new JLabel();
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
                                        text.addMouseListener(new MouseListener() {
                                            public void mouseClicked(MouseEvent me) {
                                                if (me.getClickCount()==2) {
                                                    for (int k=0;k<8;k++) {
                                                        int ni=ii+di[k];
                                                        int nj=jj+dj[k];
                                                        if (ni>=0&&ni<height&&nj>=0&&nj<width&&board[ni][nj] instanceof JButton&&
                                                                !board[ni][nj].getText().equals("F")) {
                                                            int p=ni*height+nj;
                                                            if (height==30&&width==16) {
                                                                p=ni*width+nj;
                                                            }
                                                            gamep.remove(board[ni][nj]);
                                                            board[ni][nj]=null;
                                                            gamep.revalidate();
                                                            JLabel t=new JLabel();
                                                            if (mb[ni][nj]!=0) {
                                                                t.setText("      M");
                                                                if (mb[ni][nj]!=9) {
                                                                    t.setText("      "+Integer.toString(mb[ni][nj]));
                                                                }
                                                                gamep.add(t,p);
                                                            }
                                                            else {
                                                                t.setText("");
                                                                gamep.add(t,p);
                                                            }
                                                            gamep.repaint();
                                                        }
                                                    }
                                                }
                                            }

                                            public void mousePressed(MouseEvent me) {}

                                            public void mouseReleased(MouseEvent me) {}

                                            public void mouseEntered(MouseEvent me) {}

                                            public void mouseExited(MouseEvent me) {}
                                        });
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
                            }}
                        }
                    }

                    public void mouseReleased(MouseEvent me) {}

                    public void mouseEntered(MouseEvent me) {}

                    public void mouseExited(MouseEvent me) {}
                });
            }
        }
    }
    
    public void reveal(int i,int j,int p) {
        if (this.gamep.getComponent(p) instanceof JLabel) {
            JLabel t=(JLabel)this.gamep.getComponent(p);
            if (t.getText().equals("")) {
                for (int k=0;k<4;k++) {
                    int ni=i+di[k];
                    int nj=j+dj[k];
                    if (ni>=0&&ni<height&&nj>=0&&nj<width) {
                        int pi=ni*height+nj;
                        if (height==30&&width==16) {
                            pi=ni*width+nj;
                        }
                        if (this.gamep.getComponent(pi) instanceof JButton) {
                            this.gamep.remove(this.board[ni][nj]);
                            this.board[ni][nj]=null;
                            this.gamep.revalidate();
                            this.gamep.add(new JLabel("      "+Integer.toString(mb[ni][nj])),pi);
                            this.gamep.repaint();
                        }
                    }
                }
            }
        }
    }
    
    public void rec(int i,int j) {
        if (i<0||i>=this.height||j<0||j>=this.width||
            this.mb[i][j]!=0||!(this.board[i][j] instanceof JButton)||
            this.board[i][j].getText().equals("F")) {
            return;
        }
        
        this.gamep.remove(this.board[i][j]);
        this.board[i][j]=null;
        this.gamep.revalidate();
        int p=i*this.height+j;
        if (height==30&&width==16) {
            p=i*width+j;
        }
        this.gamep.add(new JLabel(""),p);
        this.gamep.repaint();
        for (int k=0;k<4;k++) {
            int ni=i+di[k];
            int nj=j+dj[k];
            rec(ni,nj);
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