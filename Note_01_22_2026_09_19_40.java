import java.util.*;
import java.util.stream.Collectors;

public class GameSelectorConsole {

    enum AgeGroup {
        AGE_2_4("2–4 roky"),
        AGE_5_7("5–7 let"),
        AGE_7_9("7–9 let"),
        AGE_8_10("8–10 let"),
        AGE_10_12("10–12 let"),
        AGE_15_PLUS("15+");

        final String label;
        AgeGroup(String label) { this.label = label; }
        public String toString() { return label; }
    }

    enum PlayStyle {
        KOOPERATIVNI("Kooperativní"),
        KOMPETITIVNI("Kompetitivní"),
        PARTY("Párty");

        final String label;
        PlayStyle(String label) { this.label = label; }
        public String toString() { return label; }
    }

    enum Theme {
        ZVIRATA("Zvířata"),
        MOTORICKE("Motorické"),
        POSTREH("Postřeh"),
        LOGIKA("Logika"),
        BUDOVANI("Budování"),
        DOBRODRUZSTVI("Dobrodružství"),
        PRIBEH("Příběh"),
        DEDUKCE("Dedukce"),
        PAMET("Paměť"),
        KOMUNIKACE("Komunikace"),
        POHADKY("Pohádky");

        final String label;
        Theme(String label) { this.label = label; }
        public String toString() { return label; }
    }

    static class Game {
        String name;
        AgeGroup age;
        int maxTime;
        PlayStyle style;
        List<Theme> themes;

        Game(String name, AgeGroup age, int maxTime, PlayStyle style, Theme... themes) {
            this.name = name;
            this.age = age;
            this.maxTime = maxTime;
            this.style = style;
            this.themes = List.of(themes);
        }
    }

    static List<Game> games = List.of(
        new Game("Little Action", AgeGroup.AGE_2_4, 15, PlayStyle.KOMPETITIVNI, Theme.ZVIRATA, Theme.MOTORICKE),
        new Game("Ice Cool", AgeGroup.AGE_5_7, 20, PlayStyle.KOMPETITIVNI, Theme.ZVIRATA, Theme.POSTREH),
        new Game("Spící královny", AgeGroup.AGE_5_7, 20, PlayStyle.KOMPETITIVNI, Theme.POHADKY),
        new Game("Jeníček a Mařenka", AgeGroup.AGE_5_7, 15, PlayStyle.KOOPERATIVNI, Theme.POHADKY, Theme.PAMET),
        new Game("Memoarrr!", AgeGroup.AGE_8_10, 30, PlayStyle.KOMPETITIVNI, Theme.ZVIRATA, Theme.PAMET)
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("VÝBĚR DESKOVÝCH HER – KNIHOVNA\n");

        AgeGroup age = choose("Vyber věkovou skupinu:", AgeGroup.values(), sc);
        Integer time = chooseTime(sc);
        PlayStyle style = choose("Vyber styl hry:", PlayStyle.values(), sc);
        Theme theme = choose("Vyber téma:", Theme.values(), sc);

        List<Game> result = games.stream()
            .filter(g -> age == null || g.age == age)
            .filter(g -> time == null || g.maxTime <= time)
            .filter(g -> style == null || g.style == style)
            .filter(g -> theme == null || g.themes.contains(theme))
            .collect(Collectors.toList());

        System.out.println("\nDOPORUČENÉ HRY:");
        if (result.isEmpty()) {
            System.out.println("– Nenalezena žádná shoda.");
        } else {
            result.forEach(g ->
                System.out.println("• " + g.name + " (" + g.maxTime + " min)")
            );
        }
    }

    static <T> T choose(String title, T[] values, Scanner sc) {
        System.out.println("\n" + title);
        System.out.println("0 – Nezáleží");

        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + " – " + values[i]);
        }

        int choice = sc.nextInt();
        return choice == 0 ? null : values[choice - 1];
    }

    static Integer chooseTime(Scanner sc) {
        System.out.println("\nMaximální doba hraní:");
        System.out.println("0 – Nezáleží");
        System.out.println("1 – 15 min");
        System.out.println("2 – 30 min");
        System.out.println("3 – 45 min");
        System.out.println("4 – 60 min");

        int choice = sc.nextInt();
        return switch (choice) {
            case 1 -> 15;
            case 2 -> 30;
            case 3 -> 45;
            case 4 -> 60;
            default -> null;
        };
    }
}
