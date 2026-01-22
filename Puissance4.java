/*
    Programme qui implémente le jeu Puissance 4.
    Auteur : Marek Frydrychova
*/

import java.util.Scanner;

public class Puissance4 {
    static Scanner sc;
    
    // Constantes globales. Peuvent être utilisées dans toutes les fonctions.
    static final char CASE_VIDE = ' ';
    static final int ABANDON = -1;
    static final int SUITE_GAGNANTE = 4;
    
    /* Ajoutez/Modifiez les fonctions ci-dessous...
       REMISE #1
        6.4.1. afficherGrille
        6.4.2. creerGrille
        6.4.3. positionLibre
        6.4.4. getColonne
        6.4.5. insererJeton
        6.4.6. colonnePleine
       REMISE #2
        6.4.7. grillePleine
        6.4.8. gagneHorizontal
        6.4.9. gagneVertical
        6.4.10. verifierGagnant
       REMISE #3
        6.4.11. prochainJoueur
        6.4.12. main (à modifier)
        6.4.13. gagneDiagonaleDroiteBas
    */

    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";
    
    public static boolean gagneDiagonaleDroiteHaut(char[][] g, int ligne, int col) {
        int compteur = 1;
        int ligneCourante = ligne - 1;
        int colCourante = col + 1;
        while (ligneCourante >= 0 && colCourante < g[0].length && g[ligneCourante][colCourante] == g[ligne][col]) {
            compteur++;
            ligneCourante--;
            colCourante++;
        }
        ligneCourante = ligne + 1;
        colCourante = col - 1;
        while (ligneCourante < g.length && colCourante >= 0 && g[ligneCourante][colCourante] == g[ligne][col]) {
            compteur++;
            ligneCourante++;
            colCourante--;
        }
        return compteur >= SUITE_GAGNANTE;
    }

