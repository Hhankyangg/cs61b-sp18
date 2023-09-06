public class Planet {
    static final double G = 6.67e-11;
    double xxPos, yyPos, xxVel, yyVel, mass;
    String imgFileName;

    public Planet(double xP, double yP, double xV,double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        double r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return r;
    }

    public double calcForceExertedBy(Planet b) {
        double r = this.calcDistance(b);
        double force = G * this.mass * b.mass / Math.pow(r, 2);
        return force;
    }

    public double calcForceExertedByX(Planet b) {
        double force = this.calcForceExertedBy(b);
        double r = this.calcDistance(b);
        double dx = b.xxPos - this.xxPos;
        return force * dx / r;
    }

    public double calcForceExertedByY(Planet b) {
        double force = this.calcForceExertedBy(b);
        double r = this.calcDistance(b);
        double dy = b.yyPos - this.yyPos;
        return force * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double net_force_x = 0;
        for (Planet p : planets) {
            if (!this.equals(p)) {
                net_force_x += calcForceExertedByX(p);
            }
        }
        return net_force_x;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double net_force_y = 0;
        for (Planet p : planets) {
            if (!this.equals(p)) {
                net_force_y += calcForceExertedByY(p);
            }
        }
        return net_force_y;
    }

    public void update(double dt, double fx, double fy) {
        double a_x = fx / this.mass;
        double a_y = fy / this.mass;
        this.xxVel += a_x * dt;
        this.yyVel += a_y * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
