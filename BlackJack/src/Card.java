package blackjack;

import java.util.Random;
import java.util.Scanner;

class Card {
    private static int[][] card = new int[4][13];

    //카드세팅
    static void shuffle() {
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 12; j++) {
                card[i][j] = 1;

            }

        }

    }

    //무슨종류카드인지 판별
    static String whatCard(int a) {

        return switch (a) {
            case 0 -> "Diamond";
            case 1 -> "Clover";
            case 2 -> "Heart";
            default -> "Spade";

        };

    }


    //어느정도 숫자인지 판별
    static String whatCardNum(int a) {

        return switch (a) {
            case 0 -> "A";
            case 1 -> "2";
            case 2 -> "3";
            case 3 -> "4";
            case 4 -> "5";
            case 5 -> "6";
            case 6 -> "7";
            case 7 -> "8";
            case 8 -> "9";
            case 9 -> "10";
            case 10 -> "K";
            case 11 -> "Q";
            default -> "J";

        };

    }

    //위 두가지 판별값을 조합
    static String completedCard(String a, String b) {
        return a + " " + b;

    }

    //카드 드로우(서브클래스전용)
    static int[] draw() {
        Random random = new Random();

        while (true) {
            int randCard = random.nextInt(4);
            int randCardNum = random.nextInt(12);

            if (card[randCard][randCardNum] == 1) {
                card[randCard][randCardNum] = 0;
                return new int[]{randCard, randCardNum};

            }

        }


    }

    //플레이어 점수계산
    static int sumScore(int players, int card) throws InterruptedException  {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        switch (card) {
            case 1 -> {
                return 2;
            }
            case 2 -> {
                return 3;
            }
            case 3 -> {
                return 4;
            }
            case 4 -> {
                return 5;
            }
            case 5 -> {
                return 6;
            }
            case 6 -> {
                return 7;
            }
            case 7 -> {
                return 8;
            }
            case 8 -> {
                return 9;
            }
            case 9, 10, 11, 12 -> { return 10;
            }
            default -> {
                Boolean system = true;
                while (system) {
                    system = false;
                    Thread.sleep(1000);
                    System.out.println("Dealer : Choose Ace score (1 or 11)");
                    System.out.print("Player" + (players + 1) + " : ");
                    int choose = scanner.nextInt();
                
                    if (choose == 1) {
                        Thread.sleep(1000);
                        return 1;

                    } else if (choose == 11) {
                        Thread.sleep(1000);
                        return 11;

                    } else {
                        System.out.println("Dealer: Wrong enswer");
                        system = true;
                    }
                }
                return 0;
            }
        }
    }



    static void howMuchAce(int a) {

    }

    //보유 카드출력
    static void showCard(int players) {
        System.out.println();

        for (int i = 0; i < players; i++) {
            System.out.print("Player" + (i + 1) + " : ");

            for (int j = 0; j < Player.count[i]; j++) {
                System.out.print(Player.card[i][j]);

                if (j < (Player.count[i]) - 1) {
                    System.out.print(", ");
            
                }
            }
            System.out.println();

        }
            System.out.println();
            System.out.print("Dealer : ");
            
            for (int j = 0; j < Dealer.cards.length; j++) {
                System.out.println(Dealer.cards[j]);
                if (!(Dealer.cards[j + 1] == null)) {
                    System.out.println(", ");
                } else {
                    System.out.println();
                    break;
                }
            }
        

    }

}
