import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws IOException, FileNotFoundException{
		double[] c1 = {6.2, -0.8, 0, 1.3, -7.5, 2.12};
		int[] e1 = {0, 3, 2, 1, 1, 0};
		Polynomial p1 = new Polynomial(c1, e1);

		System.out.println(p1.evaluate(3));

		double[] c2 = {0, -2.6, 0.27, 0.7, -9.34, 3.55, -1.8, 6.9, 12, 2};
		int[] e2 = {0, 2, 2, 5, 4, 2, 3, 1, 0, 8};
		Polynomial p2 = new Polynomial(c2, e2);

		Polynomial sum = p1.add(p2);
		Polynomial product = p1.multiply(p2);

		System.out.println("sum = " + sum.toString());
		System.out.println("product = " + product.toString());
		System.out.println("sum(5.35) = " + sum.evaluate(5.35));
		System.out.println("product(1.53) = " + product.evaluate(1.53));

		System.out.println("");

		product.saveToFile("productFile.txt");
		sum.saveToFile("sumFile.txt");

		Polynomial x = new Polynomial(new File("x.txt"));
		System.out.println("x = " + x.toString());
		System.out.println("");

		Polynomial pText = new Polynomial(new File("pText.txt"));
		System.out.println("pText = " + pText.toString());
		System.out.println("");

		Polynomial productFile = new Polynomial(new File("productFile.txt"));
		System.out.println("productFile = " + productFile.toString());
		System.out.println("");

		Polynomial sumFile = new Polynomial(new File("sumFile.txt"));
		System.out.println("sumFile = " + sumFile.toString());
		System.out.println("");

		Polynomial four = new Polynomial(new File("constant.txt"));
		System.out.println("four = " + four.toString());
		System.out.println("");

		Polynomial neg = new Polynomial(new File("negConstant.txt"));
		System.out.println("negConstant = " + neg.toString());
		System.out.println("");

		Polynomial empty = new Polynomial(new File("empty.txt"));
		System.out.println("empty = " + empty.toString());
		System.out.println("");

		Polynomial zero = new Polynomial(new File("zero.txt"));
		System.out.println("zero = " + zero.toString());
		System.out.println("");

		Polynomial sum1 = x.add(zero);
		System.out.println("sum1 = " + sum1.toString());
		System.out.println("");

		Polynomial simple = new Polynomial(new File("simple.txt"));
		System.out.println("simple = " + simple.toString());
		System.out.println("");

		Polynomial product1 = x.multiply(simple);
		System.out.println("product1 = " + product1.toString());
		System.out.println("");

		System.out.println("3 is a root of simple: " + simple.hasRoot(3));
		System.out.println("-3 is a root of simple: " + simple.hasRoot(-3));
		System.out.println("0 is a root of simple: " + simple.hasRoot(0));
	}
}