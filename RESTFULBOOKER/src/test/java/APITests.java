import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import java.util.List;

import static  org.hamcrest.Matchers.not;

public class APITests {


    // GetBooking by id
    // Get booking when id is string
    // POst a booking
    // Update
    // Delete


    public List<Integer> GetBookingsIds()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().get();
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("bookingid");
    }


    public String GetAUTHCode()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/auth";
        String credentials = "{ \"username\" : \"admin\", \"password\" : \"password123\" }";
        //System.out.println(credentials);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post();

        response.then().assertThat().statusCode(200);
        //response.then().log().body();
        JsonPath jsonPath = response.jsonPath();
        String code = jsonPath.get("token");
        return code;
    }

    @Test
    public void GetBookingById()
    {
       RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
       Integer bookingId = GetBookingsIds().get(0);
       Response response = RestAssured.given().pathParam("id", bookingId).when().get("/{id}");
       response.then().assertThat().statusCode(200);
       response.then().assertThat().body("size", not(0));
       response.then().log().body();
    }

    @Test
    public void GetBookingByIdError()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        String bookingId = "one";
        Response response = RestAssured.given().pathParam("id", bookingId).when().get("/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(400);
        response.then().assertThat().body(Matchers.equalTo("Bad Request"));

    }

    @Test
    public void PostNewBooking() throws JsonProcessingException
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Booking newBooking = new Booking();
        newBooking.setFirstname("Juan");
        newBooking.setLastname("Perez");
        newBooking.setTotalprice(400);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("2024-10-10","2024-10-12"));
        newBooking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(newBooking);
        System.out.println(payload);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();

       response.then().assertThat().statusCode(200);
       response.then().body("booking.firstname", Matchers.equalTo(newBooking.getFirstname()));
       response.then().body("booking.lastname", Matchers.equalTo(newBooking.getLastname()));
       response.then().body("booking.totalprice", Matchers.equalTo(newBooking.getTotalprice()));
       response.then().body("booking.depositpaid", Matchers.equalTo(newBooking.getDepositpaid()));



    }

    @Test
    public void PostNewBookingErrorDates() throws JsonProcessingException
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Booking newBooking = new Booking();
        newBooking.setFirstname("Juan");
        newBooking.setLastname("Perez");
        newBooking.setTotalprice(400);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("Mañana","Domingo"));
        newBooking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(newBooking);
        System.out.println(payload);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();

        response.then().assertThat().statusCode(400);
        response.then().assertThat().body(Matchers.equalTo("Bad Request"));
    }

    @Test
    public void PutBooking() throws JsonProcessingException {
        String authCode = GetAUTHCode();
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Integer bookingId = GetBookingsIds().get(0);

        Booking newBooking = new Booking();
        newBooking.setFirstname("Update Name");
        newBooking.setLastname("Update LastName");
        newBooking.setTotalprice(400);
        newBooking.setDepositpaid(true);
        newBooking.setBookingdates(new BookingDates("2024-10-10","2024-10-12"));
        newBooking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(newBooking);
        System.out.println(payload);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + authCode)
                .body(payload)
                .and()
                .pathParam("id", bookingId)
                .put("/{id}");

        response.then().assertThat().statusCode(200);
        response.then().log().body();

        response.then().body("firstname", Matchers.equalTo(newBooking.getFirstname()));
        response.then().body("lastname", Matchers.equalTo(newBooking.getLastname()));
        response.then().body("totalprice", Matchers.equalTo(newBooking.getTotalprice()));
        response.then().body("depositpaid", Matchers.equalTo(newBooking.getDepositpaid()));


    }


}
