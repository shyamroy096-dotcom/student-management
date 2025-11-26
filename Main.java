import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO students (name, age) VALUES (?, ?)"
            );
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.executeUpdate();

            System.out.println("Student added!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void viewStudents() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            System.out.println("\nID  |  Name  | Age");
            System.out.println("---------------------");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "  |  " +
                                   rs.getString("name") + "  |  " +
                                   rs.getInt("age"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void updateStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Student ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New Name: ");
            String name = sc.nextLine();

            System.out.print("New Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE students SET name=?, age=? WHERE id=?"
            );
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);
            ps.executeUpdate();

            System.out.println("Student Updated!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void deleteStudent(Scanner sc) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Student ID to delete: ");
            int id = sc.nextInt();

            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM students WHERE id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Student Deleted!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
