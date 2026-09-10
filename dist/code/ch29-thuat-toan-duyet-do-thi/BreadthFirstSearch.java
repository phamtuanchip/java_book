import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// BFS (Breadth-First Search - duyet theo CHIEU RONG): tham TAT CA cac dinh o CUNG
// MOT "tang" (khoang cach tu dinh bat dau) truoc khi sang tang tiep theo. Dung
// Queue (FIFO - da hoc o Chuong 23) de dam bao dung thu tu nay.
public class BreadthFirstSearch {
    private static void bfs(List<List<Integer>> doThi, int dinhBatDau) {
        int soDinh = doThi.size();
        boolean[] daTham = new boolean[soDinh]; // danh dau dinh nao DA duyet, tranh lap vo han
        Queue<Integer> hangDoi = new LinkedList<>();

        daTham[dinhBatDau] = true;
        hangDoi.offer(dinhBatDau);

        while (!hangDoi.isEmpty()) {
            int dinh = hangDoi.poll(); // lay dinh DAU TIEN trong hang doi (FIFO)
            System.out.print(dinh + " ");

            for (int dinhKe : doThi.get(dinh)) {
                if (!daTham[dinhKe]) {
                    daTham[dinhKe] = true; // danh dau NGAY khi them vao hang doi, tranh them trung
                    hangDoi.offer(dinhKe);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Do thi minh hoa - dang CAY nhi phan don gian:
        //         0
        //        / \
        //       1   2
        //      / \ / \
        //     3  4 5  6
        int soDinh = 7;
        List<List<Integer>> doThi = new ArrayList<>();
        for (int i = 0; i < soDinh; i++) {
            doThi.add(new ArrayList<>());
        }

        doThi.get(0).add(1);
        doThi.get(0).add(2);
        doThi.get(1).add(3);
        doThi.get(1).add(4);
        doThi.get(2).add(5);
        doThi.get(2).add(6);

        System.out.print("Thu tu duyet BFS tu dinh 0: ");
        bfs(doThi, 0); // ket qua: 0 1 2 3 4 5 6 - dung TUNG TANG mot
        System.out.println();
    }
}
