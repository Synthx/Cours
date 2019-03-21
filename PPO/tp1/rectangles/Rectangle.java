import java.awt.geom.Point2D;

class Rectangle
{
    public Point2D.Double origin;
    public Point2D.Double corner;

    public Rectangle(double x1, double y1, double x2, double y2) {
        this.origin = new Point2D.Double(x1, y1);
        this.corner = new Point2D.Double(x2, y2);
    }

    public double largeur() {
        if (this.corner.getY() < this.origin.getY())
            return this.origin.getY() - this.corner.getY();
        else
            return this.corner.getY() - this.origin.getY();
    }

    public double longueur() {
        if (this.corner.getX() < this.origin.getX())
            return this.origin.getX() - this.corner.getX();
        else
            return this.corner.getX() - this.origin.getX();
    }

    public double surface() {
        return largeur() * longueur();
    }

    public double perimetre() {
        return 2*largeur() + 2*longueur();
    }

    public void decalerRectangle(double x, double y) {
        this.origin.setLocation(this.origin.getX() + x, this.origin.getY() + y);
        this.corner.setLocation(this.corner.getX() + x, this.corner.getY() + y);
    }

    public String toString() {
        return "(<origin>" + this.origin.toString() + " , <corner>" + this.corner.toString() + ")";
    }
}