import java.util.ArrayList;
import java.util.List;

// DFS (Depth-First Search - duyet theo CHIEU SAU): di THAT SAU theo MOT nhanh
// truoc, den khi khong con di tiep duoc nua moi QUAY LUI thu nhanh khac. Cai dat
// bang DE QUY (phuong thuc TU GOI LAI chinh no) - day la lan dau sach dung de quy,
// giai thich chi tiet o phan ly thuyet cua chuong.
public class DepthFirstSearch {
    private static void dfs(List<List<Integer>> doThi, int dinh, boolean[] daTham) {
        daTham[dinh] = true;
        System.out.print(dinh + " ");

        for (int dinhKe : doThi.get(dinh)) {
            if (!daTham[dinhKe]) {
                dfs(doThi, dinhKe, daTham); // GOI DE QUY - di sau vao nhanh nay TRUOC KHI xet nhanh khac
            }
        }
    }

    public static void main(String[] args) {
        // CUNG mot do thi voi BreadthFirstSearch.java, de de doi chieu ket qua:
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

        boolean[] daTham = new boolean[soDinh];
        System.out.print("Thu tu duyet DFS tu dinh 0: ");
        dfs(doThi, 0, daTham); // ket qua: 0 1 3 4 2 5 6 - di SAU het nhanh trai roi moi sang phai
        System.out.println();
    }
}
