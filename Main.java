import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== LOGIN E-WALLET ===");
        System.out.println("Pilih tipe akun Anda untuk masuk:");
        System.out.println("1. Regular User (Limit Rp2 Juta, Cashback 1%)");
        System.out.println("2. Premium User (Limit Rp10 Juta, Cashback 5%)");
        System.out.println("3. Merchant User (Limit Rp50 Juta, Bisa terima pembayaran)");
        System.out.print("Pilih (1-3): ");
        
        int tipeAkun = 1; 
        try {
            tipeAkun = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input salah! Otomatis login sebagai Regular User.");
            scanner.nextLine();
        }

        // Inheritance & Polymorphic Reference di Main
        User activeUser;
        if (tipeAkun == 2) {
            activeUser = new PremiumUser("U001", "User Premium", "085877713117", 100000);
            System.out.println("\n[!] Login sebagai Premium User. Limit: Rp" + ((PremiumUser)activeUser).getTransactionLimit());
        } else if (tipeAkun == 3) {
            activeUser = new MerchantUser("U001", "User Merchant", "085877713117", 100000);
            System.out.println("\n[!] Login sebagai Merchant User. Limit: Rp" + ((MerchantUser)activeUser).getTransactionLimit());
        } else {
            activeUser = new RegularUser("U001", "User Regular", "085877713117", 100000);
            System.out.println("\n[!] Login sebagai Regular User. Limit: Rp" + ((RegularUser)activeUser).getTransactionLimit());
        }

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=== MENU E-WALLET: " + activeUser.getName().toUpperCase() + " ===");
            System.out.println("1. Tampilkan Saldo");
            System.out.println("2. Top Up");
            System.out.println("3. Bayar Tagihan");
            System.out.println("4. Transfer Dana");
            System.out.println("5. Riwayat Transaksi");
            
            if (activeUser instanceof MerchantUser) {
                System.out.println("6. Terima Pembayaran (Khusus Merchant)");
                System.out.println("7. Keluar");
            } else {
                System.out.println("6. Keluar");
            }
            
            System.out.print("Pilih menu: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1:
                        activeUser.showBalance();
                        break;
                    case 2:
                        System.out.print("Masukkan nominal Top Up: Rp");
                        activeUser.topUp(scanner.nextDouble());
                        break;
                    case 3:
                        System.out.print("Masukkan nominal Bayar: Rp");
                        activeUser.pay(scanner.nextDouble()); 
                        break;
                    case 4:
                        System.out.println("\n--- PILIH METODE TRANSFER ---");
                        System.out.println("1. Bank Transfer");
                        System.out.println("2. QR Payment");
                        System.out.println("3. Wallet Transfer");
                        System.out.print("Pilih metode (1-3): ");
                        int metode = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Masukkan nominal Transfer: Rp");
                        double nomTransfer = scanner.nextDouble();
                        scanner.nextLine();
                        
                        Payment transaksiTransfer = null;
                        if (metode == 1) {
                            System.out.print("Masukkan Nama Bank Tujuan: ");
                            transaksiTransfer = new BankTransfer(nomTransfer, scanner.nextLine());
                        } else if (metode == 2) {
                            System.out.print("Masukkan ID QR Code: ");
                            transaksiTransfer = new QRPayment(nomTransfer, scanner.nextLine());
                        } else if (metode == 3) {
                            System.out.print("Masukkan No HP Tujuan: ");
                            transaksiTransfer = new WalletTransfer(nomTransfer, scanner.nextLine());
                        } else {
                            System.out.println("Metode transfer tidak valid!");
                        }

                        if (transaksiTransfer != null) {
                            System.out.println("Memproses transfer menggunakan " + transaksiTransfer.getClass().getSimpleName() + "...");
                            activeUser.pay(transaksiTransfer.getAmount());
                        }
                        break;
                    case 5:
                        activeUser.showTransactionHistory();
                        break;
                    case 6:
                        if (activeUser instanceof MerchantUser) {
                            System.out.print("Masukkan nominal Pembayaran Masuk: Rp");
                            ((MerchantUser) activeUser).receivePayment(scanner.nextDouble());
                        } else {
                            System.out.println("Terima kasih telah menggunakan E-Wallet!");
                            isRunning = false;
                        }
                        break;
                    case 7:
                        if (activeUser instanceof MerchantUser) {
                            System.out.println("Terima kasih telah menggunakan E-Wallet!");
                            isRunning = false;
                        } else {
                            System.out.println("Pilihan tidak ada.");
                        }
                        break;
                    default:
                        System.out.println("Pilihan tidak ada.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input tidak valid! Anda harus memasukkan angka.");
                scanner.nextLine(); 
            }
        }
        scanner.close();
    }
}