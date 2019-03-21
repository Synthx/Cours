import java.lang.*;
import java.util.*;

class ManipulateurRectangle {

    public Scanner scanner = new Scanner(System.in);
    public Rectangle[] tabRect;

    public Rectangle creerRectangle() {
        System.out.println("Entrer les coordonnées de l'origine puis du coin :");
    
        double x1 = this.scanner.nextDouble();
        double y1 = this.scanner.nextDouble();
        double x2 = this.scanner.nextDouble();
        double y2 = this.scanner.nextDouble();

        return new Rectangle(x1, y1, x2, y2);
    }

    public void creerTableauRectangle() {
        System.out.println("Entrer le nombre de rectangles que vous souhaitez créer :");
        int n = this.scanner.nextInt();

        this.tabRect = new Rectangle[n];

        for (int i=0; i<n; i++)
            this.tabRect[i] = creerRectangle();
    }

    public Rectangle max() {
        Rectangle max = null;

        for (Rectangle rec : this.tabRect) {
            if (max == null || rec.surface() > max.surface())
                max = rec;
        }

        return max;
    }

    public void decalerRectangles(double x, double y) {
        for (Rectangle rec : this.tabRect) {
            rec.decalerRectangle(x, y);
        }
    }

    public String toString() {
        String res = "[\n";

        for (Rectangle rec : this.tabRect)
            res += "\t" + rec.toString() + "\n";

        return res + "]\n";
    }
}