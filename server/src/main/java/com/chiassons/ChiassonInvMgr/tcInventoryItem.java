package com.chiassons.ChiassonInvMgr;



public class tcInventoryItem {

    // Contains 1:1 with mysql column for Item in database
    int ID;
    public String mcItemName;
    public String BarCodeID;
    public int Quantity;
    public BarcodeType mcBareCode;
    public boolean mbCustomEntry;
    public int mnOrderThreshold;

}