    public static void afficherGrille(char[][] g) {
        System.out.println();
        for (int i = 0; i < g.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < g[i].length; j++) {
                char cell = g[i][j];
                String toPrint;
                if (cell == 'r') {
                    toPrint = RED + 'o' + RESET;
                } else if (cell == 'j') {
                    toPrint = YELLOW + 'o' + RESET;
                } else {
                    toPrint = Character.toString(CASE_VIDE);
                }
                System.out.print(toPrint + " | ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int j = 0; j < g[0].length; j++) {
            System.out.print((j + 1) + "   ");
        }
        System.out.println("\n");
    }

    public static char[][] creerGrille(int hauteur, int largeur) {
        char[][] grille = new char[hauteur][largeur];
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = CASE_VIDE;
            }
        }
        return grille;
    }

    public static boolean positionLibre(char[][] g, int ligne, int col) {
        return g[ligne][col] == CASE_VIDE;
    }

    public static int getColonne(char[][] g) {
        int col;
        while (true) {
            System.out.print("Entrez le numéro de la colonne (1-" + g[0].length + ") ou " + ABANDON + " pour abandonner : ");
            col = sc.nextInt() - 1; 
            if (col == ABANDON) {
                return ABANDON;
            }
            if (col >= 0 && col < g[0].length) {
                return col;
            }
            System.out.println("Colonne invalide. Veuillez réessayer.");
        }
    }

    public static boolean colonnePleine(char[][] g, int col) {
        return g[0][col] != CASE_VIDE;
    }

    public static void insererJeton(char[][] g, int col, char jeton) {
        for (int i = g.length - 1; i >= 0; i--) {
            if (positionLibre(g, i, col)) {
                g[i][col] = jeton;
                break;
            }
        }
    }

    public static boolean grillePleine(char[][] g) {
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (g[i][j] == CASE_VIDE ) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean gagneHorizontal(char[][] g, int ligne, int col) {
        int compteur = 1;
        int colCourante = col - 1;
        while (colCourante >= 0 && g[ligne][colCourante] == g[ligne][col]) {
            compteur++;
            colCourante--;
        }
        colCourante = col + 1;
        while (colCourante < g[0].length && g[ligne][colCourante] == g[ligne][col]) {
            compteur++;
            colCourante++;
        }
        return compteur >= SUITE_GAGNANTE;
    }
    
    
    public static boolean gagneVertical(char[][] g, int ligne, int col) {
        int compteur = 1;
        int ligneCourante = ligne - 1;
        while (ligneCourante >= 0 && g[ligneCourante][col] == g[ligne][col]) {
            compteur++;
            ligneCourante--;
        }
        ligneCourante = ligne + 1;
        while (ligneCourante < g.length && g[ligneCourante][col] == g[ligne][col]) {
            compteur++;
            ligneCourante++;
        }
        return compteur >= SUITE_GAGNANTE;
    }

    public static boolean gagneDiagonaleDroiteBas(char[][] g, int ligne, int col) {
        int compteur = 1;
        int ligneCourante = ligne - 1;
        int colCourante = col - 1;
        while (ligneCourante >= 0 && colCourante >= 0 && g[ligneCourante][colCourante] == g[ligne][col]) {
            compteur++;
            ligneCourante--;
            colCourante--;
        }
        ligneCourante = ligne + 1;
        colCourante = col + 1;
        while (ligneCourante < g.length && colCourante < g[0].length && g[ligneCourante][colCourante] == g[ligne][col]) {
            compteur++;
            ligneCourante++;
            colCourante++;
        }
        return compteur >= SUITE_GAGNANTE;
    }

    public static boolean verifierGagnant(char[][] g, int ligne, int col) {
        return gagneHorizontal(g, ligne, col) || gagneVertical(g, ligne, col) ||
               gagneDiagonaleDroiteHaut(g, ligne, col) || gagneDiagonaleDroiteBas(g, ligne, col);
    }

    public static int prochainJoueur(int joueurCourant) {
        return (joueurCourant + 1) % 2;
    }

    public static void clearScreen() {  
    System.out.print("\033[H\033[2J");
    System.out.flush();
}  

public static void main(String[] args) {
        sc = new Scanner(System.in);
        
        final int DIM_MINIMUM = SUITE_GAGNANTE;
        final int DIM_MAXIMUM = 10;
        final int HAUTEUR_DEFAUT = 6;
        final int LARGEUR_DEFAUT = 7;
        final char[] JOUEURS = {'r', 'j'}; // rouge et jaune
        
        int hauteur = HAUTEUR_DEFAUT;
        int largeur = LARGEUR_DEFAUT;

        char[][] grille = creerGrille(hauteur, largeur);
        int joueurCourant = 0;
        boolean partieTerminee = false;
        afficherGrille(grille);
        while (!partieTerminee) {
            String joueurAffichage = (JOUEURS[joueurCourant] == 'r') ? RED + 'o' + RESET : YELLOW + 'o' + RESET;
            System.out.println("Au joueur " + (joueurCourant + 1) + " (" + joueurAffichage + ") de jouer.");
            int col = getColonne(grille);
            if (col == ABANDON) {
                System.out.println("Le joueur " + (joueurCourant + 1) + " a abandonné. Fin de la partie.");
                break;
            }
            if (colonnePleine(grille, col)) {
                System.out.println("La colonne " + (col + 1) + " est pleine. Veuillez choisir une autre colonne.");
                continue;
            }
            clearScreen();
            insererJeton(grille, col, JOUEURS[joueurCourant]);
            afficherGrille(grille);
            int ligneInseree = -1;
            for (int i = 0; i < hauteur; i++) {
                if (grille[i][col] != CASE_VIDE) {
                    ligneInseree = i;
                    break;
                }
            }
            if (verifierGagnant(grille, ligneInseree, col)) {
                joueurAffichage = (JOUEURS[joueurCourant] == 'r') ? RED + 'o' + RESET : YELLOW + 'o' + RESET;
                System.out.println("Félicitations au joueur " + (joueurCourant + 1) + " (" + joueurAffichage + ") qui a gagné !");
                partieTerminee = true;
            } else if (grillePleine(grille)) {
                System.out.println("La grille est pleine. Match nul !");
                partieTerminee = true;
            } else {
                joueurCourant = prochainJoueur(joueurCourant);
            }
        }
        
        sc.close();
    }
    
}
