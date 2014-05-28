import mpp.DataMapper;
import obj.Car;
import obj.User;

/**
 * Created by tish on 27.04.2014.
 */
public class Main {
    public static void main(String[] args) {

        DataMapper dm = new DataMapper();

        User nu = new User("Kolia", "Loboda", 22, 5000);
        User nu1 = new User("Clark", "Kent", 24, 3750);
        User nu2 = new User("Piter", "Pan", 30, 8750);
        User nu3 = new User("April", "O'Neel", 25, 5000);

        Car car0 = new Car("VW", "Golf", 22000);
        Car car1 = new Car("Mercedes", "Vito", 47000);
        Car car2 = new Car("Ford", "Fiesta", 33000);

//        dm.save(nu);
//        dm.save(nu1);
//        dm.save(nu2);
//        dm.save(nu3);

//        dm.save(car0);
//        dm.save(car1);
//        dm.save(car2);

//        User user = (User) dm.load(1, User.class);
//        System.out.println(user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getAge()
//                + " " + user.getSalary());
//
//        System.out.println();
//        List<Object> users = dm.loadAll(User.class);
//        for (int i = 0; i < users.size(); i++) {
//            User u = (User) users.get(i);
//            System.out.println(u.getId() + " " + u.getFirstName() + " " + u.getLastName() + " " + u.getAge()
//                    + " " + u.getSalary());
//        }

//        Car car = (Car) dm.load(1, Car.class);
//        System.out.println(car.getId() + " " + car.getMark() + " " + car.getModel() + " " + car.getPrice());
//        car.setModel("Polo");
//        car.setPrice(18000);
//        dm.update(car);

//        dm.delete(4, Car.class);

//        String f = o.getClass().getDeclaredField(fieldsName.get(i)).getAnnotation(Column.class).name();


    }
}
