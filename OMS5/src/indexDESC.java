import java.util.*;

public class indexDESC implements Comparator<Dish>{
	public int compare(Dish a, Dish b) {
		int ax=a.getId(),bx=b.getId();
		if (ax<bx)	return -1;	else return 1;
	}
}