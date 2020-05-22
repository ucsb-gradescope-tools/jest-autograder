package edu.ucsb.cs56.ratcalc.model;

/**
 * A class to represent a rational number with a numerator and denominator
 * 
 * @author P. Conrad for CS56 
 * 
 */

public class Rational {

    private int num;
    private int denom;

    /**
     * greatest common divisor of a and b
     * 
     * @param a first number
     * @param b second number
     * @return gcd of a and b
     */
    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        else if (b == 0)
            return a;
        else
            return gcd(b % a, a);
    }

    /**
     * least common multiple of a and b hi scott :)
     * 
     * @param a first number
     * @param b second number
     * @return lcm of a and b
     */
    public static int lcm(int a, int b) {
        return Math
                .abs(a * b) / gcd(a, b);
    }

    public Rational() {
        this.num = 1;
        this.denom = 1;
    }

    public Rational(int num, int denom) {
        if (denom == 0) {
            throw new IllegalArgumentException("denominator may not be zero");
        }
        this.num = num;
        this.denom = denom;
        if (num != 0) {
            int gcd = Rational
                    .gcd(num, denom);
            this.num /= gcd;
            this.denom /= gcd;
        }
        if ((this.num>0) && (this.denom<0)) {
            this.num *= -1;
            this.denom *= -1;
        }
    }

    public String toString() {
        if (denom == 1 || num == 0)
            return "" + num;
        return num + "/" + denom;
    }

    public int getNumerator() {
        return this.num;
    }

    public int getDenominator() {
        return this.denom;
    }

    public Rational times(Rational r) {
        return new Rational(this.num * r.num, this.denom * r.denom);
    }

    public static Rational product(Rational a, Rational b) {
        return new Rational(a.num * b.num, a.denom * b.denom);
    }

    /**
     * For testing getters.
     *
     * @param args unused
     */

    public static void main(String[] args) {
        Rational r = new Rational(5, 7);
        System.out
                .println("r.getNumerator()=" + r
                        .getNumerator());
        System.out
                .println("r.getDenominator()=" + r
                        .getDenominator());
    }

    /**
     * Sum of two rational numbers
     * 
     */
    public static Rational sum(Rational a, Rational b) {
        return new Rational(a.num * b.denom  + b.num * a.denom, a.denom * b.denom);
    }


    /**
     * Add rational to this
     * 
     */

    public Rational plus(Rational a) {
        return Rational.sum(this, a);
    }

    /**
     * Difference of two rational numbers
     * 
     */
    public static Rational difference(Rational a, Rational b) {
        return new Rational(a.num * b.denom  - b.num * a.denom, a.denom * b.denom);
    }


    /**
     * Subtract rational from this
     * 
     */

    public Rational minus(Rational a) {
        return Rational.difference(this, a);
    }

    /**
     * Return reciprocal of this rational.  Throws `ArithmeticException`
     * if the rational has value zero.
     */

    public Rational reciprocalOf() {
        if (this.num==0) {
            throw new ArithmeticException("Cannot take reciprocal of 0");
        }
        return new Rational(this.denom, this.num);
    }

    /**
     * Quotient of two rational numbers
     * 
     */
    public static Rational quotient(Rational a, Rational b) {
        return Rational.product(a, b.reciprocalOf());

    }
    /**
     * Divide this by rational
     * 
     */

    public Rational dividedBy(Rational a) {
        return Rational.quotient(this, a);
    }

}