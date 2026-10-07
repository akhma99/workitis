package academy.cashbackmerge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public CampaignConfig loadFromJSON(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        CampaignConfig config = objectMapper.readValue(file, CampaignConfig.class);
        return config;
    }
}
