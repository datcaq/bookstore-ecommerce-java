package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.text.Normalizer;

/** Legacy SQL Server text uses CP1258: compose vowel shapes, keep tones combining. */
@Converter
public class VietnameseTextConverter_24162019 implements AttributeConverter<String, String> {
  private static boolean tone(char c) {
    return c == '\u0300' || c == '\u0301' || c == '\u0303' || c == '\u0309' || c == '\u0323';
  }

  private static void append(StringBuilder result, StringBuilder cluster) {
    StringBuilder base = new StringBuilder(), tones = new StringBuilder();
    for (int i = 0; i < cluster.length(); i++) {
      char c = cluster.charAt(i);
      (tone(c) ? tones : base).append(c);
    }
    result.append(Normalizer.normalize(base, Normalizer.Form.NFC)).append(tones);
  }

  public String convertToDatabaseColumn(String text) {
    if (text == null) return null;
    StringBuilder result = new StringBuilder(), cluster = new StringBuilder();
    String decomposed = Normalizer.normalize(text, Normalizer.Form.NFD);
    for (int i = 0; i < decomposed.length(); i++) {
      char c = decomposed.charAt(i);
      if (Character.getType(c) != Character.NON_SPACING_MARK && cluster.length() > 0) {
        append(result, cluster);
        cluster.setLength(0);
      }
      cluster.append(c);
    }
    append(result, cluster);
    return result.toString();
  }

  public String convertToEntityAttribute(String text) {
    return text == null ? null : Normalizer.normalize(text, Normalizer.Form.NFC);
  }
}
