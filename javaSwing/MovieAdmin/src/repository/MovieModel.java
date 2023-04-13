package repository;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import movie.domain.Movie;


public class MovieModel extends AbstractTableModel{
	public List<Movie> movieList = new ArrayList<Movie>();
	String[] movieName = {"순번","제목","장르","평점","개봉일"};
	
	public int getRowCount() {
		return movieList.size();
	}

	
	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int col) {
		return movieName[col];
	}
	public Object getValueAt(int row, int col) {
		Movie movie =(Movie) movieList.get(row);
		String value =null;
		switch(col) {
			case 0: value = Integer.toString(movie.getMovie_idx()); break;
			case 1: value = movie.getTitle(); break;
			case 2: value = movie.getGenre(); break;
			case 3: value = movie.getGrade(); break;
			case 4: value = movie.getRelease_year(); break;
		}
		return value;	
		
	}

}
