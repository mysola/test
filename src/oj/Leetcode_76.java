package oj;

import java.util.HashMap;
import java.util.HashSet;

public class Leetcode_76 {

  public static void main(String[] args) {
    new Leetcode_76().minWindow("ADOBECODEBANC", "ABC");
  }

  public String minWindow(String s, String t) {
    int start = 0, counter = t.length(), min = Integer.MAX_VALUE;
    HashMap<Character, Integer> flags = new HashMap<>();
    for (char c : t.toCharArray()) {
      Integer integer = flags.get(c);
      flags.put(c, integer == null ? 1 : integer + 1);
    }
    String res = "";
    for (int end = 0; end < s.length(); end++) {
      char c = s.charAt(end);
      Integer integer = flags.get(c);
      if (integer == null) {
        continue;
      }
      if (integer > 0) {
        counter--;
      }
      flags.put(c, integer - 1);
      while (counter == 0) {
        if (min >= end - start) {
          min = end - start;
          res = s.substring(start, end + 1);
        }
        c = s.charAt(start++);
        Integer count = flags.get(c);
        if (null != count) {
          flags.put(c, count + 1);
          if (count >= 0) {
            counter++;
          }
        }
      }
    }
    return res;
  }
}


