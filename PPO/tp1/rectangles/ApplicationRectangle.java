import java.lang.*;

class ApplicationRectangle {

    public static void main(String[] args) {
        ManipulateurRectangle manip = new ManipulateurRectangle();
        manip.creerTableauRectangle();

        System.out.println("\nListe des rectangles crées :\n" + manip.toString());

        Rectangle max = manip.max();
        if (max != null)
            System.out.println("Le rectangle ayant la plus grande surface est :\n" + max.toString());
        else
            System.out.println("Aucun rectangle à comparer");

        System.out.println("\nDécalage de tous les rectangles de (10,20)");
        manip.decalerRectangles(10, 20);
        System.out.println("Nouvelle liste :\n" + manip.toString());
    }
} 