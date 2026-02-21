import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherClient {

    public static void main(String[] args) {

        try {

            // Chennai coordinates
            String latitude = "13.0827";
            String longitude = "80.2707";

            // Open-Meteo public API URL
            String apiUrl = "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=" + latitude
                    + "&longitude=" + longitude
                    + "&current_weather=true";

            // Create URL object
            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Set request headers
            connection.setRequestProperty("Accept", "application/json");

            // Get response code
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(response.toString());

                JSONObject currentWeather =
                        jsonObject.getJSONObject("current_weather");

                double temperature =
                        currentWeather.getDouble("temperature");

                double windspeed =
                        currentWeather.getDouble("windspeed");

                double winddirection =
                        currentWeather.getDouble("winddirection");

                String time =
                        currentWeather.getString("time");

                // Display structured output
                System.out.println("===== Weather Data =====");
                System.out.println("Location: Chennai");
                System.out.println("Time: " + time);
                System.out.println("Temperature: " + temperature + " °C");
                System.out.println("Wind Speed: " + windspeed + " km/h");
                System.out.println("Wind Direction: " + winddirection + "°");

            } else {
                System.out.println("HTTP Error Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}