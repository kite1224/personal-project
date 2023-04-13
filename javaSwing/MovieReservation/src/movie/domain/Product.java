package movie.domain;

public class Product {
	private int product_idx;
	
	//외래키들을 pk뿐 아니라 전체적으로 소유해보자
	
	private Movie movie;
	private DateTable date;
	private TimeTable time;
	private SeatTable seat;
	public int getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(int product_idx) {
		this.product_idx = product_idx;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public DateTable getDate() {
		return date;
	}
	public void setDate(DateTable date) {
		this.date = date;
	}
	public TimeTable getTime() {
		return time;
	}
	public void setTime(TimeTable time) {
		this.time = time;
	}
	public SeatTable getSeat() {
		return seat;
	}
	public void setSeat(SeatTable seat) {
		this.seat = seat;
	}
	
	

	
	
	
}
