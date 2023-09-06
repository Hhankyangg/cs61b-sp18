public class NBody {
    public static double readRadius(String filename) {
        In the_file = new In(filename);
        int planets_num = the_file.readInt();
        double radius = the_file.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In the_file = new In(filename);
        int planets_num = the_file.readInt();
        Planet[] planets = new Planet[planets_num];
        double radius = the_file.readDouble();
        
        for (int index = 0; index < planets_num; index++) {
            planets[index] = new Planet(the_file.readDouble(), the_file.readDouble(), the_file.readDouble(), 
                                        the_file.readDouble(), the_file.readDouble(), the_file.readString());
        }

        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double universe_radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        double t_origin = 0;
        int planets_num = planets.length;

        StdDraw.enableDoubleBuffering();
        
        while (t_origin <= T) {
            StdDraw.clear();
            StdDraw.setScale(-universe_radius, universe_radius);
            StdDraw.picture(0, 0, "images/starfield.jpg", 2*universe_radius, 2*universe_radius);
            
            // Draw every planets
            double[] xForces = new double[planets_num];
            double[] yForces = new double[planets_num];
            
            for (int index = 0; index < planets_num; index ++) {
                xForces[index] = planets[index].calcNetForceExertedByX(planets);
                yForces[index] = planets[index].calcNetForceExertedByY(planets);
            }
            
            for (int index = 0; index < planets_num; index ++) {
                planets[index].update(dt, xForces[index], yForces[index]);
            }
            
            for (Planet p : planets) {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            t_origin += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universe_radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }

    }
}
