package org.hqcplays.hqcsoneblock;

public class FleaListing {
    
    // Properties of a listing
    private String itemName;
    private int itemPrice;
    private String sellerName;

    // Main Constructor
    public FleaListing(String itemName, int itemPrice, String sellerName){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.sellerName = sellerName;
    }

    // Getters and Setters
    public String getItemName(){
        return itemName;
    }
    
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    // Getters and Setters
    public int getItemPrice(){
        return itemPrice;
    }
    
    public void setItemName(int itemPrice){
        this.itemPrice = itemPrice;
    }

    // Getters and Setters
    public String getSellerName(){
        return sellerName;
    }
    
    public void setSellerName(String sellerName){
        this.sellerName = sellerName;
    }
}
