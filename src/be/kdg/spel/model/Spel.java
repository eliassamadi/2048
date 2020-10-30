package be.kdg.spel.model;
import java.io.*;
import java.util.Random;

public class Spel {
    private static final String SAVEDIR = "src/be/kdg/spel/files/";
    private String[] scores = new String[11];
    private String[] player = new String[11];
    private int score;
    private int value;
    private String naam;
    private String best;

    public Spel() {
        this(0);
    }

    public Spel(int value) {
        this.value = value;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Hier word er een random nummer gegenereerd
     *
     * @return Er word random een 2 of een 4 gereturneerd.
     */

    public int randomTile() {
        Random r = new Random();
        return (r.nextInt(2) + 1) * 2;

    }

    /**
     * Hier word de naam van de huidige persoon gelezen
     */


    public void naamInlezen() {
        try {
            BufferedReader br = new BufferedReader(new FileReader((SAVEDIR + "players.txt")));
            naam = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNaam() {
        return naam;
    }

    /**
     * Telkens de scores lezen
     */

    public void inlezenScores() {
        try {
            BufferedReader br = new BufferedReader(new FileReader((SAVEDIR + "highscores.txt")));
            int i = 0;
            String lijn = br.readLine();
            while (lijn != null) {
                String[] gegeven = lijn.split("-");
                player[i] = gegeven[0];
                scores[i] = gegeven[1];
                lijn = br.readLine();
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        best = scores[0];
    }

    /**
     * Hier worden alleen de 10 scores opgeslaan
     * De scores worden ook direct vergeleken met elkaar welke score een hoger cijfer heeft
     * Deze score wordt dan hoger geplaatst
     */

    public void scoreOpslaan() {
        for (int i = 10; i > 0; i--) {
            int temp = 0;
            String tempnaam = "";
            scores[10] = String.valueOf(score);
            player[10] = naam;
            if (Integer.parseInt(scores[i - 1]) < Integer.parseInt(scores[i])) {
                temp = Integer.parseInt(scores[i - 1]);
                scores[i - 1] = scores[i];
                scores[i] = String.valueOf(temp);
                tempnaam = player[i - 1];
                player[i - 1] = player[i];
                player[i] = String.valueOf(tempnaam);
            }
        }

        try (PrintWriter pw = new PrintWriter(new BufferedWriter((new FileWriter(SAVEDIR + "highscores.txt"))))) {
            for (int i = 0; i < 10; i++) {
                pw.write(player[i] + "-" + scores[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getBest() {
        return best;
    }
}
