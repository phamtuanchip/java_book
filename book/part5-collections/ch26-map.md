# Chương 26 — HashMap, TreeMap, LinkedHashMap

## Mục tiêu học

Sau chương này, bạn sẽ:

- Dùng thành thạo `HashMap`: `put`, `get`, `remove`, `containsKey`, `getOrDefault`.
- Duyệt `Map` bằng 3 cách: `keySet()`, `values()`, `entrySet()` — biết khi nào dùng cách nào.
- Phân biệt `HashMap`/`LinkedHashMap`/`TreeMap`, tương tự bộ ba `Set` đã học ở Chương 25.
- Hiểu lại rõ hơn vai trò của `hashCode()`/`equals()` (Chương 20) trong việc `HashMap` tìm đúng key.
- Giải bài toán "đếm số lần xuất hiện" — một trong những ứng dụng phổ biến nhất của `Map`.

Code mẫu đầy đủ: [`code/ch26-map/`](../../code/ch26-map/).

## Thao tác cơ bản với `HashMap`

```java
Map<String, Integer> diem = new HashMap<>();
diem.put("Toan", 9);
diem.put("Toan", 10); // GHI DE - khong tao them cap moi, "Toan" van la MOT key duy nhat

diem.get("Toan");             // 10
diem.get("Ly");                // null - key khong ton tai, KHONG nem loi
diem.getOrDefault("Ly", 0);    // 0 - tra ve gia tri mac dinh neu key khong ton tai
diem.containsKey("Van");       // true/false
diem.remove("Toan");           // xoa cap co key "Toan"
diem.size();                   // so cap key-value hien co
```

Điểm khác biệt quan trọng nhất so với `List`/`Set` (chỉ số/phần tử): `Map` thao tác theo **khoá
(key)**, mỗi khoá tương ứng **đúng một** giá trị (value) tại một thời điểm — `put` với khoá đã tồn
tại sẽ **ghi đè** giá trị cũ, không tạo thêm cặp mới.

**Lưu ý về `get()` khi key không tồn tại**: trả về `null` (không ném lỗi) — nhắc lại nguy cơ
`NullPointerException` từ Chương 13 nếu bạn dùng ngay giá trị trả về mà không kiểm tra. Dùng
`getOrDefault(key, giaTriMacDinh)` là cách an toàn hơn khi bạn muốn có sẵn một giá trị dự phòng.

## Ba cách duyệt `Map`

