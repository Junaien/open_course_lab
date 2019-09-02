package math_thinking;

public class Solution812 {

    /**
     * Intuition: using shoelace formula(curve not intersect itself) clock or counter clock wise
     *            Polygon defined by different (x,y) coordinate, the area it covers is
     *            put all coordinates in a shoelace
     *            x0   y0
     *            x1   y1
     *            x2   y2
     *            ...
     *            xn   yn
     *            x0   y0
     *
     *            and do the determinant for every 2x2 matrix
     *            add them up and divide it by 2
     *
     * @param points
     * @return
     */
    public double largestTriangleArea(int[][] points) {
        double rt = -1.0;
        for(int i = 0; i < points.length -2; i++)
            for(int j = i+1; j < points.length -1; j++)
                for(int k = j+1; k < points.length;k++){
                    rt = Math.max(rt,calculateArea(points,i,j,k));
                }
        return rt;
    }
    private double calculateArea(int[][]points, int i, int j, int k){

        return Math.abs(0.5 * (points[i][0] * points[j][1] - points[j][0] * points[i][1] +
                points[j][0] * points[k][1] - points[k][0] * points[j][1] +
                points[k][0] * points[i][1] - points[i][0] * points[k][1]));
    }
}
