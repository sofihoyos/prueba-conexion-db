import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );

            //Menú

            int opcion;
            do {
                System.out.println("\nSelecciona lo que quieres hacer: ");
                System.out.println("1. Insertar un nuevo empleado: ");
                System.out.println("2. Consultar un empleado: ");
                System.out.println("3. Actualizar un empleado: ");
                System.out.println("4. Eliminar un empleado: ");
                System.out.println("5. Salir ");
                System.out.println("Opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion){
                    case 1:
                        System.out.println("Ingresa el nombre del nuevo empleado: ");
                        String nombre = scanner.nextLine();
                        System.out.println("Ingresa el cargo: ");
                        String cargo = scanner.nextLine();
                        System.out.println("Ingresa el salario: ");
                        double salario = scanner.nextDouble();
                        insertarEmpleado(myConn, nombre, cargo, salario);
                        break;

                    case 2:
                        consultarEmpleados(myConn);
                        break;

                    case 3:
                        System.out.println("Ingresa el ID de empleado que quieres actualizar: ");
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Ingresa el nuevo nombre del empleado: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.println("Ingresa el nuevo cargo: ");
                        String nuevoCargo = scanner.nextLine();
                        System.out.println("Ingresa el nuevo salario: ");
                        double nuevoSalario = scanner.nextDouble();
                        actualizarEmpleado(myConn, idActualizar, nuevoNombre, nuevoCargo, nuevoSalario);
                        break;

                    case 4:
                        System.out.println("Ingresa el ID del empleado que quieres eliminar: ");
                        int idEliminar = scanner.nextInt();
                        eliminarEmpleado(myConn, idEliminar);
                        break;

                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta de nuevo...");
                }
            }while (opcion !=5);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }



    }
    // Método para insertar un empleado
    private static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "INSERT INTO empleados (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM empleados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Cargo: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(Connection conexion, int id, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}