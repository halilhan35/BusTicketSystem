package models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameGenerator {
    private static List<String> names = new ArrayList<>();
    private static List<String> surnames = new ArrayList<>();
    private static Random random = new Random();
    private static boolean isLoaded = false;

    /**
     * JSON dosyasından isimleri yükler
     */
    public static void loadNames() {
        if (isLoaded) return;

        try {
            // Farklı konumları dene
            String[] possiblePaths = {
                    "names.json",
                    "src/names.json",
                    "resources/names.json",
                    "src/main/resources/names.json"
            };

            String jsonContent = null;
            String usedPath = null;

            for (String path : possiblePaths) {
                if (Files.exists(Paths.get(path))) {
                    jsonContent = Files.readString(Paths.get(path));
                    usedPath = path;
                    break;
                }
            }

            if (jsonContent != null) {
                parseJson(jsonContent);
                System.out.println("✅ İsim listesi yüklendi (" + usedPath + "): " +
                        names.size() + " isim, " + surnames.size() + " soyisim");
            } else {
                loadDefaultNames();
                System.out.println("⚠️  JSON dosyası bulunamadı, varsayılan isimler kullanılıyor.");
            }

            isLoaded = true;

        } catch (Exception e) {
            System.err.println("❌ İsim listesi yüklenirken hata: " + e.getMessage());
            loadDefaultNames();
            isLoaded = true;
        }
    }

    /**
     * Basit JSON parser (external library kullanmadan)
     */
    private static void parseJson(String jsonContent) {
        // "names" array'ini çıkar
        Pattern namesPattern = Pattern.compile("\"names\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher namesMatcher = namesPattern.matcher(jsonContent);

        if (namesMatcher.find()) {
            String namesArray = namesMatcher.group(1);
            extractStrings(namesArray, names);
        }

        // "surnames" array'ini çıkar
        Pattern surnamesPattern = Pattern.compile("\"surnames\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher surnamesMatcher = surnamesPattern.matcher(jsonContent);

        if (surnamesMatcher.find()) {
            String surnamesArray = surnamesMatcher.group(1);
            extractStrings(surnamesArray, surnames);
        }
    }

    /**
     * JSON array'inden string'leri çıkarır
     */
    private static void extractStrings(String arrayContent, List<String> targetList) {
        Pattern stringPattern = Pattern.compile("\"([^\"]+)\"");
        Matcher stringMatcher = stringPattern.matcher(arrayContent);

        while (stringMatcher.find()) {
            targetList.add(stringMatcher.group(1));
        }
    }

    /**
     * JSON yüklenemezse varsayılan isimleri kullan
     */
    private static void loadDefaultNames() {
        names.addAll(Arrays.asList(
                "Ahmet", "Mehmet", "Ayşe", "Fatma", "Ali", "Veli", "Zeynep", "Murat",
                "Elif", "Emre", "Selin", "Burak", "Özge", "Kemal", "Derya", "Okan",
                "Seda", "Onur", "Gizem", "Cem", "Pınar", "Serkan", "Ebru", "Tolga",
                "Aslı", "Eren", "Gamze", "Barış", "İrem", "Koray", "Tuğba", "Volkan"
        ));

        surnames.addAll(Arrays.asList(
                "Yılmaz", "Kaya", "Demir", "Çelik", "Şahin", "Yıldız", "Öz", "Arslan",
                "Doğan", "Aslan", "Çetin", "Kara", "Koç", "Kurt", "Özkan", "Şimşek",
                "Soylu", "Yıldırım", "Özdemir", "Acar", "Ünal", "Akın", "Duman", "Çakır"
        ));
    }

    /**
     * Random isim döndürür
     */
    public static String getRandomFirstName() {
        if (!isLoaded) loadNames();
        if (names.isEmpty()) return "Anonim";
        return names.get(random.nextInt(names.size()));
    }

    /**
     * Random soyisim döndürür
     */
    public static String getRandomLastName() {
        if (!isLoaded) loadNames();
        if (surnames.isEmpty()) return "Kullanıcı";
        return surnames.get(random.nextInt(surnames.size()));
    }

    /**
     * Random tam isim döndürür
     */
    public static String getRandomFullName() {
        return getRandomFirstName() + " " + getRandomLastName();
    }

    /**
     * Yüklenen isim sayısını döndürür
     */
    public static int getNameCount() {
        if (!isLoaded) loadNames();
        return names.size();
    }

    /**
     * Yüklenen soyisim sayısını döndürür
     */
    public static int getSurnameCount() {
        if (!isLoaded) loadNames();
        return surnames.size();
    }
}