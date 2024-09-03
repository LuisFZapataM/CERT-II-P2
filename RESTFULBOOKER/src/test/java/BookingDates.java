import lombok.Getter;
import lombok.Setter;

public class BookingDates {
    @Getter @Setter
    String checkin;

    @Getter @Setter
    String checkout;

    public BookingDates()
    {

    }

    public BookingDates(String checkin, String checkout)
    {
        this.checkin = checkin;
        this.checkout = checkout;
    }
}
