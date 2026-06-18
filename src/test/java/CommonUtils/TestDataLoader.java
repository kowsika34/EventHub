package CommonUtils;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Pojo.RegisterRequestpojo;

public class TestDataLoader {

    static String testDataFile =
            ConfigReader.getProperty("testDataFilePath");

    // =========================
    // Load POST / PUT Request Data
    // =========================
    public static <T> T loadTestDatafor_Post_Put(
            String requestType,
            Class<T> clazz,
            int index) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode =objectMapper.readTree(new File(testDataFile));

        JsonNode requestsNode = rootNode.get("requests");
        for (JsonNode requestNode : requestsNode) {

          //  JsonNode arrayNode = requestNode.get(requestType);

          //  if (arrayNode != null && arrayNode.isArray()) {
            	
            	
            	
            	Iterator<Map.Entry<String, JsonNode>> fields =
            	        requestNode.fields();

            	while (fields.hasNext()) {

            	    Map.Entry<String, JsonNode> field =
            	            fields.next();

            	    if (field.getKey().equalsIgnoreCase(requestType)) {

            	        JsonNode arrayNode = field.getValue();

            	        if (!arrayNode.isArray()) {
            	            throw new RuntimeException(
            	                "Expected array for request type: "
            	                + requestType);
            	        }

            	        if (index >= arrayNode.size()) {
            	            throw new RuntimeException(
            	                "Index out of range: " + index);
            	        }

            	        return objectMapper.treeToValue(
            	                arrayNode.get(index),
            	                clazz);
            	    }
            	}

           }
    
         // Loop through requests
        //for (JsonNode requestNode : requests) {
           // Iterator<Map.Entry<String, JsonNode>> fields =
                    //requestNode.fields();

           // while (fields.hasNext()) {

              //  Map.Entry<String, JsonNode> field =
                       // fields.next();

                // Match request type
              //  if (field.getKey()
                      //  .equalsIgnoreCase(requestType)) {

                    // Convert JSON -> POJO
                   // return objectMapper.treeToValue(
                           // field.getValue(),
                           // clazz);
              //  }
            //}
       // }

         throw new RuntimeException(
               "Request type not found: " + requestType);
    
    
    }


    // =========================
    // Load GET Response Data
    // =========================
    public static JsonNode loadTestDatafor_Get(
            String requestType) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode =
                objectMapper.readTree(new File(testDataFile));

        JsonNode requests = rootNode.get("requests");

        for (JsonNode requestNode : requests) {

            Iterator<Map.Entry<String, JsonNode>> fields =
                    requestNode.fields();

            while (fields.hasNext()) {

                Map.Entry<String, JsonNode> field =
                        fields.next();

                if (field.getKey()
                        .equalsIgnoreCase(requestType)) {

                    return field.getValue();
                }
            }
        }

        throw new RuntimeException(
                "Request type not found: " + requestType);
    }




	
}