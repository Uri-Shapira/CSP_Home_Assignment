import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PaymentMethod object represents a payment method or card that a Customer
 * can use to make transactions. The payment methods are saved in file "paymentMethods.txt" and have a foreign
 * key to the user they belong
 */
public class PaymentMethod {

    private long cardNumber;
    private int cvv;
    private int expirationDay;
    private int expirationMonth;
    private int expirationYear;

    public PaymentMethod(int cardNumber, int cvv, int expirationDay, int expirationMonth, int expirationYear){
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDay = expirationDay;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getExpirationDay() {
        return expirationDay;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getCvv() {
        return cvv;
    }

    public boolean isValid(){
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        String[] date = dateInString.split("-");
        if(Integer.parseInt(date[2]) < expirationYear ||
           (Integer.parseInt(date[2]) == expirationYear && Integer.parseInt(date[1]) < expirationMonth) ||
                (Integer.parseInt(date[2]) == expirationYear && Integer.parseInt(date[1]) == expirationMonth
                && Integer.parseInt(date[0]) <= expirationDay)){
            // Check with bank service that the card is valid - wait for response
            return true;
        }
        return false;
    }

}
