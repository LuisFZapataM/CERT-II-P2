import lombok.Getter;
import lombok.Setter;

public class Booking {

    @Getter @Setter
    String firstname;
    @Getter @Setter
    String lastname;
    @Getter @Setter
    Integer totalprice;
    @Getter @Setter
    Boolean depositpaid;
    @Getter @Setter
    BookingDates bookingdates;
    @Getter @Setter
    String additionalneeds;

    public Booking()
    {

    }

    public Booking(String firstname, String lastname, Integer totalprice, Boolean depositpaid, BookingDates bookingDates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingDates;
        this.additionalneeds = additionalneeds;
    }
}
