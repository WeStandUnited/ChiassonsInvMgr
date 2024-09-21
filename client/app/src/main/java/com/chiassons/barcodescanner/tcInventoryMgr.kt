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

    val lcCache = BarcodeRegistry.getAllEntries()
    private lateinit var gItem : tcItem

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inv_mgr)
        val barCodeID = intent.getStringExtra("BarCode")
        val barcodeFormat = intent.getStringExtra("BarCodeFormat")
        val mcItemId = intent.getStringExtra("Item-Id")


        Log.d("BarcodeAnalyzer", "Barcode detected: " + barCodeID + ":" + barcodeFormat+ ":"+mcItemId)

        // Use the value to send a get request to server
        // If GET returns null
        // then open adder menu

    }

    fun getItemFromServer(rawBarCode : String?,
                          barFormat : String?,
                          itemId : String?)
    {

        // Make




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
        gItem.quantity++
    }
    /**
     * Subtracts 1 to currently selected item
     * @return void
     */
    fun DecreaseQuantity()
    {
        gItem.quantity--
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
