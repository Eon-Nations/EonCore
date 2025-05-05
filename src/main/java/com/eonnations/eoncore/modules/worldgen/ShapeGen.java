package com.eonnations.eoncore.modules.worldgen;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapeGen {
    // Inspired by https://stackoverflow.com/questions/8997099/algorithm-to-generate-random-2d-polygon?__cf_chl_f_tk=MrzD2tL.x2fpaMPbI3sfdao6GiUlIrEzkQMT5W70E3k-1746463121-1.0.1.1-pmZbEQbbM9CxEXKphYQyXdb2hdWN_02Hnsbg3K6gX9Y
    public static List<Tuple2<Double, Double>> generatePolygon(Tuple2<Double, Double> center, double avgRadius, double irregularity, double spikiness, int numVertices, Random random) {
        irregularity *= 2 * Math.PI / numVertices;
        spikiness *= avgRadius;

        List<Double> angleSteps = randomAngleSteps(numVertices, irregularity);
        List<Tuple2<Double, Double>> points = new ArrayList<>();

        double angle = random.nextDouble() * 2 * Math.PI;
        for (int i = 0; i < numVertices; i++) {
            double radius = Math.clamp(random.nextGaussian(avgRadius, spikiness), 0.0, 2 * avgRadius);
            double x = center._1 + radius * Math.cos(angle);
            double y = center._2 + radius * Math.sin(angle);
            points.add(Tuple.of(x, y));
            angle += angleSteps.get(i);
        }
        return points;
    }

    private static List<Double> randomAngleSteps(int steps, double irregularity) {
        List<Double> angles = new ArrayList<>();
        double lower = (2 * Math.PI / steps) - irregularity;
        double upper = (2 * Math.PI / steps) + irregularity;
        double cumsum = 0;

        for (int i = 0; i < steps; i++) {
            double angle = lower + Math.random() * (upper - lower);
            angles.add(angle);
            cumsum += angle;
        }

        double scale = cumsum / (2 * Math.PI);
        angles.replaceAll(aDouble -> aDouble / scale);
        return angles;
    }

    public static boolean pointInPolygon(double x, double z, io.vavr.collection.List<Tuple2<Double, Double>> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon.get(i)._1;
            double zi = polygon.get(i)._2;
            double xj = polygon.get(j)._1;
            double zj = polygon.get(j)._2;

            boolean intersect = ((zi > z) != (zj > z)) &&
                    (x < (xj - xi) * (z - zi) / (zj - zi + 0.00001) + xi);
            if (intersect)
                inside = !inside;
        }
        return inside;
    }

}
