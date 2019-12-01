package com.tang;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AAAAA {

    @Test
    public void test01() {
        String ss = "aaaax.xlxs";
        boolean xlxs = ss.endsWith(".xlxs");
        System.out.println(xlxs);
    }

    @Test
    public void test02() throws ParseException {
        String date = "2019-11-25";
        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        System.out.println(parse);
    }

    @Test
    public void test03() {
        int[] arr = {32, 432, 534, 654, 534534};
        int target = 56;
        int[] doIt = this.doIt(arr, target);
        for (int i : doIt) {
            System.out.println(i);
        }
    }

    private int[] doIt(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == target - arr[j]) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("是在是找不到了啊啊啊啊啊");
    }

    @Test
    public void test04() {
        int[] ints = {2, 3, 5, 6};
        Integer integer = singleNumber(ints);
        System.out.println(integer);
    }

    public Integer singleNumber(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    continue;
                }
            }
            return nums[i];
        }
        return null;
    }
}
