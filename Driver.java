import java.io.*;

public class Driver {
	public static void main(String [] args) throws Exception {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));

		double [] c1 = {6,5,25}; // 6+5x^3+25^5
		int [] e1 = {0,3,5};
		Polynomial p1 = new Polynomial(c1,e1);

		double [] c2 = {-2,-9,-25}; // -2x-9x^4-25x^5
		int [] e2 = {1,4,5};
		Polynomial p2 = new Polynomial(c2,e2);

		Polynomial s = p1.add(p2);

		System.out.println("s(0.1) = " + s.evaluate(0.1));
		System.out.println(s);
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");

		double[] c3 = {2,3};
		int[] e3 = {1,3};
		Polynomial c = new Polynomial(c3,e3);

		double[] c4 = {4,9,1};
		int[] e4 = {0,2,3};
		Polynomial d = new Polynomial(c4,e4);

		Polynomial p3 = c.multiply(d);
		System.out.println(p3);

		File f = new File("C:\\Users\\itsma\\OneDrive - University of Toronto\\Y2S1\\CSCB07\\lab 2\\b07lab1\\pol.txt");
		Polynomial p4 = new Polynomial(f);
		System.out.println(p4);

		p4.saveToFile("C:\\Users\\itsma\\OneDrive - University of Toronto\\Y2S1\\CSCB07\\lab 2\\b07lab1\\p4.txt");
	}
}
