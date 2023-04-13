package movie.reservation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import movie.domain.Movie;

public class Movie_detail extends JPanel{
	ReservatePage reservatePage;
	
	JLabel la_intro;
	JLabel la_name;
	JLabel la_genre;
	JLabel la_grade;
	JLabel la_release;
	
	JTextField t_name;
	JTextField t_genre;
	JTextField t_grade;
	JTextField t_release;
	JPanel p_poster;
	JButton bt_prev;
	
	Image image;
	
	boolean detailFlag=false;
	
	public Movie_detail(ReservatePage reservatePage) {
		this.reservatePage = reservatePage;
		
		
		la_intro = new JLabel("Select Movie");
		la_name = new JLabel("Title");
		la_genre = new JLabel("Genre");
		la_grade = new JLabel("Grade");
		la_release = new JLabel("Release Year");
		t_name = new JTextField();
		t_genre = new JTextField();
		t_grade = new JTextField();
		t_release = new JTextField();
		p_poster = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 150, 200);
				g2.drawImage(image, 0, 0, 240, 200, Movie_detail.this);
			}
		};
		bt_prev = new JButton("Select");
		bt_prev.setFont(new Font("Times New Roman",Font.BOLD,15));
		
		la_intro.setPreferredSize(new Dimension(280, 30));
		la_intro.setFont(new Font("Times New Roman", Font.BOLD, 25));
		la_intro.setHorizontalAlignment(SwingConstants.CENTER);
		
		Dimension la = new Dimension(50,20);
		la_name.setPreferredSize(new Dimension(la));
		la_genre.setPreferredSize(new Dimension(la));
		la_grade.setPreferredSize(new Dimension(la));
		la_release.setPreferredSize(new Dimension(la));
		
		Font la_font = new Font("Times New Roman",Font.BOLD,15);
		la_name.setFont(la_font);
		la_genre.setFont(la_font);
		la_grade.setFont(la_font);
		la_release.setFont(la_font);
		
		
		Dimension d = new Dimension(220, 20);
		t_name.setPreferredSize(d);
		t_genre.setPreferredSize(d);
		t_grade.setPreferredSize(d);
		t_release.setPreferredSize(d);
		p_poster.setPreferredSize(new Dimension(240, 200));
		p_poster.setBackground(Color.black);
		
		Border border = new LineBorder(Color.black);
		this.setBorder(border);
		
		this.setPreferredSize(new Dimension(300,380));
		this.setVisible(detailFlag);
		
		
		this.add(la_intro);
		this.add(la_name);
		this.add(t_name);
		this.add(la_genre);
		this.add(t_genre);
		this.add(la_grade);
		this.add(t_grade);
		this.add(la_release);
		this.add(t_release);
		this.add(p_poster);
		this.add(bt_prev);

		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Movie_detail.this.setVisible(false);
			}
		});
	}
	
	
	
	public void getDetail() {
		String value = (String) reservatePage.table.getValueAt(reservatePage.table.getSelectedRow(), 0);
		System.out.println(value);
		Movie movie = reservatePage.reservationMain.movieDAO.select(Integer.parseInt(value));

		t_name.setText(movie.getTitle());
		t_genre.setText(movie.getGenre());
		t_grade.setText(movie.getGrade());
		t_release.setText(movie.getRelease_year());
		
		detailFlag=true;
		this.setVisible(detailFlag);
		System.out.println("dd");
		preview(movie.getPoster());
	}
	// 이미지 미리보기 가져오기
		public void preview(String posterUrl) {
			try {
				URL url = new URL(posterUrl);
				image = ImageIO.read(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			p_poster.repaint();
		}
}
