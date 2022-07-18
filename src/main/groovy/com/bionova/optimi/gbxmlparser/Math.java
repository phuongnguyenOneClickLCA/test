package com.bionova.optimi.gbxmlparser;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

class Math {
  /**
   * Calculate determinant for a 3x3 matrix.
   */
  public static double determinant(double[][] matrix) {
    double result = 0.0;
    result += matrix[0][0] * matrix[1][1] * matrix[2][2];
    result += matrix[0][1] * matrix[1][2] * matrix[2][0];
    result += matrix[0][2] * matrix[1][0] * matrix[2][1];
    result -= matrix[2][0] * matrix[1][1] * matrix[0][2];
    result -= matrix[2][1] * matrix[1][2] * matrix[0][0];
    result -= matrix[2][2] * matrix[1][0] * matrix[0][1];
    return result;
  }

  /**
   * Calculate dot product for two 3-vectors.
   */
  public static double dot(double[] v1, double[] v2) {
    double res = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
    return res;
  }

  /**
   * Calculate cross product for two 3-vectors.
   */
  public static double[] cross(double[] v1, double[] v2) {
    double[] v3 = new double[3];
    v3[0] = v1[1] * v2[2] - v1[2] * v2[1];
    v3[1] = v1[2] * v2[0] - v1[0] * v2[2];
    v3[2] = v1[0] * v2[1] - v1[1] * v2[0];
    return v3;
  }

  /**
   * Calculate normal vector for a plane given by three 3-vectors.
   */
  public static double[] normal(double[] vec1, double[] vec2, double[] vec3) {
    double xaxis = determinant(new double[][] {
      { 1, vec1[1], vec1[2] },
      { 1, vec2[1], vec2[2] },
      { 1, vec3[1], vec3[2] }
    });
    double yaxis = determinant(new double[][] {
      { vec1[0], 1, vec1[2] },
      { vec2[0], 1, vec2[2] },
      { vec3[0], 1, vec3[2] }
    });
    double zaxis = determinant(new double[][] {
      { vec1[0], vec1[1], 1 },
      { vec2[0], vec2[1], 1 },
      { vec3[0], vec3[1], 1 }
    });

    double magnitude = pow((pow(xaxis, 2) + pow(yaxis, 2) + pow(zaxis, 2)), 0.5);

    double[] res = new double[] { xaxis / magnitude, yaxis / magnitude, zaxis / magnitude };
    return res;
  }

  /**
   * Calculate area for a polygon given by a list of 3-vectors.
   * Ported from http://stackoverflow.com/questions/12642256/python-find-area-of-polygon-from-xyz-coordinates
   */
  public static double area(List<double[]> poly) {
    double[] total = new double[] { 0, 0, 0 };

    for (int i = 0; i < poly.size(); i++) {
      double[] prod = cross(poly.get(i), poly.get((i + 1) % poly.size()));
      total[0] += prod[0];
      total[1] += prod[1];
      total[2] += prod[2];
    }

    return abs(dot(total, normal(poly.get(0), poly.get(1), poly.get(2))) / 2);
  }
}
