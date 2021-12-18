package naufalJmartFA.jmart_android.model;

public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    @Override
    public String toString(){
//        return "Name: "+name+"\nWeight: "+weight+"\nconditionUsed: "+conditionUsed+
//                "\nprice: "+price+"\ndiscount: "+discount+"\nCategory: "+category+
//                "\naccountId: "+accountId;
        return name;
    }
}