```java
// Cach 1: chi can KEY
for (String mon : diem.keySet()) {
    System.out.println(mon);
}

// Cach 2: chi can VALUE
for (int diemSo : diem.values()) {
    System.out.println(diemSo);
}

// Cach 3: can CA HAI key va value cung luc
for (Map.Entry<String, Integer> entry : diem.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

`entrySet()` là cách **hiệu quả nhất** khi bạn cần cả key lẫn value trong cùng vòng lặp — mỗi
`Map.Entry` là một "cặp" đại diện đúng một dòng dữ liệu trong `Map`. Tránh cách viết kém hiệu quả
(dù vẫn đúng): duyệt `keySet()` rồi gọi `diem.get(key)` bên trong vòng lặp để lấy value — cách này
phải tra cứu lại `Map` một lần nữa cho mỗi key, trong khi `entrySet()` đã có sẵn cả hai.

## `LinkedHashMap` và `TreeMap`

Hoàn toàn tương tự bộ ba `Set` đã học ở Chương 25, áp dụng cho `Map`:

```java
Map<String, Integer> theoThuTu = new LinkedHashMap<>(); // giu dung thu tu KEY da them vao
Map<String, Integer> sapXep = new TreeMap<>();           // tu dong sap xep theo KEY
```

| Cần gì? | Chọn |
|---|---|
| Chỉ cần tra cứu nhanh, không quan tâm thứ tự | `HashMap` |
| Tra cứu nhanh, giữ đúng thứ tự các key đã thêm vào | `LinkedHashMap` |
| Tra cứu, và key luôn cần duyệt theo thứ tự sắp xếp | `TreeMap` |

## Vai trò của `hashCode()`/`equals()` trong `HashMap`

`HashMap` dùng **key** (không phải value) để tính `hashCode()` xác định vị trí lưu trữ, y hệt cách
`HashSet` dùng phần tử (Chương 25) — thực tế, `HashSet` **bên trong được cài đặt dựa trên**
`HashMap` (mỗi phần tử của `Set` là một key trong `HashMap` ẩn, value chỉ là giá trị đánh dấu). Hệ
quả: nếu bạn dùng một object tự định nghĩa **làm key** mà quên override `equals`/`hashCode` đúng
cách, `get()` với một object khác (dù nội dung giống hệt key gốc) sẽ **không tìm thấy** giá trị đã
lưu — vì `HashMap` coi hai object đó là hai key khác nhau hoàn toàn (giống hệt lỗi đã minh hoạ với
`HashSet` ở Chương 25).

## Bài tập kinh điển: đếm số lần xuất hiện

```java
Map<String, Integer> demSoLan = new HashMap<>();
for (String tu : mangTu) {
    if (demSoLan.containsKey(tu)) {
        demSoLan.put(tu, demSoLan.get(tu) + 1);
    } else {
        demSoLan.put(tu, 1);
    }
}
```

Đây là một trong những khuôn mẫu (pattern) dùng `Map` phổ biến nhất trong lập trình thực tế: đếm
tần suất xuất hiện của từng phần tử trong một tập dữ liệu. Cách viết gọn hơn dùng `merge()`:

```java
demSoLan.merge(tu, 1, Integer::sum);
```

`merge(key, giaTriMoi, hamKetHop)` tự động xử lý cả hai trường hợp: nếu `key` **chưa có**, gán
`giaTriMoi` (`1`); nếu `key` **đã có**, áp dụng `hamKetHop` lên (giá trị cũ, giá trị mới) để ra giá
trị cuối cùng. `Integer::sum` là **method reference** (Chương 30) — hiểu đơn giản ở đây là "hàm
cộng hai số lại" — trong trường hợp này nghĩa là "cộng dồn số đếm cũ với 1".

## Bài tập

1. Viết chương trình đếm số lần xuất hiện của từng ký tự trong một chuỗi (dùng `Map<Character, Integer>`,
   duyệt chuỗi bằng `charAt(i)` như Chương 11).
2. Dùng `Map<String, List<String>>` để nhóm một danh sách tên theo chữ cái đầu tiên (ví dụ: "An",
   "Anh" thuộc nhóm "A") — cần khởi tạo `List` mới cho key lần đầu xuất hiện trước khi thêm phần tử
   vào.
3. Viết phương thức tìm key có value **lớn nhất** trong một `Map<String, Integer>`, dùng vòng lặp
   `entrySet()`.

## Lỗi thường gặp

- **`NullPointerException` khi dùng luôn giá trị trả về của `get()` mà không kiểm tra `null`** —
  key không tồn tại trả về `null`, không ném lỗi. Dùng `getOrDefault` hoặc kiểm tra `containsKey`
  trước khi thao tác tiếp với giá trị lấy ra.
- **Dùng object tự định nghĩa làm key mà quên override `equals`/`hashCode`** — `get()` với một
  object khác object gốc (dù nội dung giống hệt) sẽ không tìm thấy giá trị, dù bạn "biết chắc" nó
  phải có trong `Map`. Xem lại phần "Vai trò của hashCode()/equals()" ở trên.
- **Nhầm lẫn `put()` ghi đè giá trị, tưởng nó "cộng thêm"** — `put("Toan", 5)` sau
  `put("Toan", 3)` cho kết quả `key "Toan" = 5`, không phải `8`. Muốn cộng dồn, dùng `merge()` hoặc
  tự lấy giá trị cũ ra cộng thêm rồi `put()` lại.
- **Sửa `Map` (thêm/xóa cặp) ngay trong vòng lặp `for-each` duyệt trên chính `Map` đó** — ném
  `ConcurrentModificationException` lúc chạy (sẽ giải thích rõ nguyên nhân kỹ thuật ở Chương 27,
  khi học `Iterator`). Nếu cần sửa trong lúc duyệt, dùng `Iterator` trực tiếp thay vì for-each.
