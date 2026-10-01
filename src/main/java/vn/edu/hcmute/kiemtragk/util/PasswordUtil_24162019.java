package vn.edu.hcmute.kiemtragk.util;

import java.security.*;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil_24162019 {
  private static final SecureRandom RANDOM = new SecureRandom();

  // 8-byte random salt + 16-byte PBKDF2 hash -> exactly 32 Base64 characters.
  public static String hash(String password) {
    byte[] salt = new byte[8];
    RANDOM.nextBytes(salt);
    byte[] out = new byte[24];
    System.arraycopy(salt, 0, out, 0, 8);
    System.arraycopy(derive(password, salt), 0, out, 8, 16);
    return Base64.getEncoder().encodeToString(out);
  }

  public static boolean matches(String password, String stored) {
    try {
      byte[] b = Base64.getDecoder().decode(stored);
      return b.length == 24
          && MessageDigest.isEqual(
              Arrays.copyOfRange(b, 8, 24), derive(password, Arrays.copyOf(b, 8)));
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private static byte[] derive(String password, byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 210000, 128);
    try {
      return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    } finally {
      spec.clearPassword();
    }
  }
}
