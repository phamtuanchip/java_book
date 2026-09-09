# Chương 8 — Vòng lặp: for, while, do-while

## Mục tiêu học

Sau chương này, bạn sẽ:

- Chọn đúng loại vòng lặp (`for`, `while`, `do-while`) tuỳ tình huống.
- Dùng `break` và `continue` đúng cách, hiểu khác biệt giữa hai lệnh này.
- Viết được vòng lặp lồng nhau, và dùng nhãn (label) khi cần thoát nhiều tầng vòng lặp cùng lúc.
- Tránh được lỗi vòng lặp vô hạn ngoài ý muốn.

Code mẫu đầy đủ: [`code/ch08-vong-lap/`](../../code/ch08-vong-lap/).

## `for` — khi biết trước (hoặc tính được) số lần lặp

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("i = " + i);
}
```

Ba phần trong ngoặc `for`, phân cách bởi `;`:

1. **Khởi tạo** (`int i = 1`) — chạy **đúng một lần** trước khi vòng lặp bắt đầu.
2. **Điều kiện** (`i <= 5`) — kiểm tra **trước mỗi lần lặp**; còn đúng thì tiếp tục, sai thì dừng.
3. **Cập nhật** (`i++`) — chạy **sau mỗi lần lặp**, trước khi kiểm tra điều kiện lần tiếp theo.

Biến `i` khai báo trong phần khởi tạo chỉ tồn tại **bên trong** vòng lặp (phạm vi biến — scope —
đã nhắc sơ ở Chương 6, sẽ nói kỹ hơn ở Chương 10).

## `while` — khi chưa biết trước số lần lặp

```java
double giaTri = 100;
while (giaTri >= 1) {
    System.out.println("giaTri = " + giaTri);
    giaTri = giaTri / 2;
}
```

`while` kiểm tra điều kiện **trước** mỗi lần chạy thân vòng lặp — nếu điều kiện sai ngay từ đầu,
thân vòng lặp **không chạy lần nào cả**. Dùng `while` khi số lần lặp phụ thuộc vào một điều kiện
thay đổi dần trong quá trình chạy (ở ví dụ trên: chia đôi giá trị tới khi nhỏ hơn 1 — không biết
trước chính xác sẽ lặp bao nhiêu lần nếu giá trị ban đầu thay đổi).

## `do-while` — luôn chạy ít nhất một lần

```java
int soLanThu = 0;
do {
    soLanThu++;
    System.out.println("Lan thu " + soLanThu);
} while (soLanThu < 3);
```

Khác biệt duy nhất nhưng quan trọng so với `while`: `do-while` kiểm tra điều kiện **sau** khi chạy
thân vòng lặp, nên thân vòng lặp **luôn chạy ít nhất một lần**, dù điều kiện sai ngay từ đầu. Dùng
khi bài toán tự nhiên đòi hỏi "làm trước, rồi mới kiểm tra có nên làm tiếp không" — ví dụ điển hình:
hiện menu và đọc lựa chọn người dùng ít nhất một lần trước khi hỏi có muốn tiếp tục không.

## `break` — thoát khỏi vòng lặp ngay lập tức

```java
for (int i = 1; i <= 100; i++) {
    if (i % 7 == 0) {
        System.out.println("Tim thay: " + i);
        break; // thoat vong lap NGAY, khong chay tiep cac gia tri i con lai
    }
}
```

`break` dừng vòng lặp hoàn toàn, chương trình tiếp tục chạy từ dòng code **ngay sau** vòng lặp.

## `continue` — bỏ qua phần còn lại của lần lặp hiện tại

```java
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // bo qua phan con lai CUA LAN LAP NAY, nhay sang i tiep theo
    }
    System.out.println("So le: " + i);
}
```

Khác với `break`, `continue` **không dừng cả vòng lặp** — nó chỉ bỏ qua phần code còn lại trong
thân vòng lặp **ở lần lặp hiện tại**, rồi tiếp tục sang lần lặp kế tiếp như bình thường (với `for`,
phần cập nhật `i++` vẫn chạy trước khi kiểm tra điều kiện tiếp theo).

## Vòng lặp lồng nhau (nested loop)

```java
for (int a = 1; a <= 3; a++) {
    for (int b = 1; b <= 3; b++) {
        System.out.println(a + " x " + b + " = " + (a * b));
    }
}
```

Vòng lặp trong (`b`) chạy **trọn vẹn từ đầu đến cuối** ứng với **mỗi** lần lặp của vòng lặp ngoài
(`a`). Với ví dụ trên: `a = 1` → vòng `b` chạy đủ 1,2,3; rồi `a = 2` → vòng `b` lại chạy đủ 1,2,3;
tương tự cho `a = 3`. Tổng số lần thân vòng lặp trong chạy là `3 × 3 = 9` lần.

### Nhãn (label) — thoát nhiều tầng vòng lặp cùng lúc

`break` thông thường chỉ thoát vòng lặp **gần nhất** chứa nó. Muốn thoát cả vòng lặp ngoài từ bên
trong vòng lặp trong, đặt **nhãn** trước vòng lặp ngoài:

```java
outerLoop:
for (int a = 1; a <= 3; a++) {
    for (int b = 1; b <= 3; b++) {
        if (a * b == 6) {
            System.out.println("Dung tai a=" + a + ", b=" + b);
            break outerLoop; // thoat CA HAI vong lap, khong chi vong trong
        }
    }
}
```

`continue nhãn` cũng hoạt động tương tự — bỏ qua lần lặp hiện tại của vòng lặp **có nhãn đó**, chứ
không phải vòng lặp gần nhất. Nhãn không dùng thường xuyên trong code thực tế (thường có cách viết
khác rõ ràng hơn, ví dụ tách thành phương thức riêng — sẽ học ở Chương 10), nhưng cần biết để đọc
hiểu khi gặp trong code người khác.

## Tránh vòng lặp vô hạn ngoài ý muốn

```java
int n = 1;
while (true) {
    if (n > 3) {
        break;
    }
    System.out.println("n = " + n);
    n++;
}
```

`while (true)` tạo vòng lặp chạy mãi mãi — chỉ dừng được nhờ `break` bên trong. Đây là mẫu hợp lệ
và hữu ích (ví dụ vòng lặp chính của một chương trình chờ nhập lệnh liên tục), nhưng **lỗi phổ biến
nhất của người mới** là quên viết điều kiện dừng (`break`, hoặc quên cập nhật biến điều kiện trong
`while`/`for`), khiến chương trình treo và phải tự tắt (Ctrl+C trong terminal, hoặc nút Stop trong
IDE).

## Bài tập

1. Chạy `FizzBuzz.java`, sau đó tự viết lại từ đầu **không nhìn code mẫu** — đây là bài tập kinh
   điển để luyện phản xạ với `for` + `if/else if/else`.
2. Viết chương trình dùng `while` tính tổng các số nguyên từ 1 đến khi tổng vượt quá 1000 lần đầu
   tiên, in ra tổng đó và số cuối cùng đã cộng vào.
3. Viết vòng lặp lồng nhau in ra hình tam giác dấu `*`:
   ```
   *
   **
   ***
   ****
   *****
   ```
4. Cố tình viết một vòng lặp `while` quên cập nhật biến điều kiện (gây vòng lặp vô hạn), quan sát
   chương trình treo, rồi dừng bằng Ctrl+C (terminal) hoặc nút Stop (IDE) — làm quen cảm giác này
   để nhận ra ngay khi gặp lại trong thực tế.

## Lỗi thường gặp

- **Vòng lặp vô hạn do quên cập nhật biến điều kiện** — ví dụ quên `i++` trong `while (i <= 10)`.
  Luôn kiểm tra: biến trong điều kiện có chắc chắn thay đổi dần tới khi điều kiện sai không?
- **Lỗi "off-by-one"**: dùng `<` thay vì `<=` (hoặc ngược lại) khiến vòng lặp chạy thiếu/thừa đúng
  một lần. Đây là lỗi cực kỳ phổ biến — luôn kiểm tra kỹ giá trị biên (giá trị đầu và cuối) có được
  xử lý đúng như mong đợi không.
- **Nhầm `break` với `continue`** — `break` thoát hẳn vòng lặp, `continue` chỉ bỏ qua lần lặp hiện
  tại. Nhầm hai lệnh này cho ra kết quả sai nhưng chương trình vẫn chạy bình thường (không có lỗi
  biên dịch/runtime), rất dễ bỏ sót khi kiểm tra.
- **Sửa giá trị biến điều khiển vòng lặp (`i`) bên trong thân `for`** — về mặt cú pháp hợp lệ,
  nhưng làm logic vòng lặp khó đoán (giá trị `i` bị thay đổi bởi cả phần cập nhật của `for` lẫn code
  trong thân), nên tránh trừ khi thực sự cần thiết và ghi chú rõ lý do.
