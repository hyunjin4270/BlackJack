package blackjack;

import java.util.Arrays;
import java.util.Scanner;

class Rule {
    private static int bestPlayer = 0;

    //초기화면
    static int start() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================");
        System.out.println("1.start     2.rules         3.exit");
        System.out.println("==================================");
        return scanner.nextInt();
    }

    //1. 게임 시작
    static int gameStart() throws InterruptedException {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        Card.shuffle();

        while (true) {
            System.out.print("Player : ");
            int playerNum = scanner.nextInt();
            scanner.nextLine();

            if (playerNum >= 2 && playerNum <= 8) {
                Player.cardSum = new int[playerNum];
                Player.bust = new Boolean[playerNum];
                Player.card = new String[playerNum][11];
                Player.count = new int[playerNum];
                Arrays.fill(Player.bust, true);
                System.out.println("Player : " + playerNum + ", Start");
                System.out.println();
                Thread.sleep(2000);

                for (int i = 0; i < playerNum; i++) {

                    for (int j = 1; j <= 2; j++) {
                        int[] card = Card.draw();
                        Player.card[i][Player.count[i]] = Card.completedCard(Card.whatCard(card[0]), Card.whatCardNum(card[1]));
                        System.out.print("Player" + (i + 1));
                        System.out.print(" Card" + j + " : " + Card.completedCard(Card.whatCard(card[0]), Card.whatCardNum(card[1])));
                        System.out.println();
                        Player.cardSum[i] += Card.sumScore(i, card[1]);
                        Player.count[i]++;
                        Thread.sleep(1000);

                    }
                    detectTie(i);
                    if (Player.cardSum[i] == 21) {
                        Thread.sleep(2000);
                        BlackJack(i);

                    } else {
                        System.out.println();

                    }
                }
                return playerNum;

            } else if (playerNum < 2) {
                System.out.println("Player can`t below 2");

            } else {
                System.out.println("Player can`t over 8");

            }
        }
    }

    //2. rules
    static void showRules() {
        System.out.println("    -Rule-");
        System.out.println();
        System.out.println("yes        - Answer Yes");
        System.out.println("No         - Answer No");
        System.out.println("hit        - Draw the card");
        System.out.println("stand      - SKip the card ");
        System.out.println("showcard   - Show the player's card.");
        System.out.println("yes        - Answer Yes");
        System.out.println("No         - Answer No");
    }

    //3. exit
    static void exit() {
        System.out.println("close BlackJack");
        System.exit(0);
    }

    //카드 드로우
    static void draw(int a) throws InterruptedException {
        int[] card = Card.draw();
        System.out.println();
        Player.card[a][Player.count[a]] = Card.completedCard(Card.whatCard(card[0]), Card.whatCardNum(card[1]));
        Player.cardSum[a] += Card.sumScore(a, card[1]);
        detectTie(a);
        Player.count[a]++;
        Thread.sleep(1000);
        System.out.println("Player" + (a + 1) + " Card" + Player.count[a] + " : " + Card.completedCard(Card.whatCard(card[0]), Card.whatCardNum(card[1])));
        System.out.println();

    }

    //인슈어런스
    static void Insurance(int players) throws InterruptedException {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        Boolean system = true;
        Boolean[] playerInsurance = new Boolean[players];
        int count = 0;

        System.out.println("Dealer : Insurance?");

        //플레이어 인슈어런스 응답
        for (int i = 0; i < players; i++) {

            while (system) {
                system = false;
                System.out.print("Player" + (i + 1) + " : ");
                String[] answer = new String[players];
                answer[i] = scanner.nextLine();

                if (answer[i].equals("yes")) {
                    playerInsurance[i] = true;
                    count++;

                } else if (answer[i].equals("no")) {
                    playerInsurance[i] = false;

                } else {
                    System.out.println("Dealer : Wrong answer");
                    Thread.sleep(1000);
                    System.out.println("Dealer : Insurance?");
                    system = true;

                }
            }
            system = true;

        }

        //플레이어 인슈어런스 응답전달
        Thread.sleep(1000);
        System.out.print("Dealer : ");
        for (int i = 0; i < players; i++) {

            if (playerInsurance[i]) {
                System.out.print("Player" + (i + 1));

                if (i < (count - 1)) {
                    System.out.print(", ");

                }
            }

        }
        if (count != 0) {
            System.out.println(" responded insurance.");
            Thread.sleep(3000);
            Dealer.judgeInsurance(players, playerInsurance);

        } else {
            System.out.println("Nobody Insurance");

        }
    }

    //버스트
    static void isOver(int player) {
        if (Player.cardSum[player] > 21) {
            System.out.println("Dealer : Player" + (player + 1) + " is over 21.");
            Player.bust[player] = false;
            Player.cardSum[player] = 0;
            Player.bustPlayer++;

        }
    }

    //블랙잭
    static void BlackJack(int players) {
        System.out.println("Dealer : Player" + (players + 1) + " made BlackJack");
        System.exit(0);

    }

    //우승자 가려내기
    static void whoIsWinner(int players) throws InterruptedException {
        Boolean[] winner = new Boolean[players];
        Arrays.fill(winner, false);
        System.out.println("Dealer : all player ended");
        Thread.sleep(2000);
        Dealer.drawDealer(players);
        Thread.sleep(2000);

        if (Dealer.cardSum > 21) {
            Dealer.bust = true;
        
        }

        if (Dealer.bust) {
            System.out.println("Dealer : Dealer busted");
            Thread.sleep(1000);
            for (int i = 0; i < players; i++) {
                if (Player.bust[i]) {
                    System.out.println("Player" + (i + 1));
                }
            }
        
        } else if (bestPlayer == Dealer.cardSum) {
            System.out.println("Dealer : Dealer and the player tied the game");

        } else if (bestPlayer < Dealer.cardSum) {
            System.out.println("Dealer : Dealer win");
            
        } else {
            System.out.print("Dealer : ");

            for (int i = 0; i < players; i++) {
                
                if (Player.cardSum[i] > Dealer.cardSum) {
                    System.out.print("Player" + (i + 1));
                    
                    if (i < (players - 1)) {
                        System.out.print(", ");

                    }   

                }

            }
            System.out.println(" win dealer");

        }
        Thread.sleep(2000);
        System.out.println();

        for (int i = 0; i < players; i++) {
            System.out.print("Player" + (i + 1) + " Score : ");
            if (Player.bust[i]) {
                System.out.println(Player.cardSum[i]);
            } else {
                System.out.println("Busted");
            }            
        }
    }

    //무승부 감지
    private static void detectTie(int player) {
        if (bestPlayer < Player.cardSum[player]) {
            bestPlayer = Player.cardSum[player];
        }
    }
}
