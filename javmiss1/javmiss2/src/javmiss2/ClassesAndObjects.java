package javmiss2;

//Define a class
class Person {
 // Fields (attributes)
 String name;
 int age;
 String occupation;
 
 // Constructor
 public Person(String name, int age, String occupation) {
     this.name = name;
     this.age = age;
     this.occupation = occupation;
 }
 
 // Methods
 public void introduce() {
     System.out.println("Hi! I'm " + name + ", I'm " + age + 
                        " years old and I work as a " + occupation + ".");
 }
 
 public void haveBirthday() {
     age++;
     System.out.println("Happy birthday! " + name + " is now " + age + ".");
 }
}

public class ClassesAndObjects {
 public static void main(String[] args) {
     // Create objects
     Person person1 = new Person("Alice", 25, "Teacher");
     Person person2 = new Person("Bob", 30, "Engineer");
     
     // Use objects
     person1.introduce();
     person2.introduce();
     
     person1.haveBirthday();
     person2.haveBirthday();
 }
}
