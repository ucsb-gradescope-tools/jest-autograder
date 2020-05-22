package edu.ucsb.cs56.pconrad.menuitem;

public class MenuItem {

    private String name;
    private int priceInCents;
    private String category;

    /**
     * Custom exception thrown when getPrice is called with a width
     * that is too narrow for the formatted price.
     */

    public static class TooNarrowException extends RuntimeException {
    }


    public MenuItem(String name, int priceInCents, String category) {
        this.name = name;
	this.priceInCents = priceInCents;
	this.category = category;
    }

    public String getName(){
	return name;
    }

    public int getPriceInCents(){
	return priceInCents;
    }

    public String getCategory(){
	return category;
    }

    /**
     * Returns the price, formatted as a string with a $.
     * For example "$0.99", "$10.99", or "$3.50"
     */

    public String getPrice() {
        int cents = this.getPriceInCents()%10;
	int dollars = (this.getPriceInCents() - cents)/10;
	String tmp = String.valueOf(dollars) + String.valueOf(cents);
	String p1 = "$" + tmp.substring(0, tmp.length()-2);
	String p2 = "." + tmp.substring(tmp.length()-2, tmp.length());
	String price = p1 + p2;
	return price;
    }

    /**
     * Returns the price, formatted as a string with a $,
     * right justified in a field with the specified width.
     * For example "$0.99", "$10.99", or "$3.50".
     * <p>
     * If the width is too small, throws IllegalArgumentException
     *
     * @param width width of returned string
     */

    public String getPrice(int width) {

	if (width < 6)
	    {
		throw new TooNarrowException();
	    }
	int cents = this. getPriceInCents()%10;
	int dollars = (this.getPriceInCents() - cents)/10;
	String tmp = String.valueOf(dollars) + String.valueOf(cents);
	String p1 = "$" + tmp.substring(0,tmp.length()-2);
	String p2 = "." + tmp.substring(tmp.length() - 2, tmp.length());
	String price = p1 + p2;
	String finalPrice = String.format("%" + width + "s", price);

	return finalPrice;
    }

    /**
     * return a string in csv format, in the order name,price,cateogry.
     * For example <code>Small Poke Bowl,1049,Poke Bowls</code>
     *
     * @return string in csv format
     */

    @Override
    public String toString() {

	String csvFormat = this.getName() + "," + String.valueOf(this.getPriceInCents()) + "," + this.getCategory();
	return csvFormat;
    }

}
