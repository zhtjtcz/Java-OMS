import java.util.*;

public class DishList {
	public Vector<Dish> Hot;
	public Vector<Dish> Cold;
	public Vector<Dish> Other;

	public DishList(){Hot=new Vector<Dish>(0);	Cold=new Vector<Dish>(0);	Other=new Vector<Dish>(0);}

	@Override
	public String toString(){
		String a = "[";
		for (Dish i : Hot)		a+=(i.getTotal())+" "+i.getName()+",";
		for (Dish i : Cold)		a+=(i.getTotal())+" "+i.getName()+",";
		for (Dish i : Other)	a+=(i.getTotal())+" "+i.getName()+",";
		return a.substring(0, a.length()-1)+"]";
	}

	public String TString(){
		String a = "[";
		for (Dish i : Hot)		a+=i.getName()+" "+i.getTotal()*i.getPrice()+",";
		for (Dish i : Cold)		a+=i.getName()+" "+i.getTotal()*i.getPrice()+",";
		for (Dish i : Other)	a+=i.getName()+" "+i.getTotal()*i.getPrice()+",";
		return a.substring(0, a.length()-1)+"]";
	}
	// Another toString function
	// Make the format print more easy

	public boolean AddDish(Vector<Dish> l, Dish d){
		for (Dish i : l)
			if (i.getDid().compareTo(d.getDid()) == 0){
				i.setTotal(i.getTotal()+d.getTotal());	return true;	
			}
		return false;
	}

	public void Insert(Dish d){
		if (AddDish(Hot, d) || AddDish(Cold, d) || AddDish(Other, d))	return;
		if (d.getDid().charAt(0) == 'H')	Hot.add(d);
		else if (d.getDid().charAt(0) == 'C')	Cold.add(d);
		else Other.add(d);
	}

	public void Sort(){
		Comparator<Dish> cmp =new indexDESC();
		Collections.sort(Hot, cmp);
		Collections.sort(Cold, cmp);
		Collections.sort(Other, cmp);
	}

	public double GetCharge(){
		double total = 0.0;
		for (Dish i : Hot)		total+=i.getPrice()*i.getTotal();
		for (Dish i : Cold)		total+=i.getPrice()*i.getTotal();
		for (Dish i : Other)	total+=i.getPrice()*i.getTotal();
		return total;
	}

	public void Print(){
		Sort();
		int num = 1;
		for (Dish i : Hot)		System.out.println((num++)+"."+i.Calculate());
		for (Dish i : Cold)		System.out.println((num++)+"."+i.Calculate());
		for (Dish i : Other)	System.out.println((num++)+"."+i.Calculate());
		System.out.println("|");
		System.out.printf("SUM:%.1f\n",GetCharge());
	}
}