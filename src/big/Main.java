package big;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        List<Employee> listEmployee = Koneksi.sync(); 				//sinkronisasi ArrayList dengan Koneksi.java
        int pilihan = 0;
        String again = "", back = "";

        do {
            try {
                pilihan = showMenu();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input !");
                continue;
            }

            switch (pilihan) {
                case 1:            //input
                    do {
                        try {
                            Employee newEmployee = addEmployee();                          //var newEmployee hanya untuk pilihan1
                            if (Koneksi.insertEmployee(newEmployee)) {
                                listEmployee.add(newEmployee);
                                System.out.println("== Data Saved ! ==");
                                System.out.println("Do you want to input data again (Y/N)? ");
                                again = scan.next();
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            break;
                        }
                    } while (again.equalsIgnoreCase("y"));
                    showListEmployee(listEmployee);
                    break;

                case 2:        //delete
                    do {
                        try {
                            Employee employee = deleteEmployee(listEmployee); //listEmployee adalah tumpukan employee
                            if (employee != null) {
                                Koneksi.removeEmployee(employee);
                                listEmployee.remove(employee);
                                System.out.println("== Data Removed ==");
                                System.out.println("Do you want to input data again (Y/N)? ");
                                again = scan.next();
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            break;
                        }
                    } while (again.equalsIgnoreCase("y"));
                    showListEmployee(listEmployee);
                    break;

                case 3:            //update
                    do {        //update
                        try {
                            Employee updatedEmployee = Update(listEmployee);
                            if (updatedEmployee != null) {
                                for (Employee karyawan : listEmployee) {
                                    if (updatedEmployee.getNoId() == karyawan.getNoId()) {
                                        listEmployee.set(listEmployee.indexOf(karyawan), updatedEmployee);
                                        Koneksi.updateEmployee(updatedEmployee);
                                        System.out.println("== Data Updated ! ==");
                                        System.out.println("Do you want to input data again (Y/N)? ");
                                        again = scan.next();
                                        break;
                                    }
                                }
                            }
                            System.out.println("Do u want to input data again (Y/N)? ");
                            again = scan.next();
                        } catch (Exception e) {
                            System.out.println("Invalid input! ");
                            break;
                        }
                    } while (again.equalsIgnoreCase("y"));
                    break;

                case 4:            //tampilkan semua employee -> listEmployee
                    showListEmployee(listEmployee);
                    break;

                case 5:
                    System.out.println("== Exit ==");
                    break;

                default:
                    System.out.println("Invalid input!\n");
            }

            if (pilihan != 5) {
                System.out.println("Do you want to back at menu (Y/N)? ");
                back = scan.next();
            }
            if (pilihan == 5) {
                break;
            } //punya if
        } while (back.equalsIgnoreCase("y"));    //punya do
    } //punya main

    private static int showMenu() throws InputMismatchException {
        System.out.println("Menu: ");
        System.out.println("1. Create Data Employee");
        System.out.println("2. Delete Data Employee");
        System.out.println("3. Update Data Employee");
        System.out.println("4. Show List Data Employee");
        System.out.println("5. Exit");
        System.out.print("Input your choose: ");
        return scan.nextInt();
    }

    private static Employee addEmployee() { 						//method Employee punya nama addEmployee()
        System.out.println("----- INPUT DATA KARYAWAN -----\n");
        System.out.print("Nama: ");
        String nama = scan.next();
        System.out.print("No ID Karyawan: ");
        int noId = scan.nextInt();
        System.out.print("No hp: ");
        int noHp = scan.nextInt();
        System.out.print("Address: ");
        String alamat = scan.next();
        System.out.print("Email: ");
        String email = scan.next();
        return new Employee(nama, noId, noHp, alamat, email);       //me return value dari new Employee yaitu nama, noId dll.. persis dengan scan
    }

    private static Employee deleteEmployee(List<Employee> data) throws SQLException {    //methhod Employee punya nama deleteEmployee
        if (data.isEmpty()) {                                                                //cek apakah DATA <Employee> kosong
            System.out.println("Empty! Input data employee first");                        //kalo kosong cetak EMPTY
        } else {                                                                            //kalo ga kosong
            System.out.print("=> Enter employee ID number: ");                                //masukkan ID number
            int noId = scan.nextInt();                                                        // scan inputan user
            for (Employee karyawan : data) {                                                //setiap karyawan yang ada pada array data maka lakukan looping
                if (karyawan.getNoId() == noId) {                                            // kalo noId cocok dengan getNoId user
                    return karyawan;                                                        //return value karyawan
                }
            }
            System.out.println("ID number of employee not detected");                        //kalo noId nya ga cocok sm inputan user, cetak not detected
        }
        return null;
    }

    private static Employee Update(List<Employee> data) throws InputMismatchException {
        Employee tempEmployee = null;            //			tempEmployee = ktm A //tempEmployee = instance = object
        Employee newEmployee = null;            //newEmployee untuk menimpa Employee lama --- newEmployee = ktm B
        if (data.isEmpty()) {
            System.out.println("Nothing update. Data is empty!");
            System.out.println("Input data please");
        } else {
            System.out.println("Enter employee ID number: ");
            int noId = scan.nextInt();
            for (Employee karyawan : data) {
                if (karyawan.getNoId() == noId) {
                    tempEmployee = karyawan;
                    break;
                }
            }
            if (tempEmployee != null) {
                try {
                    System.out.println("----- UPDATE DATA KARYAWAN -----\n");
                    System.out.print("Nama: ");
                    String nama = scan.next();
                    System.out.print("No hp: ");
                    int noHp = scan.nextInt();
                    System.out.print("Address: ");
                    String alamat = scan.next();
                    System.out.print("Email: ");
                    String email = scan.next();

                    newEmployee = new Employee();                    //newEmployee var untuk menampung inputan baru
                    newEmployee.setNoId(tempEmployee.getNoId());
                    newEmployee.setNama(nama);
                    newEmployee.setNoHp(noHp);
                    newEmployee.setAlamat(alamat);
                    newEmployee.setEmail(email);

                    return newEmployee;                                //nilai dikembalikan ke newEmployee
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ID number not detected");
        }
        return newEmployee;
    }

    private static void showListEmployee(List<Employee> data) {
        if (data.isEmpty()) {
            System.out.println("EMPTY here. Please check at localhost");
        } else {
            for (Employee karyawan : data) { //untuk setiap karyawan dalam data maka lakukan perulangan
                System.out.println("\n***** LIST DATA EMPLOYEE *****");
                System.out.println("Name: " + karyawan.getNama());
                System.out.println("ID Employee: " + karyawan.getNoId());
                System.out.println("Address: " + karyawan.getAlamat());
                System.out.println("Phone number: " + karyawan.getNoHp());
                System.out.println("Email: " + karyawan.getEmail());
                System.out.println("======================================");
            }
        }
    }
}