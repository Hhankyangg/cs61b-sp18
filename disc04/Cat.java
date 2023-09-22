public class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
        this.noise = "Meow!";
    }

    public void greet() {
        System.out.println("Cat" + name + " says: " + makeNoise() );
    }

    public static void main(String[] args) {
        Cat c = new Cat("Hank", 19);
        Animal a = c;
        c.greet();
        a.greet();
    }
}
