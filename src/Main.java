import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {280, 270, 250};
    public static int[] heroesDamage = {10, 15, 20};
    public static int[] medicHealth = {300};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic"};
    public static String[] playerNames = {"Игрок 1", "Игрок 2", "Игрок 3", "Медик"};
    public static int roundCounter;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            round();
        }
    }

    public static void round() {
        if (bossHealth > 0) {
            changeDefenceType();
            bossAttack();
        }
        heroesAttack();
        medicHeal();
        printStatistics();
    }

    public static void changeDefenceType() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Босс выбрал тип защиты: " + bossDefenceType);
    }

    public static void printStatistics() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Раунд: " + roundCounter);
        roundCounter++;
        System.out.println("Здоровье босса: " + bossHealth);

        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(playerNames[i] + " здоровье: " + heroesHealth[i] + " " + heroesDamage[i]);
        }
        System.out.println("Здоровье медика: " + medicHealth[0]);
        System.out.println("-----------------------------------------------------------");
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }

        // Урон медику от босса
        if (medicHealth[0] > 0) {
            if (medicHealth[0] - bossDamage < 0) {
                medicHealth[0] = 0;
            } else {
                medicHealth[0] = medicHealth[0] - bossDamage;
            }
        }
    }

    public static void medicHeal() {
        // Лечение после каждого раунда
        if (medicHealth[0] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    heroesHealth[i] += 25;
                    System.out.println("Медик вылечил " + playerNames[i] + " на 25 единиц здоровья.");
                    break;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefenceType)) {
                    int coeff = new Random().nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Критический урон: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Герои победили!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead) {
            System.out.println("Босс победил!!!");

        }

        return allHeroesDead;
    }
}