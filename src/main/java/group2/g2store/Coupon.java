package group2.g2store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Coupon {

    private String couponid;
    private int value;
    private String expirydate;
    private int status;

    public Coupon(String couponid, int value, String expirydate, int status) {
        this.couponid = couponid;
        this.value = value;
        this.expirydate = expirydate;
        this.status = status;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Phương thức chuyển đổi giá trị status thành chuỗi
    public String getStatusText() {
        return status == 1 ? "Expired" : "Ready";
    }

    public LocalDateTime getExpiryDateTime() {
        // Định dạng ngày tháng từ chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // Parse chuỗi thành LocalDateTime
        return LocalDateTime.parse(expirydate, formatter);
    }
}
