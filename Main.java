public class Main {
    public static void main(String[] args) {
        User user1 = new User("U001", "Lintang", "085877713117", 100000);
        User user2 = new User("U002", "Ibob", "0870000000", 50000);

        // Uji Top Up Valid & Invalid
        user1.topUp(25000); // Berhasil
        user1.topUp(-5000); // Gagal: Validasi negatif jalan

        // Uji Pay Valid & Invalid
        user1.pay(30000);   // Berhasil
        user2.pay(120000);  // Gagal: Saldo kurang

        // Tampilkan Riwayat Transaksi
        user1.showTransactionHistory();
        user2.showTransactionHistory();

        user1.showBalance();
        user2.showBalance();
    }
}