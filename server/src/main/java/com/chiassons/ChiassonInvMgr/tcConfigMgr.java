package com.chiassons.ChiassonInvMgr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


class DbParams{

    private String url;
    private String user;
    private String password;

    public void setDbpassword(String dbpassword) {
        password = dbpassword;
    }

    public void setDburl(String dburl) {
        url = dburl;
    }

    public void setDbuser(String dbuser) {
        user = dbuser;
    }

    public String getDbpassword() {
        return password;
    }

    public String getDburl() {
        return url;
    }

    public String getDbuser() {
        return user;
    }
}



public class tcConfigMgr {



    static String mcConfigPaths;
    static String mcWorkspace;// Sub for {WORKSPACE} in configs
    static Map<String,String> mcFileMap;

    /**
     * @brief Constructor for ConfigMgr , needs CONFIG_PATHS system variable to be set to run
     */

    public tcConfigMgr()//TODO actually set up scripts to set these env vars
    {
        if (mcConfigPaths == null)
        {
           mcConfigPaths = "D:\\Intellij\\Projects\\ChiassonInvMgr\\ConfigPaths.json"; //System.getenv("CONFIG_PATHS");

        }
        if (mcWorkspace == null)
        {
           mcWorkspace = "D:\\Intellij\\Projects\\ChiassonInvMgr\\"; //System.getenv("WORKSPACE");
        }

        InitializeConfigPaths();
    }

    /**
     * @brief Inits config paths for all needed files replaces workspace from env variable
     *
     *
     */

    public void InitializeConfigPaths(){
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read the JSON file into a JsonNode
            File file = new File(mcConfigPaths);
            JsonNode rootNode = objectMapper.readTree(file);

            // Convert the JsonNode to a Map
            mcFileMap = new HashMap<>();
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                String value = field.getValue().asText();

                // Replace placeholder with the actual workspace value
                value = value.replace("{WORKSPACE}", mcWorkspace);

                mcFileMap.put(key, value);
            }

            // Print the resulting map
            mcFileMap.forEach((k, v) -> System.out.println(k + ": " + v));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     *
     * @param acDatabaseName Name of Database
     * @return DbParams object of database parameters
     */
    public DbParams GetDbCreds(String acDatabaseName)
    {
        DbParams lcDbParams = new DbParams();

        try 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(mcFileMap.get("DataBaseConfig"));//TODO have map value return path

            // Read JSON content into a JsonNode
            JsonNode rootNode = objectMapper.readTree(file);

            // Navigate to the "mysql-databases" node
            JsonNode mysqlDatabasesNode = rootNode.path("mysql-databases");

            // Extract the "ItemInventory" node
            JsonNode itemInventoryNode = mysqlDatabasesNode.path(acDatabaseName);

            // Extract values from the "ItemInventory" node
            lcDbParams.setDburl(itemInventoryNode.path("url").asText());
            lcDbParams.setDbuser(itemInventoryNode.path("user").asText());
            lcDbParams.setDbpassword(itemInventoryNode.path("password").asText());
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return lcDbParams;
    }



}
