import java.util.*;
import java.io.*;

class Polynomial {
	double[] coe;
	int[] exp;

    public Polynomial() {
        coe = new double[1];
        coe[0] = 0;
        exp = new int[1];
        exp[0] = 0;
    }

    public Polynomial(double[] a, int[] b) {
        // making a deep copy of a and b (they should be the same length)
        coe = new double[a.length];
        exp = new int[b.length];
        for(int i=0;i<a.length;i++) {
            coe[i] = a[i];
            exp[i] = b[i];
        }
    }

    public Polynomial(File f) throws FileNotFoundException {
        Scanner input = new Scanner(f);
        String line = input.nextLine();
        input.close();

        // should split "-2+5x+6x2" into {"-2","+5x","+6x2"}
        String[] terms = line.split("(?=-|\\+)");

        // fix the outliers with exponent 0 and 1 (different formatting) to make it easier to parse later on
        for(int i=0;i<terms.length;i++) {
            // if it doesnt contain an x => it is exponent 0 (and comes first)
            if(!terms[i].contains("x")) {
                // add the x0 to the end
                terms[i] = terms[i] + "x0";
            }
            // if it contains x ^ AND the last character is x (not a number) => it is exponent 1
            else if(terms[i].charAt(terms[i].length() - 1) == 'x') {
                terms[i] = terms[i] + "1";
            }
        }

        /*System.out.print("[ ");
        for(String a:terms) System.out.print("'" + a + "' , ");
        System.out.println("]");*/

        coe = new double[terms.length];
        exp = new int[terms.length];

        for(int i=0;i<terms.length;i++) {
            // will produce an array with 2 doubles
            String[] a = terms[i].split("x");
            double[] info = {Double.parseDouble(a[0]),Double.parseDouble(a[1])};
            coe[i] = info[0];
            exp[i] = (int) info[1];
        }
    }

    public void saveToFile(String fileName) throws Exception {
        FileWriter output = new FileWriter(fileName);
        output.write(toString());
        output.close();
    }

    // helper function: returns the max int in a
    int max(int[] a) {
        int max = 0;
        for(int i:a) {
            if(i>max) max = i;
        }
        return max;
    }

    public Polynomial add(Polynomial c) {
        // convert coe and exp into one array coefficient
        // the length of the list is the biggest exponent
        double[] coefficients = new double[max(exp)+1];
        for(int i=0;i<coe.length;i++) {
            coefficients[exp[i]] = coe[i];
        }
        double[] c_coefficients = new double[max(c.exp)+1];
        for(int i=0;i<c.coe.length;i++) {
            c_coefficients[c.exp[i]] = c.coe[i];
        }

        // if c is the bigger array
        if(c_coefficients.length >= coefficients.length) {
            double[] result = new double[c_coefficients.length];
            for(int i=0;i<c_coefficients.length;i++) {
                result[i] = c_coefficients[i];
            }
            for(int i=0;i<coefficients.length;i++) {
                result[i] += coefficients[i];
            }

            // now convert back into 2 arrays
            // first, count how many nonzeroes there are
            int ctn = 0;
            for(int i=0;i<result.length;i++)
                if(result[i] != 0) ctn++;

            double[] result_coe = new double[ctn];
            int[] result_exp = new int[ctn];

            int k = 0;
            for(int i=0;i<result.length;i++) {
                if(result[i] != 0) {
                    result_coe[k] = result[i];
                    result_exp[k] = i;
                    k++;
                }
            }

            return new Polynomial(result_coe, result_exp);
        }
        // otherwise 'this' is the bigger array
        else {
            double[] result = new double[coefficients.length];
            for(int i=0;i<coefficients.length;i++) {
                result[i] = coefficients[i];
            }
            for(int i=0;i<c_coefficients.length;i++) {
                result[i] += c_coefficients[i];
            }

            // now convert back into 2 arrays
            // first, count how many nonzeroes there are
            int ctn = 0;
            for(int i=0;i<result.length;i++)
                if(result[i] != 0) ctn++;

            double[] result_coe = new double[ctn];
            int[] result_exp = new int[ctn];

            int k = 0;
            for(int i=0;i<result.length;i++) {
                if(result[i] != 0) {
                    result_coe[k] = result[i];
                    result_exp[k] = i;
                    k++;
                }
            }
            
            return new Polynomial(result_coe, result_exp);
        }
    }

    public Polynomial multiply(Polynomial c) {
        // make an array of Polynomials which will hold each product of each term in 'this' with each term in c
        Polynomial[] toAdd = new Polynomial[coe.length];
        
        // for each term of 'this'
        for(int k=0;k<coe.length;k++) {
            double[] newCoe = new double[c.coe.length];
            int[] newExp = new int[c.exp.length];
            for(int i=0;i<c.coe.length;i++) {
                newCoe[i] = coe[k] * c.coe[i];
                newExp[i] = exp[k] + c.exp[i];
            }
            toAdd[k] = new Polynomial(newCoe, newExp);
        }
        // now add every polynomial in toAdd and return it
        Polynomial result = new Polynomial();
        for(int i=0;i<toAdd.length;i++) {
            //System.out.println("TO ADD " + i + ": " + toAdd[i]);
            result = result.add(toAdd[i]);
        }

        return result;
    }

    public double evaluate(double x) {
        double result = 0;
        for(int i=0;i<coe.length;i++) {
            result += coe[i]*Math.pow(x, exp[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public String toString() {
        String result = "";
        if(coe[0] < 0) result = result + "-";

        for(int i=0;i<coe.length;i++) {

            if(i > 0 && coe[i] < 0)
                result = result + "-";
            else if(i > 0)
                result = result + "+";

            if(exp[i] == 0)
                result = result + String.valueOf(Math.abs(coe[i]));
            else if(exp[i] == 1)
                result = result + String.valueOf(Math.abs(coe[i])) + "x";
            else {
                result = result + String.valueOf(Math.abs(coe[i])) + "x" + String.valueOf(exp[i]);
            }
        }
        return result;
    }
}