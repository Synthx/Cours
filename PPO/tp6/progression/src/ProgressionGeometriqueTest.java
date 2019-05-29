import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProgressionGeometriqueTest {

    private ProgressionGeometrique p;

    @Before
    public void initData() {
        this.p = new ProgressionGeometrique(10, 2);
    }

    @Test
    public void getTerme() {
        Assert.assertEquals(10, this.p.getTerme(), 0);
    }

    @Test
    public void constructor() {
        Assert.assertEquals(1, this.p.termes.size());
        Assert.assertEquals(10, this.p.getTerme(), 0);
        Assert.assertEquals(2, this.p.raison, 0);
    }

    @Test
    public void next() {
        Assert.assertEquals(1, this.p.termes.size());
        Assert.assertEquals(10, this.p.getTerme(), 0);
        this.p.next();
        Assert.assertEquals(2, this.p.termes.size());
        Assert.assertEquals(20, this.p.getTerme(), 0);
    }

    @Test
    public void next1() {
        Assert.assertEquals(1, this.p.termes.size());
        Assert.assertEquals(10, this.p.getTerme(), 0);
        this.p.next(3);
        Assert.assertEquals(4, this.p.termes.size());
        Assert.assertEquals(80, this.p.getTerme(), 0);
    }
}
