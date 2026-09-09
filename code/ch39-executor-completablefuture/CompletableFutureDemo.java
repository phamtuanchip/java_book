import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("-- CompletableFuture: xay dung PIPELINE bat dong bo --");

        CompletableFuture<Integer> pipeline = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("Buoc 1 (async): lay du lieu tho...");
                gia(300);
                return 10;
            })
            .thenApply(giaTri -> { // BIEN DOI ket qua, giong map() cua Stream (Chuong 31)
                System.out.println("Buoc 2: nhan doi gia tri");
                return giaTri * 2;
            })
            .thenApply(giaTri -> {
                System.out.println("Buoc 3: cong them 5");
                return giaTri + 5;
            });

        System.out.println("Pipeline da duoc THIET LAP, main van chay tiep khong bi chan...");

        int ketQua = pipeline.get(); // CHO ket qua CUOI CUNG cua toan bo pipeline
        System.out.println("Ket qua cuoi cung: " + ketQua); // (10 * 2) + 5 = 25

        System.out.println("-- thenCombine(): ket hop KET QUA cua HAI CompletableFuture doc lap --");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            gia(200);
            return 3;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            gia(300);
            return 4;
        });

        CompletableFuture<Integer> tongHop = future1.thenCombine(future2, (a, b) -> a + b);
        System.out.println("Tong hop ca hai (chay SONG SONG, khong tuan tu): " + tongHop.get());

        System.out.println("-- exceptionally(): xu ly loi trong pipeline bat dong bo --");
        CompletableFuture<Integer> coLoi = CompletableFuture
            .supplyAsync(() -> {
                if (true) {
                    throw new RuntimeException("Loi gia lap!");
                }
                return 1;
            })
            .exceptionally(loi -> {
                System.out.println("Da bat duoc loi: " + loi.getMessage());
                return -1; // gia tri THAY THE khi co loi
            });
        System.out.println("Ket qua sau khi xu ly loi: " + coLoi.get());
    }

    static void gia(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
