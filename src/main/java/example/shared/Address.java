package example.shared;

/**
 * 住所を表します。
 *
 * @author kawasima
 */
public record Address(String country,
                      String postalCode,
                      String region,
                      String streetAddress) {
}
