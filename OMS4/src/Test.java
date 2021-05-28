import java.util.*;

public class Test {
	private static Menu menu;
	private static PersonList PList;
//	private static DishDoneList DList;
	public static Scanner in;
	private static Error Err = new Error();

	public static void SUDO(String []arr){
		String s;
		if (arr.length != 1){
			Err.FoundErr();	return;
		}
		System.out.println("Enter sudo mode");
		while (true){
			s = in.nextLine().trim();
			String []a=s.split("\\s+");
			switch (a[0]){
				case "sncu":	PList.AddPerson(a,0);	break;
				case "snwa":	PList.AddPerson(a,1);	break;
				case "snco":	PList.AddPerson(a,2);	break;
				case "dcu":		PList.DelPerson(a,0);	break;
				case "dwa":		PList.DelPerson(a,1);	break;
				case "dco":		PList.DelPerson(a,2);	break;
				case "pp":		PList.PP(a);	break;
				case "SQ":
					if (a.length != 1){
						Err.CountErr();	break;
					}
					System.out.println("Quit sudo mode");	return;
				default: System.out.println("Call sudo method illegal");	break;
			}
		}
	}

	public static void LOGIN(String []a){
		if (a.length == 1){
			Err.CountErr();	return;
		}
		if (a[1].compareTo("-n")!=0 && a[1].compareTo("-i")!=0){
			Err.FoundErr();	return;
		}
		if (a.length != 4){
			Err.CountErr();	return;
		}
		PList.Login(a,in);
	}

	public static void PM(String []a){
		if (a.length == 3){
			menu.PMbyPage(a,in);	return;
		}
		
		if (a.length > 1){
			Err.CountErr();	return;
		}
		menu.PM();
	}

	public static void GD(String []a){
		if (a.length < 2){
			Err.FoundErr();	return;
		}
		if (a[1].compareTo("-id") != 0 && a[1].compareTo("-key") != 0){
			Err.FoundErr();	return;
		}
		if (a[1].compareTo("-key")==0 && a.length==5){
			menu.GdbyPage(a, in);
			return;
		}
		if (a.length != 3){
			Err.CountErr();	return;
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

	public static void ND(String []a){
		if (a.length != 5){
			Err.CountErr();	return;
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
		switch (arr[0]){
			case "SUDO": SUDO(arr);	break;
			case "login": LOGIN(arr);	break;
			case "pm": PM(arr);	break;
			case "nd": ND(arr);	break;
			case "gd": GD(arr);	break;
			default:  Err.FoundErr(); break;
		}
	}

	public static void main(String[] args){
		in = new Scanner(System.in);
		String s;
		menu = new Menu();
		PList = PersonList.getInstance();
//		DList = new DishDoneList();
		while (true) {
			s = in.nextLine();
			if (s.compareTo("QUIT")==0){
				System.out.println("----- Good Bye! -----");
				in.close();
				System.exit(0);
			}
			Check(s.trim());
		}
	}
}