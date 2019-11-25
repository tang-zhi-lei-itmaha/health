package com.tang;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AAAAA {

    @Test
    public void test01() {
        String ss ="aaaax.xlxs";
        boolean xlxs = ss.endsWith(".xlxs");
        System.out.println(xlxs);
    }

    @Test
    public void test02() throws ParseException {
       String date="2019-11-25";
        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        System.out.println(parse);
    }
}
