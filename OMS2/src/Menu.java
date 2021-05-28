import java.util.*;
import java.util.regex.Pattern;

class indexDESC implements Comparator<Dish>{
	public int compare(Dish a, Dish b) {
		int ax=a.getId(),bx=b.getId();
		if (ax<bx)	return -1;	else return 1;
	}
}

public class Menu{
	private Vector<Dish> Hot;
	private Vector<Dish> Cold;
	private Vector<Dish> Other;

	public Menu() {Hot=new Vector<Dish>(0);	Cold=new Vector<Dish>(0);	Other=new Vector<Dish>(0);}

	public void Sort(){
		Comparator<Dish> cmp =new indexDESC();
		Collections.sort(Hot, cmp);
		Collections.sort(Cold, cmp);
		Collections.sort(Other, cmp);
	}

	public Vector<Dish> Merge(){
		Vector<Dish> ans = new Vector<Dish>(0);
		for (Dish i : Hot)		ans.add(i);
		for (Dish i : Cold)		ans.add(i);
		for (Dish i : Other)	ans.add(i);
		return ans;
	}

	public void PrintbyPage(Vector<Dish> D, int n, int m){
		System.out.println("Page: "+n);
		int num=1;
		for (int i=(n-1)*m;i<n*m;i++){
			if (i>=D.size())	break;
			System.out.println((num++)+". "+D.elementAt(i));
		}
		System.out.println("n-next page,l-last page,f-first page,q-quit");
	}

	public void Page(Vector<Dish> D, Scanner in, int n, int m){
		int tot = D.size()/m;
		if (D.size()%m > 0)	tot++;
		if (n<=0)	n=1;
		if (n>tot)	n=tot;
		PrintbyPage(D, n, m);
		while (true){
			String s = in.nextLine().trim();
			switch (s){
				case "n": 	if (n==tot){	System.out.println("This is the last page");	continue;}
							PrintbyPage(D, ++n, m); break;
				case "l":	if (n==1){	System.out.println("This is the first page");	continue;}
							PrintbyPage(D, --n, m);	break;
				case "f":	n=1;	PrintbyPage(D, 1, m);	break;
				case "q":	System.out.println("Exit page check mode"); return;
				default: 	System.out.println("Call inner method illegal"); break;
			}
		}
	}

	public void PM(){
		if (Hot.capacity()+Cold.capacity()+Other.capacity() == 0){
			System.out.println("Empty Menu");
			return;
		}
		
		int num=1;	Sort();

		if (Hot.capacity() != 0)
			for (Dish i : Hot)
				System.out.println((num++)+". "+i);
		
		if (Cold.capacity() != 0)
			for (Dish i : Cold)
				System.out.println((num++)+". "+i);
		
		if (Other.capacity() != 0)
			for (Dish i : Other)
				System.out.println((num++)+". "+i);
	}

	public void PMbyPage(String []a, Scanner in){
		int n,m;
		try {
			n=Integer.valueOf(a[1]);
			m=Integer.valueOf(a[2]);
		} catch (Exception e) {
			System.out.println("Page slice method's params input illegal");
			return;
		}
		if (m<=0){
			System.out.println("Page slice method's params input illegal");
			return;
		}
		if (Hot.capacity()+Cold.capacity()+Other.capacity() == 0){
			System.out.println("Menu is empty, exit page check mode");
			return;
		}
		Sort();	Page(Merge(), in, n, m);
	}

	public void GdbyPage(String []a, Scanner in){
		int n,m;
		Sort();
		Vector<Dish> allDish = getDishByKeyWord(a[2]);
		if (allDish.size() == 0){
			System.out.println("Dish does not exist");
			return;
		}
		try {
			n=Integer.valueOf(a[3]);
			m=Integer.valueOf(a[4]);
		} catch (Exception e) {
			System.out.println("Page slice method's params input illegal");
			return;
		}
		if (m<=0){
			System.out.println("Page slice method's params input illegal");
			return;
		}
		Page(allDish, in, n, m);
	}

	public Boolean FindDid(String s){
		for (Dish i : Hot)		if (i.getDid().compareTo(s) == 0)	return true;
		for (Dish i : Cold)		if (i.getDid().compareTo(s) == 0)	return true;
		for (Dish i : Other)	if (i.getDid().compareTo(s) == 0)	return true;
		return false;
	}

	public void Find(Vector<Dish> a, int id){
		for (Dish i : a)
			if (i.getId() == id){
				System.out.println(i);	return;
			}
		System.out.println("Dish does not exist");
	}

	public void getDishById(char type, int id){
		if (type=='H')	Find(Hot,id);
		else if (type=='C')	Find(Cold,id);
		else Find(Other,id);
	}

	public Vector<Dish>	getDishByKeyWord(String s){
		Vector<Dish> p = new Vector<Dish>(0);
		s=s.toLowerCase();
		for (Dish i : Hot)
			if (i.getName().toLowerCase().indexOf(s) != -1)	p.add(i);
		for (Dish i : Cold)
			if (i.getName().toLowerCase().indexOf(s) != -1)	p.add(i);
		for (Dish i : Other)
			if (i.getName().toLowerCase().indexOf(s) != -1)	p.add(i);
		return p;
	}

	public boolean CheckSameDid(String s){
		for (Dish i : Hot)
			if (i.getDid().compareTo(s) == 0)	return false;
		for (Dish i : Cold)
			if (i.getDid().compareTo(s) == 0)	return false;
		for (Dish i : Other)
			if (i.getDid().compareTo(s) == 0)	return false;
		return true;
	}

	public boolean CheckSameName(String s){
		for (Dish i : Hot)
			if (i.getName().compareTo(s) == 0)	return false;
		for (Dish i : Cold)
			if (i.getName().compareTo(s) == 0)	return false;
		for (Dish i : Other)
			if (i.getName().compareTo(s) == 0)	return false;
		return true;
	}

	public boolean DidLegal(String s){return Pattern.matches("^[CHO][0-9]{6}$", s);}

	public boolean NameLegal(String a){return Pattern.matches("^[a-zA-Z0-9]{1,}$", a);}

	public boolean QLegal(String c){
		int x = 0;
		try {
			x = Integer.valueOf(c);
		}	catch (Exception e){
			return false;
		}
		return (x>=0);
	}

	public boolean PLegal(String b){
		double y = 0.0;
		try {
			y = Double.valueOf(b);
		}	catch (Exception e){
			return false;
		}
		return (y>=0);
	}

	public boolean CheckLegal(String a,String b,String c){return (NameLegal(a) && PLegal(b) && QLegal(c)); }

	public void Add(Dish x){
		if (x.getDid().charAt(0) == 'H')	Hot.add(x);
		else if (x.getDid().charAt(0) == 'C')	Cold.add(x);
		else Other.add(x);
	}

	public void Update(String did,String s,int flag){
		Vector <Dish> x;
		if (did.charAt(0)=='H')	x=Hot;
		else if (did.charAt(0)=='C') x=Cold;
		else x=Other;
		for (Dish i : x)
			if (i.getDid().compareTo(did) == 0){
				if (flag==0)	i.setName(s);
				else if (flag==1)	i.setTotal(Integer.valueOf(s));
				else i.setPrice(Double.valueOf(s));
			}
	}
}