//package pizza.pizzaorderapp.frontend;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//  CourseRepository courseRepository;
//
//    @Autowired
//    StudentRepository studentRepository;
//
//
//
//
//    @Override
//    public void run(String... strings) throws Exception{
//
//        Course course;
//        course=new Course("Java101","Victor","Introduction to Java");
//        Set<Course> cour = new HashSet<>();
//        cour.add(course);
//        courseRepository.save(course);
//
//        Student student=new Student("Petros","Melake","1984","https://res.cloudinary.com/dyarncgbq/image/upload/v1575762700/bkepc7ttnxo3mogclan1.jpg",cour);
//        studentRepository.save(student);
//
//        course=new Course("Python101","Beth","Introduction to python");
//        cour.add(course);
//        courseRepository.save(course);
//        student=new Student("Helen","Tomas","1988","https://res.cloudinary.com/dyarncgbq/image/upload/v1575667165/zsobhf4482zzja6vkrfm.jpg",cour);
//        studentRepository.save(student);
//    }
//
//}
//
