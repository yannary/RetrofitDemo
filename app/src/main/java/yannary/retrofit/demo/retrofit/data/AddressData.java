package yannary.retrofit.demo.retrofit.data;

/**
 * Created by zhangyanyan on 2018/4/24.
 */

public class AddressData {
    private String name;  // 收货人
    private String mobliephone;  // 手机号
    private String shdz;  // 地址
    private String postmark;  // 邮编

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobliephone() {
        return mobliephone;
    }

    public void setMobliephone(String mobliephone) {
        this.mobliephone = mobliephone;
    }

    public String getShdz() {
        return shdz;
    }

    public void setShdz(String shdz) {
        this.shdz = shdz;
    }

    public String getPostmark() {
        return postmark;
    }

    public void setPostmark(String postmark) {
        this.postmark = postmark;
    }
}
