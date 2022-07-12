import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MicroPong extends JFrame{
public MicroPong(){
setTitle("MicroPong | Score: 0:0");
setSize(400, 250);setLocationRelativeTo(null);
setDefaultCloseOperation(EXIT_ON_CLOSE);
add(new Core(this));setVisible(true);
addKeyListener(new KeyAdapter(){
public void keyPressed(KeyEvent e){
if (e.getKeyCode()==KeyEvent.VK_DOWN)Core.md=true;
if (e.getKeyCode()==KeyEvent.VK_UP)Core.mu=true;}
public void keyReleased(KeyEvent e){
if (e.getKeyCode()==KeyEvent.VK_DOWN)Core.md=false;
if (e.getKeyCode()==KeyEvent.VK_UP)Core.mu=false;}});}
public static void main(String[] args){
new MicroPong();}}
class Core extends JComponent{
static boolean md,mu,bt;
int py,by,w,h,p,b,lv,bs=1;
float rx=200,ry=100,sx=-1,sy=-1,sf=1;
public Core(JFrame f){
var t = new Timer(16, new ActionListener(){
public void actionPerformed(ActionEvent e){
w=getWidth();h=getHeight();
if(w==0){w=400;h=226;}if(md)py+=1;if(mu)py-=1;
if(rx<=15&&(ry+15>py&&ry<py+40)){sx*=-1;bt=true;}
if(rx>=w-30&&(ry+15>by&&ry<by+40)){sx*=-1;bt=false;}
if(rx<=0){b+=1;sx*=-1;s(f);bt=true;}
if(rx>=(w-15)){p+=1;sx*=-1;s(f);bt=false;}
if(ry<=0||ry>=(h-15))sy*=-1;
rx+=sx*sf;ry+=sy*sf;if(bt){
if(by==(int)ry-13)by=(int)ry-13;
if(by<(int)ry-13)by+=bs;
if(by>(int)ry-13)by-=bs;}lv+=1;
if(lv>300){sf+=0.1f;lv=0;}
if(p==4) bs=2;if(b==5||p==5){p=0;b=0;bs=1;s(f);}
repaint();}});t.start();}
public void s(JFrame f){
f.setTitle("MicroPong | Score: "+p+":"+b);
rx=100;ry=50;sf=1;}
public void paint(Graphics g){
Graphics2D g2d = (Graphics2D) g;
g.setColor(Color.green);g.fillRect(0,py,15,40);
g.setColor(Color.white);
g2d.fill(new Rectangle2D.Float(rx,ry,15,15));
g.setColor(Color.red);g.fillRect(w-15,by,15,40);
getToolkit().sync();}}
