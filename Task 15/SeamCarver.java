import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
        private Picture picture;
        private int[] edgeTo;
        private double[] distanceTo;
        private double[] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
        int w = picture.width();
        int h = picture.height();
        this.energy = new double[w*h];
        this.distanceTo = new double[w*h];
        this.edgeTo = new int[w*h];
    }

    // current picture
    public Picture picture(){
        return new Picture(picture);
    }

    // width of current picture
    public int width(){
        return picture.width();
    }

    // height of current picture
    public int height(){
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if(x + 1> picture.width() || y + 1 > picture.height() || y < 0 || x < 0)
            throw new IllegalArgumentException();
        int ax = (x+1)%width();
        int bx = (x-1);
        if(y+1 >= height()) ax = 0;
        if(x-1 < 0) bx = width()-1;

        int redx = picture.get(ax,y).getRed() - picture.get(bx,y).getRed();
        int bluex = picture.get(ax,y).getBlue() - picture.get(bx,y).getBlue();
        int greenx = picture.get(ax,y).getGreen() - picture.get(bx,y).getGreen();


        int ay = (y+1);
        int by = (y-1);
        if(y+1 >= height()) ay = 0;
        if(y-1 < 0) by = height()-1;
        int redy = picture.get(x,ay).getRed() - picture.get(x,by).getRed();
        int bluey = picture.get(x,ay).getBlue() - picture.get(x,by).getBlue();
        int greeny = picture.get(x,ay).getGreen() - picture.get(x,by).getGreen();

        int parteA = redx*redx + bluex*bluex + greenx*greenx;
        int parteB = redy*redy + bluey*bluey + greeny*greeny;

        return Math.sqrt(parteA + parteB);
    }




    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        Picture transposta = new Picture(picture.height(), picture.width());
        for(int x = 0; x<transposta.width(); x++){
            for(int y = 0; y<transposta.height(); y++) {
                transposta.set(x, y, picture.get(y, x));
            }
        }
        SeamCarver transpositor = new SeamCarver(transposta);
        return transpositor.findVerticalSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        int atual;
        if(distanceTo == null){
            int w = picture.width();
            int h = picture.height();
            this.energy = new double[w*h];
            this.distanceTo = new double[w*h];
            this.edgeTo = new int[w*h];
        }
        double infinito = Double.POSITIVE_INFINITY;
        for(int x = 0; x<width(); x++){
            for(int y = 0; y<height(); y++){
                atual = x + y*width();
                if(y==0) distanceTo[atual] = 0;
                else distanceTo[atual] = infinito;
                edgeTo[atual] = -1;
                energy[atual] = energy(x,y);
            }
        }
        for(int y = 0; y<height()-1; y++){
            for(int x = 0; x<width(); x++){
                atual = x + y*width();
                if(x  >= 1) atualiza(atual, x-1, y+1);
                if(x< width() - 1) atualiza(atual, x+1, y+1);
                atualiza(atual, x,y+1);
            }

        }
        double ans = infinito;
        int b = 0;
        for(int x = 0; x<width(); x++){
            int c = x + (height()-1)*width();
            if(distanceTo[c] < ans){
                ans = distanceTo[c];
                b = c;
            }

        }
        int[] retorno = new int[height()];
        while(b>=0){
            int t = (int)b/width();
            int k = (int)b%width();
            retorno[t] = k;
            b = edgeTo[b];
        }
        return retorno;

    }
    private void atualiza (int atual,int x, int y){
        int b = x+y*width();
        if(distanceTo[b] > distanceTo[atual] + energy[b]){
            distanceTo[b] = distanceTo[atual] + energy[b];
            edgeTo[b] = atual;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){

        if(seam == null) throw new IllegalArgumentException();
        if(picture.height() == 1 || picture.width() == 1) throw new IllegalArgumentException();
        if (seam.length!= width() || height() <=1)
            throw new IllegalArgumentException();
        for (int id: seam)
            if (id < 0 || id > height()-1)
                throw new IllegalArgumentException();
        for (int i =0; i<seam.length - 1;i++){
            if (seam[i+1] - seam[i] > 1 || seam[i+1] - seam[i] < -1)
                throw new IllegalArgumentException();
        }
        Picture nova = new Picture(picture.width(),picture.height() - 1);
        for(int x = 0;x <nova.width();x++){
            for (int y =0; y <nova.height(); y++){
                if (y < seam[x])
                    nova.set(x,y,picture.get(x,y));
                else
                    nova.set(x,y,picture.get(x,y+1));
            }
        }
        picture = nova;
        distanceTo =null;
        edgeTo =null;
        energy = null;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if(seam == null) throw new IllegalArgumentException();
        if (seam.length!=height() || width()<=1)
            throw new IllegalArgumentException();
        if(picture.height() == 1 || picture.width() == 1) throw new IllegalArgumentException();
        for (int id: seam)
            if (id < 0 || id > width()-1)
                throw new IllegalArgumentException();
        for (int i =0; i<seam.length - 1;i++){
            if (seam[i+1] - seam[i] > 1 || seam[i+1] - seam[i] < -1)
                throw new IllegalArgumentException();
        }
        Picture nova = new Picture(picture.width() - 1,picture.height());
        for (int y =0; y <nova.height(); y++){
            for(int x = 0;x <nova.width();x++) {
                if (x < seam[y])
                    nova.set(x,y,picture.get(x,y));
                else
                    nova.set(x,y,picture.get(x+1,y));
            }
        }
        picture = nova;
        distanceTo =null;
        edgeTo =null;
        energy = null;
    }

    //  unit testing (required)
    public static void main(String[] args){

    }

}
