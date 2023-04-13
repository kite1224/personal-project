package movie.reservation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import movie.domain.Movie;

public class SlidePanel extends JPanel {
	ReservationMain reservationMain;
	ArrayList<Image> imageList = new ArrayList<Image>();

	Thread loopThread;

	double x;
	//double a = 0.05;
	double velX=-7;

	public SlidePanel(ReservationMain reservationMain) {
		this.reservationMain = reservationMain;
		this.setBackground(Color.cyan);
		this.setPreferredSize(new Dimension(700, 150));

		loopThread = new Thread() {
			public void run() {
				while (true) {
					tick();
					render();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopThread.start();
	}
	
	public void tick() {
		x+=velX;
		
	}

	public void render() {
		repaint();
	}

	public void createImage() {
		for (int i = 1; i < 9; i++) {
			Movie movie = (Movie) reservationMain.movieDAO.select(i);
			// System.out.println(movie.getPoster());
		
			try {
				URL url = new URL((String) movie.getPoster());
				Image image = ImageIO.read(url);
				imageList.add(image);
				// System.out.println("이미지 개수 "+ imageList.size());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, 700, 150);

		for (int i = 0; i < imageList.size(); i++) {
			Image img = imageList.get(i);
			g2.drawImage(img, (int) x + i * 100, 0, 100, 150, null);
			// System.out.println("paintmethod img is "+img);
			if(x<-800) {
				x=0;
				g2.drawImage(img, (int) x + i * 100, 0, 100, 150, null);
			}
			
		}	for (int i = 0; i < imageList.size(); i++) {
			Image img = imageList.get(i);
			g2.drawImage(img, 800+(int) x + i * 100, 0, 100, 150, null);
			
			if(x<-800) {
				x=0;
				g2.drawImage(img, 800+(int) x + i * 100, 0, 100, 150, null);
			}
		}

	}
}
