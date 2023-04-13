package AdminPage;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import movie.domain.Movie;

import repository.MovieDAO;
import repository.MovieModel;

public class RegistPage extends JPanel {
	JPanel p_total;
	// 위쪽
	JLabel la_now;
	// 서쪽
	JPanel p_west;
	JLabel la_intro;
	JTextField t_name;
	JTextField t_genre;
	JTextField t_grade;
	JTextField t_release;
	JTextField t_poster;

	// 센터
	JTable table;
	JScrollPane scroll;
	JButton bt_regist;
	JButton bt_del;

	MovieModel model;

	Image image; // 포스터 이미지
	// boolean registFlag = false;

	AdminMain adminMain;
	Movie movie;// 추가할 영화를 담을 movie dto
	Movie selectMovie;// 삭제할 영화를 선택했을 때 담을 dto
	MovieDAO movieDAO;

	public RegistPage(AdminMain adminMain) {
		this.adminMain = adminMain;
		movieDAO = new MovieDAO();

		p_total = new JPanel();
		la_now = new JLabel("Movie Regist");
		p_west = new JPanel();
		la_intro = new JLabel("Regist");
		t_name = new JTextField("Title");
		t_genre = new JTextField("Genre");
		t_grade = new JTextField("Grade");
		t_release = new JTextField("Release Year");
		t_poster = new JTextField("Poster_url");

		table = new JTable(model = new MovieModel());
		scroll = new JScrollPane(table);

		bt_regist = new JButton("Regist");
		bt_del = new JButton("Delete");

		p_total.setPreferredSize(new Dimension(700, 450));
		// 디자인
		la_now.setPreferredSize(new Dimension(700, 60));
		la_now.setFont(new Font("Times New Roman", Font.BOLD, 35));
		la_now.setHorizontalAlignment(SwingConstants.CENTER);

		la_intro.setFont(new Font("Times New Roman", Font.BOLD, 10));
		t_name.setFont(new Font("Dotum", Font.BOLD, 10));
		t_genre.setFont(new Font("Times New Roman", Font.BOLD, 10));
		t_grade.setFont(new Font("Times New Roman", Font.BOLD, 10));
		t_release.setFont(new Font("Times New Roman", Font.BOLD, 10));
		t_poster.setFont(new Font("Times New Roman", Font.BOLD, 10));
		bt_regist.setFont(new Font("Times New Roman", Font.BOLD, 15));
		bt_del.setFont(new Font("Times New Roman", Font.BOLD, 15));

		p_west.setPreferredSize(new Dimension(180, 360));

		Border border = BorderFactory.createLineBorder(Color.black);
		p_west.setBorder(border);

		Dimension d = new Dimension(150, 20);
		t_name.setPreferredSize(d);
		t_genre.setPreferredSize(d);
		t_grade.setPreferredSize(d);
		t_release.setPreferredSize(d);
		t_poster.setPreferredSize(d);

		scroll.setPreferredSize(new Dimension(490, 360));
		scroll.setBorder(border);

		// 패널에 부착
		p_west.add(la_intro);
		p_west.add(t_name);
		p_west.add(t_genre);
		p_west.add(t_grade);
		p_west.add(t_release);
		p_west.add(t_poster);
		p_west.add(bt_regist);
		p_west.add(bt_del);

		p_total.add(la_now, BorderLayout.NORTH);
		p_total.add(p_west, BorderLayout.WEST);
		p_total.add(scroll, BorderLayout.EAST);
		add(p_total);

		setSize(700, 500);
		this.setVisible(false);

		getMovieList();

		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(adminMain, "등록하시겠습니까?", "영화 등록",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					movieRegist();
				}
			}
		});
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(adminMain, "영화를 삭제합니다");
				if (JOptionPane.showConfirmDialog(adminMain, "삭제하시겠습니까?", "영화 삭제",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					delMovie();
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				getDetail();
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
	}

	// 영화 목록 가져오기
	public void getMovieList() {
		model.movieList = movieDAO.selectAll();
		System.out.println(model.movieList.size());
		table.updateUI();
	}

	// 선택 내역 상세보기
	public void getDetail() {
		selectMovie = model.movieList.get(table.getSelectedRow());
		t_name.setText(selectMovie.getTitle());
		t_genre.setText(selectMovie.getGenre());
		t_grade.setText(selectMovie.getGrade());
		t_release.setText(selectMovie.getRelease_year());
		t_poster.setText(selectMovie.getPoster());
	}

	// 영화 1건 삭제하기
	public void delMovie() {
		selectMovie = model.movieList.get(table.getSelectedRow());
		int movie_idx = selectMovie.getMovie_idx();
		int result = movieDAO.delete(movie_idx);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "삭제되었습니다");
			getMovieList();
			t_name.setText("Title");
			t_genre.setText("Genre");
			t_grade.setText("Grade");
			t_release.setText("Release Year");
			t_poster.setText("Poster");
		}
	}

	// 영화 1건 insert
	public void movieRegist() {
		movie = new Movie();
		movie.setTitle(t_name.getText());
		movie.setGenre(t_genre.getText());
		movie.setGrade(t_grade.getText());
		movie.setRelease_year(t_release.getText());
		movie.setPoster(t_poster.getText());

		int result = movieDAO.insert(movie);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "등록을 완료하였습니다");
			getMovieList();
		} else {
			JOptionPane.showMessageDialog(this, "등록을 실패했습니다");
		}
	}

}
