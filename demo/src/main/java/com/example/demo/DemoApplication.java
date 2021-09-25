package com.example.demo;

import com.example.demo.book_package.Book;
import com.example.demo.book_package.BookRepository;
import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.enrolment_package.Enrolment;
import com.example.demo.enrolment_package.EnrolmentRepository;
import com.example.demo.student_package.Student;
import com.example.demo.student_package.StudentRepository;
import com.example.demo.student_card_package.StudentCard;
import com.example.demo.student_card_package.StudentCardRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										StudentCardRepository studentCardRepository,
										BookRepository bookRepository,
										CourseRepository courseRepository,
										EnrolmentRepository enrolmentRepository
	){
		return args -> {
//			// Generate only 1 student
//			Student maria = new Student(
//					"Maria Jones",
//					"maria.jones@gmail.com"
//			);
//			studentRepository.save(maria);

			// following code lines must be executed in sequence

//			// 1. Populate data Students
//			generateFakeStudentsOnly(studentRepository);

//			// 2. Populate data Students, StudentCards
//			generateFakeStudentsOnly(studentRepository);
//			generateFakeStudentCardsOnly(studentCardRepository, studentRepository);

//			// 3. Populate data Students, StudentCards, Books
//			generateFakeStudentsOnly(studentRepository);
//			generateFakeStudentCardsOnly(studentCardRepository, studentRepository);
//			generateFakeBooksOnly(studentRepository, bookRepository);

//			// 4. Populate data Students, StudentCards, Books, Courses, Enrolment
//			generateFakeStudentsWithCourses(studentRepository, courseRepository);
//			generateFakeStudentCardsOnly(studentCardRepository, studentRepository);
//			generateFakeBooksOnly(studentRepository, bookRepository);

//			// 5. Populate data Students and StudentCards together
//			generateFakeStudentsWithIdCards(studentRepository, studentCardRepository);

			// 6. Populate data Students, StudentCards, Books
			generateFakeStudentsOnly(studentRepository);
			generateFakeStudentCardsOnly(studentCardRepository, studentRepository);
			generateFakeBooksOnly(studentRepository, bookRepository);
			generateFakeCoursesOnly(courseRepository);
			generateRandomEnrolments(studentRepository, courseRepository, enrolmentRepository);

		};
	}

	private void generateFakeStudentsOnly(StudentRepository studentRepository){
		Faker faker = new Faker();

		for (int i = 0; i < 20; i++) {
			String first_name = faker.name().firstName();
			String last_name = faker.name().lastName();
			String name = first_name + last_name;
			String email = first_name.toLowerCase() + "." + last_name.toLowerCase() + "@gmail.com";

			Student s = new Student(name, email);

			studentRepository.save(s);
		}
	}

	private void generateFakeStudentsWithCourses(StudentRepository studentRepository,
												 CourseRepository courseRepository){
		Faker faker = new Faker();
		Random random = new Random();

		for (int i = 0; i < 20; i++) {
			String first_name = faker.name().firstName();
			String last_name = faker.name().lastName();
			String name = first_name + last_name;
			String email = first_name.toLowerCase() + "." + last_name.toLowerCase() + "@gmail.com";

			Student s = new Student(name, email);
			String department_name = "";
			int selectDepartmentAtRandom = random.nextInt(3); // will give either 0 or 1 or 2
			if(selectDepartmentAtRandom == 0) {
				department_name = "IS";
			} else if (selectDepartmentAtRandom == 1) {
				department_name = "CS";
			} else if (selectDepartmentAtRandom == 2) {
				department_name = "CSYE";
			}

			Course c = new Course(faker.book().title(), department_name);
//			s.enrolToCourse(c);

			studentRepository.save(s);
		}
	}
	private void generateFakeStudentCardsOnly(StudentCardRepository studentCardRepository,
											  StudentRepository studentRepository
	){
		List<Student> list_of_students = studentRepository.findAll();
		Faker faker = new Faker();

		for (Student s: list_of_students) {
			Integer card_number = Integer.parseInt(faker.number().digits(8));
			StudentCard sc = new StudentCard(card_number, s);
			studentCardRepository.save(sc);
		}
	}

	private void generateFakeStudentsWithIdCards(StudentRepository studentRepository,
												 StudentCardRepository studentCardRepository
	){
		Faker faker = new Faker();

		for (int i = 0; i < 20; i++) {
			String first_name = faker.name().firstName();
			String last_name = faker.name().lastName();
			String name = first_name + last_name;
			String email = first_name + "." + last_name + "@gmail.com";

			Student s = new Student(name, email);

			StudentCard sc = new StudentCard(
					Integer.parseInt(faker.number().digits(9)),
					s
			);

			studentCardRepository.save(sc);
		}
	}

	private void generateFakeBooksOnly(StudentRepository studentRepository,
									   BookRepository bookRepository){
		Faker faker = new Faker();
		List<Student> list_of_students = studentRepository.findAll();
		Random random = new Random();

		for(Student s:list_of_students){
			int number_of_books = random.nextInt(5);

			for (int i = 0; i < number_of_books; i++) {
				String book_name = faker.book().title();
				Date created_at_date = getRandomDate();

				Book b = new Book(book_name, created_at_date, s);
				bookRepository.save(b);
			}
		}
	}

	private Date getRandomDate(){
		long aDay = TimeUnit.DAYS.toMillis(1);
		long now = new Date().getTime();
		//hundredYearsAgo
		Date startInclusive = new Date(now - aDay * 365 * 100);
		//tenDaysAgo
		Date endExclusive = new Date(now - aDay * 10);

		long startMillis = startInclusive.getTime();
		long endMillis = endExclusive.getTime();
		long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

		return new Date(randomMillisSinceEpoch);
	}

	private void generateFakeCoursesOnly(CourseRepository courseRepository){
		Faker faker = new Faker();
		Random random = new Random();

		for(int i=0; i<10; i++){
			String course_name = faker.book().title(); // consider that a book is a course
			String department_name = "";

			int selectDepartmentAtRandom = random.nextInt(3); // will give either 0 or 1 or 2
			if(selectDepartmentAtRandom == 0) {
				department_name = "IS";
			} else if (selectDepartmentAtRandom == 1) {
				department_name = "CS";
			} else if (selectDepartmentAtRandom == 2) {
				department_name = "CSYE";
			}

			Course c = new Course(course_name, department_name);
			courseRepository.save(c);
		}
	}

	public void generateRandomEnrolments(StudentRepository studentRepository,
										 CourseRepository courseRepository,
										 EnrolmentRepository enrolmentRepository){
		int number_of_students = studentRepository.findAll().size();
		int number_of_courses = courseRepository.findAll().size();
		Random random = new Random();

		for (int i = 0; i < 30; i++) {
			int student_number = random.nextInt(number_of_students);
			int course_number = random.nextInt(number_of_courses);

			Enrolment enrolment = new Enrolment(
					studentRepository.findAll().get(student_number),
					courseRepository.findAll().get(course_number)
			);
			studentRepository.findAll().get(student_number).addEnrolment(enrolment);
			courseRepository.findAll().get(course_number).addEnrolment(enrolment);
			enrolmentRepository.save(enrolment);
		}
	}

}
