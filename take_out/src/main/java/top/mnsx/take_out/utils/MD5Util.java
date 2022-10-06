package top.mnsx.take_out.utils;

import lombok.val;
import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 16:51
 * @Description: MD5加密解密工具
 */
public class MD5Util {
    // 默认盐值
    private static final String salt = "7655d825";

    public static void main(String[] args) {
        // f7266e6ee3e88b58c87c7264a5477c13
        System.out.println(inputPassToTPass("123123", salt));
    }

    /**
     * 普通md5加密
     * @param src 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    /**
     * md5加密，需要提供一个盐值，加上默认盐值，进行两次加密后返回
     * @param inputPass 未加密字符串
     * @param tSalt 盐值
     * @return 加密后字符串
     */
    public static String inputPassToTPass(String inputPass, String tSalt) {
        if (tSalt == null) {
            tSalt = salt;
        }
        String midPass = inputPassToMidPass(inputPass);
        return midPassToTPass(midPass, tSalt);
    }

    private static String inputPassToMidPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(0) + salt.charAt(3);
        return md5(str);
    }
    private static String midPassToTPass(String midPass, String tSalt) {
        String str = "" + tSalt.charAt(0) + tSalt.charAt(2) + midPass + tSalt.charAt(0) + tSalt.charAt(3);
        return md5(str);
    }
}
