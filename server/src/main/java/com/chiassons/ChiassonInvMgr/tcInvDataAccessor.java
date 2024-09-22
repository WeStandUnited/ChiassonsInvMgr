package com.chiassons.ChiassonInvMgr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class tcInvDataAccessor {

    Connection con;
    DbParams mcDbParams;
    static final String mcDatabaseName = "ItemInventory";

    /**
     *
     * @brief Constructor for MYSQL connection
     */
    public tcInvDataAccessor()
    {
        tcConfigMgr lcConfig = new tcConfigMgr();
        mcDbParams = lcConfig.GetDbCreds(mcDatabaseName);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/iteminventory",mcDbParams.getDbuser(),mcDbParams.getDbpassword());
        }
        catch (SQLException e)// TODO fix this catch block its silly
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @brief Method adds item to database
     * @param acInventoryItem Object of Inventory Item
     * @return true if Add was successful false if there was an error
     */
    public boolean AddToInventory(tcInventoryItem acInventoryItem) throws SQLException {

        if (con.isClosed()) throw new SQLException("Not Connected to Database");

        String lcQuery = "insert into "+ "InvItems" +
                " (Item_Name,BarCodeID,Quantity,BarCodeType,customEntry,OrderThreshHold) "+
                " values (?,?,?,?,?,?)";

        PreparedStatement lcPreparedStmt = con.prepareStatement(lcQuery);

        lcPreparedStmt.setString(1,acInventoryItem.mcItemName);
        lcPreparedStmt.setString(2,acInventoryItem.BarCodeID);
        lcPreparedStmt.setInt(3,acInventoryItem.Quantity);
        lcPreparedStmt.setString(4,acInventoryItem.mcBareCode.toString());
        lcPreparedStmt.setBoolean(5,acInventoryItem.mbCustomEntry);
        lcPreparedStmt.setInt(6,acInventoryItem.mnOrderThreshold);

        return lcPreparedStmt.execute();
    }

    /**
     * @brief Method adds item to database
     * @param acInventoryItem Object of Inventory Item
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int UpdateInventory(tcInventoryItem acInventoryItem) throws SQLException {

        if (con.isClosed()) throw new SQLException("Not Connected to Database");

        String lcQuery = "update " + "InvItems" + " SET " +
                "(Item_Name,BarCodeID,Quantity,BarCodeType,customEntry,OrderThreshHold)"+
                "values (?,?,?,?,?) WHERE BarCodeID ='?'";

        PreparedStatement lcPreparedStmt = con.prepareStatement(lcQuery);

        lcPreparedStmt.setString(1,acInventoryItem.mcItemName);
        lcPreparedStmt.setInt(2,acInventoryItem.Quantity);
        lcPreparedStmt.setString(3,acInventoryItem.mcBareCode.toString());
        lcPreparedStmt.setBoolean(4,acInventoryItem.mbCustomEntry);
        lcPreparedStmt.setInt(5,acInventoryItem.mnOrderThreshold);
        lcPreparedStmt.setString(6,acInventoryItem.BarCodeID);

        return lcPreparedStmt.executeUpdate();
    }

    /**
     * @brief Method Removes item from database
     * @param acInventoryItem Object of Inventory Item
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     */
    public int RemoveFromInventory(tcInventoryItem acInventoryItem) throws SQLException {

        if (con.isClosed()) throw new SQLException("Not Connected to Database");

        String lcQuery = "DELETE from" + "InvItems"
                + " where BarCodeID= '?'";

        PreparedStatement lcPreparedStmt = con.prepareStatement(lcQuery);
        lcPreparedStmt.setString(1,acInventoryItem.BarCodeID);

        return lcPreparedStmt.executeUpdate();
    }

    /**
     * @brief Method Checks if item is in database
     * @param arcBarCodeID Bar Code ID
     * @param arcBareCode Bard Code Type
     * @return true if Add was successful false if there was an error
     */
    public boolean CheckInventory( String arcBarCodeID,BarcodeType arcBareCode) throws SQLException {

        if (con.isClosed()) throw new SQLException("Not Connected to Database");

        // Only thing that will be valid is the BarCodeID

        String lcQuery = "Select * from" + "InvItems"
                + " where BarCodeID= '?' AND where BarCodeType='?'";

        PreparedStatement lcPreparedStmt = con.prepareStatement(lcQuery);
        lcPreparedStmt.setString(1,arcBarCodeID);
        lcPreparedStmt.setString(2,arcBareCode.toString());


        return lcPreparedStmt.execute();
    }

    /**
     * @brief Method Checks if item is in databasee
     * @return true if database was successfully exported
     */
    public boolean ExportDatabase()
    {


        return true;
    }

    /**
     * @brief Method imports database from a csv file
     * @param acFilePath - path to csv / xlxs to import
     * @return true if database was successfully imported
     */
    public boolean ImportDatabase(String acFilePath)
    {


        return true;
    }


    /**
     * @brief Method backsup database to a dump file
     */
    public void BackupDatabase()
    {


    }

}
