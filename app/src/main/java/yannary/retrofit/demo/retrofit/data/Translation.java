package yannary.retrofit.demo.retrofit.data;

/**
 * Created by zhangyanyan on 2018/2/27.
 */

public class Translation {
    public int status;
    public content content;

    public class content {
        public String from;
        public String to;
        public String vendor;
        public String out;
        public int errNo;
    }
}
