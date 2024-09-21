package com.chiassons.barcodescanner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * tcInventoryMgr is a AppCompatActivity
 *
 * This class Manages tcItems selected my the BarcodeAssistant(MainActivity)
 *
 */
class tcInventoryMgr : AppCompatActivity()
{

    private lateinit var gItem : tcItem
    private lateinit var gRestMgr: tcRestMgr

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inv_mgr)
        val barCodeID = intent.getStringExtra("BarCode")
        val barcodeFormat = intent.getStringExtra("BarCodeFormat")
        val mcItemId = intent.getStringExtra("Item-Id")
        gRestMgr = tcRestMgr("")//TODO make config reader to get url value

        Log.d("BarcodeAnalyzer", "Barcode detected: " + barCodeID + ":" + barcodeFormat+ ":"+mcItemId)

        // Use the value to send a get request to server
        // If GET returns null
        // then open adder menu

    }

    fun getItemFromServer(rawBarCode : String,
                          barFormat : String,
                          itemId : String?)
    {
        setItem(this.gRestMgr.GetItem(
            tcItem(rawBarCode,barFormat,itemId,0,"",null,null)))

    }


    /**
     * Sets given item to global item
     * @return void
     */
    fun setItem(item: tcItem)
    {
        gItem = item
    }
    /**
     * Adds 1 to currently selected item
     * @return void
     */
    fun IncreaseQuantity()
    {
        gItem.quantity!! + 1
    }
    /**
     * Subtracts 1 to currently selected item
     * @return void
     */
    fun DecreaseQuantity()
    {
        gItem.quantity!! - 1
    }
    /**
     * Sets current select item quantity
     * @return void
     */
    fun SetQuantity(quantity:Int)
    {
        gItem.quantity = quantity
    }

}
