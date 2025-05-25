abstract class Vehicle {
    public abstract void startEngine();
    public void stopEngine() {
        System.out.println("Engine stopped.");
    }
}

interface Electric {
    void chargeBattery();
}

class Car extends Vehicle implements Electric {
    private String model;

    public Car(String model) {
        this.model = model;
    }

    @Override
    public void startEngine() {
        System.out.println("Car " + model + " engine started silently (electric).");
    }

    @Override
    public void chargeBattery() {
        System.out.println("Car " + model + " battery is charging.");
    }

    public void drive() {
        System.out.println("Car " + model + " is driving.");
    }
}

class Motorcycle extends Vehicle {
    private String type;

    public Motorcycle(String type) {
        this.type = type;
    }

    @Override
    public void startEngine() {
        System.out.println("Motorcycle " + type + " engine started with a roar.");
    }

    public void wheelie() {
        System.out.println("Motorcycle " + type + " is doing a wheelie!");
    }
}

public class MainApp {
    public static void main(String[] args) {
        Vehicle myCar = new Car("Tesla Model S");
        Vehicle myMotorcycle = new Motorcycle("Harley-Davidson");

        myCar.startEngine();
        myMotorcycle.startEngine();
        myCar.stopEngine();
        myMotorcycle.stopEngine();

        if (myCar instanceof Car) {
            Car specificCar = (Car) myCar;
            specificCar.chargeBattery();
            specificCar.drive();
        }

        if (myMotorcycle instanceof Motorcycle) {
            Motorcycle specificMotorcycle = (Motorcycle) myMotorcycle;
            specificMotorcycle.wheelie();
        }

        Vehicle[] vehicles = new Vehicle[2];
        vehicles[0] = new Car("BMW iX");
        vehicles[1] = new Motorcycle("Kawasaki Ninja");

        for (Vehicle v : vehicles) {
            v.startEngine();
            if (v instanceof Electric) {
                ((Electric) v).chargeBattery();
            }
        }
    }
}