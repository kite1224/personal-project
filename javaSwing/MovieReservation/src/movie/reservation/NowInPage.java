package movie.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import movie.domain.Movie;

public class NowInPage extends Page{
	//위쪽
	JLabel la_now;
	//서쪽
	JPanel p_west;
	JLabel la_intro;
	JTextField t_name;
	JTextField t_genre;
	JTextField t_grade;
	JTextField t_release;
	JPanel p_poster;
	
	//센터
	JTable table;
	JScrollPane scroll;
	JButton bt_prev;
	
	MovieModel model;
	
	Image image; //포스터 이미지

	
	public NowInPage(ReservationMain reservationMain) {
		super(reservationMain);
		
		
		la_now = new JLabel("Now In Theater");
		p_west = new JPanel();
		la_intro = new JLabel("Introduce");
		t_name = new JTextField("Title");
		t_genre = new JTextField("Genre");
		t_grade = new JTextField("Grade");
		t_release = new JTextField("Release Year");
		p_poster = new  JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.clearRect(0, 0, 150, 200);
				g2.drawImage(image, 0, 0, 150, 200, NowInPage.this);
			}
		};
		
		table = new JTable(model = new MovieModel());
		
		scroll = new JScrollPane(table);
		bt_prev = new JButton("Prev");

		//디자인
		la_now.setPreferredSize(new Dimension(700,60));
		la_now.setFont(new Font("Times New Roman", Font.BOLD, 35));
        la_now.setHorizontalAlignment(SwingConstants.CENTER);
		
        la_intro.setFont(new Font("Times New Roman", Font.BOLD, 15));
        t_name.setFont(new Font("Dotum", Font.BOLD, 10));
        t_genre.setFont(new Font("Times New Roman", Font.BOLD, 15));
        t_grade.setFont(new Font("Times New Roman", Font.BOLD, 15));
        t_release.setFont(new Font("Times New Roman", Font.BOLD, 15));
        bt_prev.setFont(new Font("Times New Roman",Font.BOLD,15));
        
		p_west.setPreferredSize(new Dimension(180,400));
		
		Border border = BorderFactory.createLineBorder(Color.black);
		p_west.setBorder(border);
		
		Dimension d = new Dimension(150,20);
		t_name.setPreferredSize(d);
		t_genre.setPreferredSize(d);
		t_grade.setPreferredSize(d);
		t_release.setPreferredSize(d);
		p_poster.setPreferredSize(new Dimension(150,200));
		p_poster.setBackground(Color.black);
		
		scroll.setPreferredSize(new Dimension(500,400));
		scroll.setBorder(border);
	
		//패널에 부착
		p_west.add(la_intro);
		p_west.add(t_name);
		p_west.add(t_genre);
		p_west.add(t_grade);
		p_west.add(t_release);
		p_west.add(p_poster);
		p_west.add(bt_prev);
		
		add(la_now,BorderLayout.NORTH);
		add(p_west,BorderLayout.WEST);
		add(scroll,BorderLayout.EAST);
	//	add(bt_prev,BorderLayout.EAST);
		
		 getMovieList();
		
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationMain.showHide(reservationMain.MAINPAGE);
			}
		});
		
		//마우스 리스너 > 상세보기 사용
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				getDetail();
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		
	}
	
	public void getDetail() {
		String value = (String)table.getValueAt(table.getSelectedRow(), 0);
		System.out.println(value);
		Movie movie = reservationMain.movieDAO.select(Integer.parseInt(value));
		
		t_name.setText(movie.getTitle());
		t_genre.setText(movie.getGenre());
		t_grade.setText(movie.getGrade());
		t_release.setText(movie.getRelease_year());
		
		preview(movie.getPoster());
	
	}
	//이미지 미리보기 가져오기
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
	

	public void getMovieList() {
		model.movieList = reservationMain.movieDAO.selectAll();
		table.updateUI();
	
	}
}
