package domain;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class AuthorOrders {
    private String titleId;
    private String authorName;
    private String phone;
    private String address;

    public AuthorOrders(){};

    @Override
    public String toString() {
        return "AuthorOrders{" +
                "titleId='" + titleId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
