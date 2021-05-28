public class Order {
	public String OrderID;
	public int CustomerID;
	public DishList dlist;
	public int len = 0;
	public boolean IsConfirmed;
	public boolean IsDelivered;

	public Order(String OrderID, int CustomerID){
		this.OrderID = OrderID;
		this.CustomerID = CustomerID;
		dlist = new DishList();
		IsConfirmed = false;
		IsDelivered = false;
		len = 0;
	}

	@Override
	public String toString(){return "OID:"+OrderID+",DISH:"+dlist;}

	public String Sinfo(){
		return dlist.TString();
	}
}