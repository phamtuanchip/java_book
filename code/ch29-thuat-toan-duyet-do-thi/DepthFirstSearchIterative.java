import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// Cung la DFS, nhung cai dat KHONG dung de quy - dung Deque lam STACK (LIFO - Lay
// Vao Sau Ra Truoc) thu cong. So sanh voi DepthFirstSearch.java (de quy) va
// BreadthFirstSearch.java (Queue/FIFO) de thay ro: DFS dung STACK, BFS dung QUEUE -
// CHI KHAC nhau o cau truc du lieu dung de "nho" dinh nao can xet tiep theo.
public class DepthFirstSearchIterative {
    private static void dfs(List<List<Integer>> doThi, int dinhBatDau) {
        boolean[] daTham = new boolean[doThi.size()];
        Deque<Integer> nganXep = new ArrayDeque<>(); // dung Deque lam Stack (khuyen dung hon java.util.Stack cu)

        nganXep.push(dinhBatDau);

        while (!nganXep.isEmpty()) {
            int dinh = nganXep.pop(); // lay dinh THEM VAO GAN DAY NHAT (LIFO)
            if (daTham[dinh]) {
                continue; // co the da them trung truoc do, bo qua neu da tham roi
            }
            daTham[dinh] = true;
            System.out.print(dinh + " ");

            // Them theo thu tu NGUOC de khi pop() ra van dung thu tu tu nhien
            List<Integer> danhSachKe = doThi.get(dinh);
            for (int i = danhSachKe.size() - 1; i >= 0; i--) {
                if (!daTham[danhSachKe.get(i)]) {
                    nganXep.push(danhSachKe.get(i));
                }
            }
        }
    }

    public static void main(String[] args) {
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

        System.out.print("Thu tu duyet DFS (khong de quy) tu dinh 0: ");
        dfs(doThi, 0); // ket qua giong het ban de quy: 0 1 3 4 2 5 6
        System.out.println();
    }
}
