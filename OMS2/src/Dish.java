public class Dish{
	private String name;
	private String did;
	private double Price;
	private int total;
	private int id;

	@Override
	public String toString() {return String.format("DID:%s,DISH:%s,PRICE:%.1f,TOTAL:%d",did,name,Price,total);}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public double getPrice() {return Price;}
	public void setPrice(double Price) {this.Price = Price;}
	public int getTotal() {return total;}
	public void setTotal(int total) {this.total = total;}
	public String getDid() {return did;}
	public void setDid(String did) {this.did = did;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public Dish(String name, String did, double Price, int total) {
		this.name = name;
		this.did = did;
		this.Price = Price;
		this.total = total;
		this.id = Integer.valueOf(did.substring(1,7));
	}
}