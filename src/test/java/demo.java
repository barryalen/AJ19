import utils.Authentication;
import utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class demo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("ccc");
        list.removeIf("aaa"::equalsIgnoreCase);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
