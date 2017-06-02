package sample;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJPlayer {

    private int pHandValue = 0;
    private int dHandValue = 0;

    public static final int H_LIMIT = 17;
    private static final int P_HAND = 1;
    private static final int D_HAND = 2;

    private boolean showDealerCards;
    private boolean pStay;
    private boolean dStay;

    private Deck d;
    private List<Card> pCards;
    private List<Card> dCards;

    private int pWins = 0;
    private int dWins = 0;

    private int CardsStartCount = 2;
    private int maxHandCount = 20;



    public BJPlayer() {
        d = new Deck();
        dCards = new ArrayList<>();
        pCards = new ArrayList<>();
    }

    public void play() {
        dCards = new ArrayList<>();
        pCards = new ArrayList<>();
        showDealerCards = false;
        dStay = false;
        pStay = false;
        dHandValue = 0;
        pHandValue = 0;

        System.out.println("$$$$ BLACK JACK GAME $$$$");

        System.out.println("$$$ Pierwsze rozdanie $$$");
        System.out.println("Player oraz krupier dostają po dwie karty");

        for (int i = 0; i < CardsStartCount; i++) {
            tieHand(P_HAND);
            tieHand(D_HAND);
        }
        chckHand(P_HAND);
        chckHand(D_HAND);
        deal();

        if (!showDealerCards) {
            System.out.println("Karty krupiera są teraz widoczne");
        }
        showDealerCards = true;

        chckHand(P_HAND);
        chckHand(D_HAND);

        if (pHandValue == 21) {
            System.out.print("Gratulacje! Perfekcyjna 21! Wygrałeś!");
            pWins++;
        }
        else if (dHandValue == 21) {
            System.out.print("Krupier wygrał! Perfekcyjna 21!");
            dWins++;
        }
        else if (dHandValue > 21) {
            System.out.print("Krupier przekroczył wartość 21! Wygrywasz! Gratulacje!");
            pWins++;
        }
        else if (pHandValue > 21) {
            System.out.print("Niestety, przekroczyłeś wartość 21, przegrałeś!");
            dWins++;
        }

        if (pStay && dHandValue <= 21) {
            if (pHandValue == dHandValue) {
                System.out.print("Krupier oraz gracz posiadają identyczną liczbę samo punktów! REMIS!!!");
                pWins++;
                dWins++;
            } else if (pHandValue > dHandValue) {
                System.out.print("Wygrałeś większą liczbą punktów!");
                pWins++;
            } else if (dHandValue > pHandValue) {
                System.out.print("Przegrywasz mniejszą liczbą punktów!");
                dWins++;
            }
        }
    }

    private void deal() {
        String choice = "#";

        while (choice.charAt(0) != 'o' && dHandValue < 21 && pHandValue < 21 && !pStay) {
            System.out.println("Jaki ruch chcesz wykonać?");
            System.out.print("$$$$$$$$$$$$$$$$$$$$ Opcje $$$$$$$$$$$$$$$$$$$\n");
            System.out.print("$                                            $\n");
            System.out.print("$   (C)zekaj      (D)obierz      (O)dejdz    $\n");
            System.out.print("$                                            $\n");
            System.out.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n\n     ");

            choice = input().toLowerCase();

            switch (choice.charAt(0)) {
                case 'd':
                    System.out.print("\n Dobierasz.\n");
                    hit(P_HAND);
                    break;
                case 'c':
                    System.out.print("\n Czekasz.\n");
                    pStay = true;
                    chckHand(P_HAND);
                    break;
                case 'o':
                    break;
                default:
                    System.out.print("\n Błąd wyboru!\n");
                    choice = "INVALID_INPUT";
                    break;
            }

            if (!choice.equals("INVALID_INPUT")) {
                if (pHandValue < 21) {
                    if (dHandValue < H_LIMIT) {
                        System.out.print("\n Krupier dobiera kartę.\n");
                        hit(D_HAND);
                    } else {
                        System.out.print("\n Krupier czeka.\n");
                        dStay = true;
                        chckHand(D_HAND);
                    }
                }
            }
        }
    }

    private void hit(int who) {
        tieHand(who);
        chckHand(who);

        if (who == P_HAND) {
            if (pHandValue > 21) {
                for (int i = 0; i < pCards.size(); i++) {
                    Card card = pCards.get(i);
                    if (card.asChecker()) {
                        System.out.println("Wartość twojej ręki jest większa niż 21, ale masz Asa.");
                        System.out.println("Zmieniamy wartość asa z 11 na 1");
                        card.setFirstVal(1);
                        break;
                    }
                }

                chckHand(who);
            }
        } else {
            if (dHandValue > 21) {
                for (int i = 0; i < dCards.size(); i++) {
                    Card card = dCards.get(i);
                    if (card.asChecker()) {
                        System.out.println("Wartość ręki krupiera jest większa niż 21, ale posiada Asa.");
                        System.out.println("Zmieniamy wartość asa z 11 na 1");
                        card.setFirstVal(1);
                        break;
                    }
                }

                chckHand(who);
            }
        }
    }

    private void tieHand(int who) {
        switch (who) {
            case P_HAND:
                pCards.add(d.RandCard());
                break;
            case D_HAND:
                dCards.add(d.RandCard());
                break;
        }
    }

    private void chckHand(int who) {
        int handValue = 0;
        switch (who) {
            case P_HAND:
                for (int i = 0; i < pCards.size(); i++) {
                    handValue += pCards.get(i).getFirstVal();
                }
                System.out.println("Ilość punktów gracza: " + handValue);
                pHandValue = handValue;
                break;
            case D_HAND:
                if (showDealerCards) {
                    for (int i = 0; i < dCards.size(); i++) {
                        handValue += dCards.get(i).getFirstVal();
                    }
                    System.out.println("Ilość punktów krupiera: " + handValue);
                    dHandValue = handValue;
                } else {
                    for (int i = 1; i < dCards.size(); i++) {
                        handValue += dCards.get(i).getFirstVal();
                    }
                    System.out.println("Ilość punktów krupiera: " + handValue + " (JEDNA KARTA JEST UKRYTA)");
                }
                break;
        }
    }

    public static String input() {
        LineNumberReader onion = new LineNumberReader(new InputStreamReader(System.in));
        String in = "";
        try {
            in = onion.readLine();
        } catch (IOException APPLE) {
            System.err.println("Input ERROR !!!!");
        }
        return in;
    }

    public int getpWins() {
        return pWins;
    }

    public int getdWins() {
        return dWins;
    }
}
