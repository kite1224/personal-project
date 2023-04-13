package movie.domain;

public class History {
	private int history_idx;
	private MovieMember movieMember;
	private Product product;
	public int getHistory_idx() {
		return history_idx;
	}
	public void setHistory_idx(int history_idx) {
		this.history_idx = history_idx;
	}
	public MovieMember getMovieMember() {
		return movieMember;
	}
	public void setMovieMember(MovieMember movieMember) {
		this.movieMember = movieMember;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
