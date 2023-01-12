package CucumberGoogleMaps.Resources;

import POJO.AddPlace;
import POJO.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayLoad(String name, String language, String address)
    {
        AddPlace place =new AddPlace();
        place.setAccuracy(50);
        place.setAddress(address);
        place.setLanguage(language);
        place.setPhone_number("(+91) 983 893 3937");
        place.setWebsite("https://rahulshettyacademy.com");
        place.setName(name);
        List<String> myList =new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");

        place.setTypes(myList);
        Location location =new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        place.setLocation(location);
        return place;
    }

    public String updatePlacePayload(String placeId)
    {
        String newAddress = "70 Summer walk, USA";

        return "{\n" +
                "\"place_id\":\"" + placeId + "\",\n" +
                "\"address\":\"" + newAddress + "\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

    public String deletePlacePayload(String placeId)
    {
        return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
    }
}
