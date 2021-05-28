import java.util.*;

public class Test {
	private static Menu menu;

	public static void NP(String []a){
		if (a.length != 4)	{
			System.out.println("Params' count illegal");	return;
		}
		if (a[2].length()!=1){
			System.out.println("Sex illegal");	return;
		}
		Person p=Person.addPerson(a[1],a[2].charAt(0),a[3]);
		if (p!=null)	System.out.println(p.toString());
	}

	public static void PM(String []a){
		if (a.length > 1){
			System.out.println("Params' count illegal");
			return;
		}
		menu.PM();
	}

	public static void GD(String []a){
		if (a.length < 2){
			System.out.println("Command not exist");	return;
		}
		if (a[1].compareTo("-id") != 0 && a[1].compareTo("-key") != 0){
			System.out.println("Command not exist");	return;
		}
		if (a.length != 3){
			System.out.println("Params' count illegal");	return;
		}
		if (a[1].compareTo("-id") == 0){
			if (menu.DidLegal(a[2]) == false)	{
				System.out.println("Did input illegal");
				return;
			}
			int id = Integer.valueOf(a[2].substring(1,7));
			menu.getDishById(a[2].charAt(0), id);
		}	else if (a[1].compareTo("-key") == 0){
			Vector<Dish> l = menu.getDishByKeyWord(a[2]);
			if (l.capacity() == 0)
				System.out.println("Dish does not exist");
			else {
				int cnt = 1;
				for (Dish i : l)
					System.out.println((cnt++)+". "+i);
			}
		}
	}

	public static void UDD(String []a){
		if (a.length < 2){
			System.out.println("Command not exist");	return;
		}
		if (a[1].compareTo("-n") != 0 && a[1].compareTo("-t") != 0 && a[1].compareTo("-p") != 0){
			System.out.println("Command not exist");	return;
		}
		if (a.length != 4){
			System.out.println("Params' count illegal");
			return;
		}
		if (menu.DidLegal(a[2]) == false){
			System.out.println("Did input illegal");	return;
		}
		if (menu.FindDid(a[2]) == false){
			System.out.println("Dish does not exist");
			return;
		}

		if (a[1].compareTo("-n") == 0){
			if (menu.NameLegal(a[3]) == false){
				System.out.println("New name input illegal");
				return;
			}
			if (menu.CheckSameName(a[3]) == false){
				System.out.println("New name repeated");
				return;
			}
			menu.Update(a[2],a[3],0);
			System.out.println("Update dish's name success");
		}	else if (a[1].compareTo("-t") == 0){
			if (menu.QLegal(a[3]) == false){
				System.out.println("Change dish's total illegal");
				return;
			}
			menu.Update(a[2],a[3],1);
			System.out.println("Update dish's total success");
		}	else if (a[1].compareTo("-p") == 0){
			if (menu.PLegal(a[3]) == false){
				System.out.println("Change dish's price illegal");
				return;
			}
			menu.Update(a[2],a[3],2);
			System.out.println("Update dish's price success");
		}
	}

	public static void ND(String []a){
		if (a.length != 5){
			System.out.println("Params' count illegal");
			return;
		}
		if (menu.DidLegal(a[1]) == false){
			System.out.println("Did input illegal");
			return;
		}
		if (menu.CheckSameDid(a[1]) == false){
			System.out.println("Dish exists");
			return;
		}
		if (menu.CheckLegal(a[2],a[3],a[4]) == false){
			System.out.println("New dish's attributes input illegal");
			return;
		}
		if (menu.CheckSameName(a[2]) == false){
			System.out.println("Name repeated");
			return;
		}
		Dish x = new Dish(a[2],a[1],Double.valueOf(a[3]),Integer.valueOf(a[4]));
		menu.Add(x);	menu.Sort();
		System.out.println("Add dish success");
	}

	public static void Check(String s){
		String []arr=s.split("\\s+");
		if (arr[0].length() == 0){
			System.out.println("Input illegal");
			return;
		}

		//for (int i=0;i<arr.length;i++)	System.out.println(arr[i]);
		if (arr[0].compareTo("np") == 0){
			NP(arr);	return;
		}
		if (arr[0].compareTo("pm") == 0){
			PM(arr);	return;
		}
		if (arr[0].compareTo("nd") == 0){
			ND(arr);	return;
		}
		if (arr[0].compareTo("gd") == 0){
			GD(arr);	return;
		}
		if (arr[0].compareTo("udd") == 0){
			UDD(arr);	return;
		}
		System.out.println("Command not exist");
	}

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String s;
		menu = new Menu();
		while (true) {
			s = in.nextLine();
			if (s.compareTo("QUIT")==0){
				System.out.println("----- Good Bye! -----");
				in.close();
				System.exit(0);
			}
			s = s.trim();
			Check(s);
		}
	}
}