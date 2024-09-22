package com.chiassons.ChiassonInvMgr.Controller;

import com.chiassons.ChiassonInvMgr.BarcodeType;
import com.chiassons.ChiassonInvMgr.tcInvDataAccessor;
import com.chiassons.ChiassonInvMgr.tcInventoryItem;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.Optional;


@RestController
public class tcInvCtrl {

    tcInvDataAccessor mpcDataAccessor = new tcInvDataAccessor();

    /**
     * @brief Gets Item based off GET Request
     */
    @GetMapping("/inv")
    public Optional<Boolean> CheckItem(@RequestParam String arcBarCodeID,@RequestParam BarcodeType arcBareCode)
    {
        Optional<Boolean> lcOptional = Optional.empty();


        try
        {
            mpcDataAccessor.CheckInventory(arcBarCodeID,arcBareCode);
            lcOptional = Optional.ofNullable(true);
        }
        catch (SQLException e)
        {
            lcOptional = Optional.ofNullable(false);

        }


        return lcOptional;
    }

    /**
     * @brief POST Item based off GET Request
     */
    @PostMapping("/inv")
    public Optional<Boolean> AddItem(@RequestParam String arcBarCodeID,@RequestParam BarcodeType arcBareCode)
    {
        Optional<Boolean> lcOptional = Optional.empty();


        tcInventoryItem lcInventoryItem = new tcInventoryItem();//TODO fix this
        lcInventoryItem.mcItemName = "Test";
        lcInventoryItem.BarCodeID = "Test";
        lcInventoryItem.Quantity = 1;
        lcInventoryItem.mcBareCode= BarcodeType.Unknown;
        lcInventoryItem.mbCustomEntry= true;
        lcInventoryItem.mnOrderThreshold= 1;


        try
        {
            lcOptional = Optional.of(mpcDataAccessor.AddToInventory(lcInventoryItem));
        } catch (SQLException e) {
            lcOptional = Optional.ofNullable(false);
        }

        return lcOptional;
    }

    /**
     * @brief POST Item based off GET Request
     */
    @PutMapping("/inv")
    public  Optional<Boolean> UpdateItem(@RequestParam tcInventoryItem acInventoryItem)//TODO base off
    {
        Optional<Boolean> lcOptional = Optional.empty();//TODO Look into what Update Inv mysql call actually returns

        try
        {
            mpcDataAccessor.UpdateInventory(acInventoryItem);
        }
        catch (SQLException e)
        {

            return lcOptional;
        }

        return lcOptional;
    }
    /**
     * @brief DELETES item based of
     * @param arcBarCodeID
     * @param arcBareCode
     * @return true if delete had a hit , false if nothing deleted
     */
    @DeleteMapping("/inv")
    public  Optional<Boolean> DeleteItem(@RequestParam String arcBarCodeID,@RequestParam BarcodeType arcBareCode)//TODO base off
    {
        Optional<Boolean> lcOptional = Optional.empty();

        return lcOptional;
    }




}
