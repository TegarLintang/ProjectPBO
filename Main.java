import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user1 = new User("U001", "Lintang", "085877713117", 100000);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=== MENU E-WALLET: " + user1.getName().toUpperCase() + " ===");
            System.out.println("1. Tampilkan Saldo");
            System.out.println("2. Top Up");
            System.out.println("3. Bayar");
            System.out.println("4. Riwayat Transaksi");
            System.out.println("5. Edit Profil (Ganti Nama/No HP)");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu (1-6): ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1:
                        user1.showBalance();
                        break;
                    case 2:
                        System.out.print("Masukkan nominal Top Up: Rp");
                        double nominalTopUp = scanner.nextDouble();
                        user1.topUp(nominalTopUp);
                        break;
                    case 3:
                        System.out.print("Masukkan nominal Tagihan: Rp");
                        double nominalBayar = scanner.nextDouble();
                        user1.pay(nominalBayar);
                        break;
                    case 4:
                        user1.showTransactionHistory();
                        break;
                    case 5:
                        System.out.println("\n--- EDIT PROFIL ---");
                        System.out.println("1. Ubah Nama");
                        System.out.println("2. Ubah No HP");
                        System.out.print("Pilih data yang ingin diubah (1/2): ");
                        
                        int opsiEdit = scanner.nextInt();
                        scanner.nextLine(); 
                        
                        if (opsiEdit == 1) {
                            System.out.print("Masukkan Nama Baru: ");
                            String namaBaru = scanner.nextLine();
                            user1.setName(namaBaru); 
                            System.out.println("Sukses! Nama diubah menjadi: " + user1.getName());
                        } else if (opsiEdit == 2) {
                            System.out.print("Masukkan No HP Baru: ");
                            String hpBaru = scanner.nextLine();
                            user1.setPhone(hpBaru); 
                            System.out.println("Sukses! No HP diubah menjadi: " + user1.getPhone());
                        } else {
                            System.out.println("Peringatan: Pilihan tidak valid! Anda harus mengetik angka 1 atau 2.");
                            System.out.println("Gagal mengedit profil. Membatalkan aksi...");
                        }
                        break;
                    case 6:
                        System.out.println("Terima kasih telah menggunakan E-Wallet!");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Peringatan: Pilihan tidak ada. Silakan masukkan angka 1 sampai 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input tidak valid! Anda harus memasukkan angka.");
                scanner.nextLine(); 
            }
        }
        scanner.close();
    }
}