import java.awt.geom.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
	// 
	// Script author @Warrpy
	// 
public class MicroPong extends JFrame {
	public MicroPong() {
		setTitle("MicroPong Score: 0:0");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new Core(this));
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					Core.move_down = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					Core.move_up = true;
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					Core.move_down = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					Core.move_up = false;
				}
			}
		});
	}

	public static void main(String[] args) {
		new MicroPong();
	}
}

class Core extends JComponent {

	static boolean move_down, move_up, bot;
	int player_pos, bot_pos, width, height, player_score, bot_score, level, bot_speed = 1;
	float rect_x = 200, rect_y = 100, speed_x = -1, speed_y = -1, speed_factor = 1;

	public Core(JFrame frame) {
		var update = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 
				// Check out the MicroBounce source code to better understand the motion logic.
				//
				width = getWidth();
				height = getHeight();

				if (width == 0) {
					width = 400;
					height = 226;
				}

				if (move_down) {
					player_pos += 1;
				}
				if (move_up) {
					player_pos -= 1;
				}
				if (rect_x <= 15 && (rect_y + 15 > player_pos && rect_y < player_pos + 40)) {
					speed_x *= -1;
					bot = true;
				}

				if (rect_x >= width - 30 && (rect_y + 15 > bot_pos && rect_y < bot_pos + 40)) {
					speed_x *= -1;
					bot = false;
				}

				if (rect_x <= 0) {
					bot_score += 1;
					speed_x *= -1;
					bot = true;
					update_score(frame);
				}

				if (rect_x >= (width - 15)) {
					player_score += 1;
					speed_x *= -1;
					bot = false;
					update_score(frame);
				}

				if (rect_y <= 0 || rect_y >= (height - 15)) {
					speed_y *= -1;
				}
				rect_x += speed_x * speed_factor;
				rect_y += speed_y * speed_factor;

				if (bot) {
					// Bot logic.
					if (bot_pos == (int) rect_y - 13) {
						bot_pos = (int) rect_y - 13;
					}
					if (bot_pos < (int) rect_y - 13) {
						bot_pos += bot_speed;
					}
					if (bot_pos > (int) rect_y - 13) {
						bot_pos -= bot_speed;
					}
					level += 1;
					if (level > 300) {
						speed_factor += 0.1f;
						level = 0;
					}
					if (player_score == 4) {
						// The bot enters beast mode.
						bot_speed = 2;
					}
					if (bot_score == 5 || player_score == 5) {
						player_score = 0;
						bot_score = 0;
						bot_speed = 1;
						update_score(frame);
					}
				}
				repaint();
			}
		});
		update.start();
	}

	public void update_score(JFrame frame) {
		frame.setTitle("MicroPong Score: " + player_score + ":" + bot_score);
		rect_x = 100;
		rect_y = 50;
		speed_factor = 1;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g.fillRect(0, player_pos, 15, 40);
		g.setColor(Color.white);
		g2d.fill(new Rectangle2D.Float(rect_x, rect_y, 15, 15));
		g.setColor(Color.red);
		g.fillRect(width - 15, bot_pos, 15, 40);
		getToolkit().sync();
	}
}
