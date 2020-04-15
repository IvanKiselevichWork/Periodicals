package by.kiselevich.periodicals.util;

import org.junit.Assert;
import org.junit.Test;

public class HashUtilTest extends Assert {

    @Test
    public void testGetHash1() {
        String data = "12345678";
        String salt = "admin";
        String hashExpected = "7e88a31c432e2e146b7aed08a0b5faf16db34d3487358a02eace0c5c43aa2c86d4f21083840008fc2e2c2ed313fa4d50c24caa8b8c0d72b53a8ed4f525c13f71";
        String hashResult = HashUtil.getHash(data.toCharArray(), salt);
        assertEquals(hashExpected, hashResult);
    }

    @Test
    public void testGetHash2() {
        String data = "12345678";
        String salt = "Admin";
        String hashExpected = "7e88a31c432e2e146b7aed08a0b5faf16db34d3487358a02eace0c5c43aa2c86d4f21083840008fc2e2c2ed313fa4d50c24caa8b8c0d72b53a8ed4f525c13f71";
        String hashResult = HashUtil.getHash(data.toCharArray(), salt);
        assertNotEquals(hashExpected, hashResult);
    }

    @Test
    public void testGetHash3() {
        String data = "1234567";
        String salt = "admin";
        String hashExpected = "7e88a31c432e2e146b7aed08a0b5faf16db34d3487358a02eace0c5c43aa2c86d4f21083840008fc2e2c2ed313fa4d50c24caa8b8c0d72b53a8ed4f525c13f71";
        String hashResult = HashUtil.getHash(data.toCharArray(), salt);
        assertNotEquals(hashExpected, hashResult);
    }
}
