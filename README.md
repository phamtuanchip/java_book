# Java Book — Kế hoạch biên soạn sách

Sách lập trình Java bằng tiếng Việt, dành cho người **chưa biết gì về lập trình lẫn OOP**,
đi từ cài đặt JDK/JVM, cú pháp cơ bản, dạy hướng đối tượng (OOP) từ số 0 một cách chi tiết,
cho đến các chủ đề nâng cao (generics, collection, stream, đa luồng, JDBC, design pattern...),
kèm code mẫu chạy được cho từng chương.

> **Trạng thái: đang lên kế hoạch, chưa viết nội dung sách.** Repo hiện có 10 file `.java` mẫu cũ,
> đã được gom vào `code/algorithms/` (thuật toán tìm kiếm, duyệt đồ thị, cấu trúc dữ liệu và vài
> ví dụ ứng dụng nhỏ) — đây là code tham khảo có sẵn, sẽ được rà soát và ánh xạ vào đúng
> chương/phần tương ứng khi viết nội dung (xem mục 7), không phải nội dung sách đã hoàn thiện.
> Định dạng phát hành: **HTML trước**, **PDF và EPUB sau**.

## 1. Mục tiêu

- Dạy lập trình Java từ **con số 0** — không giả định người đọc từng lập trình hay biết OOP.
- Giảng OOP (class, encapsulation, kế thừa, đa hình, abstract/interface...) chi tiết, đầy đủ,
  đúng bản chất — đây là phần lõi của sách, không lướt qua.
- Mỗi bài/chương có ví dụ code mẫu, và code đó được lưu trên repo (chạy được, không phải snippet
  rời rạc).
- Xuất bản dạng "release" — chất lượng đủ để công bố công khai kèm code kèm theo.
- Định dạng phát hành: **HTML** trước, **PDF** và **EPUB** ở giai đoạn sau.

## 2. Đối tượng độc giả

- Người mới bắt đầu hoàn toàn: chưa từng lập trình, hoặc từng biết chút ít nhưng chưa hiểu OOP.
- Chưa biết JDK, JVM, JRE là gì — cần hướng dẫn từ bước cài đặt môi trường trên máy.
- Không giả định biết Maven/Gradle, IDE, hay bất kỳ công cụ nào ngoài Java thuần.
- Mục tiêu cuối: sau khi đọc xong, người học viết được ứng dụng console Java hoàn chỉnh, hiểu và
  áp dụng đúng tư duy hướng đối tượng, dùng được các thư viện chuẩn (collection, stream, I/O,
  concurrency) và các pattern/nguyên lý thiết kế cơ bản.

## 3. Cấu trúc thư mục dự kiến

```
java_book/
├── book/                        # Nội dung sách (Markdown), 1 file/chương + manifest.json (mục lục)
│   ├── part0-setup/             # Chương 1-4
│   ├── part1-fundamentals/      # Chương 5-11
│   ├── part2-oop-basics/        # Chương 12-15
│   ├── part3-oop-advanced/      # Chương 16-20
│   ├── part4-exceptions-generics/  # Chương 21-22
│   ├── part5-collections/       # Chương 23-29 (bao gồm thuật toán tìm kiếm, BFS/DFS)
│   ├── part6-functional/        # Chương 30-33
│   ├── part7-io/                # Chương 34-36
│   ├── part8-concurrency/       # Chương 37-40
│   ├── part9-tooling-testing/   # Chương 41-43
│   ├── part10-architecture/     # Chương 44-47
│   └── part11-appendix/         # Phụ lục A-B
├── code/                        # Code mẫu — mỗi chương có project/file Java riêng, chạy độc lập
│   ├── algorithms/              # Code mẫu thuật toán cũ, gốc cho Chương 28-29 (xem mục 7)
│   └── ch03-hello-world/, ch13-class-object/, ... ch47-capstone-project/
├── tools/                       # Script build HTML (tools/build.js) + CSS (tools/style.css)
├── dist/                        # HTML đã build (không commit — chạy `npm run build` để sinh ra)
└── README.md                    # File kế hoạch này
```

Quy ước: mỗi chương gồm 1 file nội dung trong `book/<part>/chXX-slug.md` + 1 project/file Java
riêng trong `code/chXX-slug/` (đặt tên khớp số chương), có README riêng hướng dẫn compile & chạy
bằng `javac`/`java` thuần (không phụ thuộc IDE). Từ Phần 9 trở đi (khi đã giới thiệu Maven/Gradle),
code mẫu có thể chuyển sang dùng build tool để minh hoạ đúng thực tế, nhưng vẫn ghi rõ cách chạy
thủ công tương đương.

## 4. Quy ước cho mỗi chương

Mỗi chương cần có:
1. Mục tiêu học (đọc xong làm được gì).
2. Lý thuyết/khái niệm nền, giải thích ngắn gọn, có sơ đồ nếu cần (đặc biệt các chương OOP: sơ đồ
   class, sơ đồ quan hệ kế thừa).
3. Ví dụ code từng bước, giải thích tại sao (không chỉ liệt kê code) — với các chương OOP, luôn
   giải thích **vì sao thiết kế như vậy**, không chỉ cú pháp.
4. Project/file mẫu hoàn chỉnh trong `code/`, compile & chạy được bằng `javac`/`java` (hoặc build
   tool ở các chương sau).
5. Bài tập/gợi ý mở rộng cuối chương.
6. Lỗi thường gặp + cách khắc phục (đúc kết từ thực tế, không lý thuyết suông) — đặc biệt các lỗi
   compile-time/runtime điển hình của người mới học OOP (NullPointerException, ClassCastException,
   nhầm lẫn overload/override...).

## 5. Mục lục sách (dự kiến)

### Phần 0 — Chuẩn bị môi trường
1. Giới thiệu Java: JDK/JRE/JVM là gì, "write once run anywhere", hệ sinh thái, các phiên bản LTS
2. Cài đặt JDK, chọn & cài IDE (IntelliJ IDEA Community / VS Code + Extension Pack for Java)
3. Chương trình Java đầu tiên: `javac`/`java`, cấu trúc file `.java`, `main` method, biên dịch thủ công dòng lệnh
4. Làm quen IDE: tạo project, chạy/debug cơ bản, giới thiệu sơ lược Maven/Gradle (sẽ học sâu ở Phần 9)

### Phần 1 — Nền tảng lập trình (chưa cần OOP)
5. Biến, kiểu dữ liệu nguyên thủy (int, double, boolean, char...), hằng số, ép kiểu
6. Toán tử, biểu thức, độ ưu tiên toán tử
7. Cấu trúc điều khiển: if/else, switch statement & switch expression (Java hiện đại)
8. Vòng lặp: for, while, do-while, break/continue, vòng lặp lồng nhau
9. Mảng (array) một chiều và nhiều chiều
10. Phương thức (method) tĩnh: tham số, giá trị trả về, overloading, phạm vi biến (scope)
11. Chuỗi (String): bất biến (immutability), các thao tác cơ bản, StringBuilder

### Phần 2 — Nhập môn OOP (từ số 0)
12. Vì sao cần OOP: tư duy lập trình thủ tục vs hướng đối tượng, các trụ cột của OOP (tổng quan)
13. Class & Object: định nghĩa class, field, constructor, từ khoá `this`, tạo và dùng object
14. Encapsulation (đóng gói): access modifier (public/private/protected/package-private), getter/setter, bất biến hoá đối tượng
15. Package & import: tổ chức project nhiều class, quy ước đặt tên, phạm vi truy cập giữa các package

### Phần 3 — OOP nâng cao
16. Kế thừa (Inheritance): `extends`, `super`, gọi constructor cha, method overriding vs overloading
17. Đa hình (Polymorphism): upcasting/downcasting, `instanceof`, liên kết động (dynamic binding)
18. Abstract class & Interface: khi nào dùng cái nào, `default`/`static` method trong interface, đa kế thừa interface
19. Lớp lồng (nested/inner class), anonymous class, enum nâng cao (enum có field/method/constructor riêng)
20. Lớp `Object`: `equals`/`hashCode`/`toString`, so sánh đối tượng đúng cách, `Comparable` vs `Comparator`

### Phần 4 — Xử lý lỗi & Generics
21. Exception handling: `try/catch/finally`, `try-with-resources`, checked vs unchecked exception, tạo custom exception
22. Generics: generic class/method, bounded type (`extends`), wildcard (`? extends`/`? super`), vì sao generics giúp an toàn kiểu

### Phần 5 — Collection Framework & Cấu trúc dữ liệu
23. Tổng quan Collection Framework: List/Set/Map/Queue, khi nào chọn cấu trúc nào
24. `ArrayList`, `LinkedList`: cơ chế bên trong, độ phức tạp thao tác, so sánh với mảng thuần
25. `HashSet`, `TreeSet`, `LinkedHashSet`
26. `HashMap`, `TreeMap`, `LinkedHashMap` — vai trò của `equals`/`hashCode` trong `HashMap`
27. Duyệt & sắp xếp: `Iterator`, `Comparator` nâng cao (chain, `Comparator.comparing`), lớp tiện ích `Collections`
28. Thuật toán tìm kiếm: Linear Search, Binary Search, Jump Search, Interpolation Search — so sánh độ phức tạp
29. Duyệt đồ thị/cây: BFS & DFS — ứng dụng thực tế (tìm đường đi ngắn nhất, duyệt cây thư mục...)

### Phần 6 — Lập trình hàm & Java hiện đại
30. Lambda expression, functional interface (`Runnable`, `Comparator`, interface tự định nghĩa), method reference
31. Stream API: tạo stream, `map`/`filter`/`reduce`, thao tác trung gian vs kết thúc, `collect`
32. `Optional` và xử lý null an toàn
33. Java hiện đại: `var` (type inference), record, sealed class, pattern matching cho `switch`/`instanceof`

### Phần 7 — Nhập/Xuất & Xử lý dữ liệu
34. File I/O: đọc/ghi file với `java.io` và `java.nio.file`, đọc/ghi theo dòng
35. Xử lý JSON/CSV: giới thiệu thư viện Gson/Jackson, đọc-ghi dữ liệu có cấu trúc
36. Serialization cơ bản: `Serializable`, khi nào nên/không nên dùng

### Phần 8 — Đa luồng & bất đồng bộ
37. Thread cơ bản: tạo thread bằng `Thread`/`Runnable`, vòng đời thread
38. Đồng bộ hoá: race condition, `synchronized`, `Lock`, deadlock và cách tránh
39. `ExecutorService`, thread pool, `CompletableFuture`
40. Giới thiệu Virtual Thread (Java 21+) và mô hình concurrency hiện đại

### Phần 9 — Công cụ, kiểm thử & quản lý dự án
41. Unit test với JUnit 5: viết test, assertion, test lifecycle
42. Build tool: Maven và Gradle — quản lý dependency, cấu trúc project chuẩn, chuyển các chương trước sang dùng build tool
43. Debug & logging: dùng debugger trong IDE, logging cơ bản với SLF4J/java.util.logging

### Phần 10 — Kiến trúc & thực hành nâng cao
44. Design pattern cơ bản: Singleton, Factory, Strategy, Observer — áp dụng đúng ngữ cảnh, không lạm dụng
45. Nguyên lý SOLID áp dụng trong Java, qua ví dụ tái cấu trúc code thực tế
46. Kết nối cơ sở dữ liệu: JDBC cơ bản, connection, PreparedStatement, tránh SQL injection
47. Dự án tổng hợp: xây dựng ứng dụng console hoàn chỉnh áp dụng toàn bộ kiến thức (OOP, collection, exception, I/O, JDBC)

### Phụ lục
- Phụ lục A — Bảng tổng hợp lỗi thường gặp & cách xử lý (tra cứu nhanh theo triệu chứng, ví dụ
  `NullPointerException`, `ClassCastException`, `ConcurrentModificationException`...)
- Phụ lục B — Tài liệu tham khảo & nguồn học thêm

## 6. Pipeline xuất bản (HTML → PDF → EPUB)

Dự kiến áp dụng lại đúng hướng đã dùng cho `android_book` (đã chứng minh hoạt động tốt): script
Node.js tự viết thay vì mdBook/Honkit/Pandoc, để chủ động tuỳ biến hiển thị sơ đồ (Mermaid có
pan/zoom) và giữ nhất quán CSS giữa các bản phát hành.

- **HTML (ưu tiên làm trước)**: `tools/build.js` (dùng `markdown-it`) đọc `book/manifest.json` +
  từng file `book/<part>/chXX-*.md`, sinh HTML đầy đủ vào `dist/` (sidebar điều hướng, prev/next,
  syntax highlight bằng highlight.js, sơ đồ Mermaid có zoom/pan cho sơ đồ class/kế thừa).
- **PDF (sau khi HTML ổn định)**: gộp toàn bộ chương thành một trang HTML dài (bìa, mục lục liên
  kết, mỗi chương một trang in riêng), dùng Puppeteer (Chromium headless) render rồi in thành PDF —
  không qua Pandoc/LaTeX, tái sử dụng đúng CSS/font đã dùng cho bản HTML.
- **EPUB (giai đoạn sau cùng)**: dùng lại đúng nguồn Markdown, không viết lại nội dung.

## 7. Lộ trình biên soạn (milestones)

1. ✅ Rà soát code mẫu cũ trong `code/algorithms/` và ánh xạ vào từng chương: `BinarySearch`/
   `LinearSearch`/`JumpSearch`/`InterpolationSearch` → Chương 28 (thuật toán tìm kiếm);
   `BreadthFirstSearch`/`DepthFirstSearch` → Chương 29 (duyệt đồ thị/cây); `HashTable` tham khảo
   cho Chương 26 (HashMap); `Calculator` tham khảo cho ví dụ OOP nhập môn (Phần 2);
   `InventoryManagementSystem`/`TextEditor` tham khảo cho dự án tổng hợp Chương 47. Đã dựng khung
   thư mục `book/` (11 phần + phụ lục, `manifest.json` đầy đủ 49 chương), `tools/`, `package.json`
   — chọn hướng script Node.js tự viết (`markdown-it` + `highlight.js`), xem mục 6.
2. ⬜ Viết & code mẫu xong Phần 0 (môi trường) — cột mốc "chạy được Hello World bằng dòng lệnh".
3. ⬜ Viết & code mẫu xong Phần 1 (nền tảng, chưa OOP).
4. ⬜ Viết & code mẫu xong Phần 2–3 (nhập môn OOP + OOP nâng cao) — phần lõi quan trọng nhất, cần
   review kỹ về mặt sư phạm (đúng thứ tự khái niệm, ví dụ dễ hiểu cho người chưa biết OOP).
5. ⬜ Viết & code mẫu xong Phần 4–5 (exception, generics, collection).
6. ⬜ Viết & code mẫu xong Phần 6–7 (lập trình hàm/stream, I/O).
7. ⬜ Viết & code mẫu xong Phần 8–9 (đa luồng, công cụ/kiểm thử).
8. ⬜ Viết & code mẫu xong Phần 10 + Phụ lục A-B, hoàn thiện dự án tổng hợp Chương 47.
9. ⬜ Build bản HTML hoàn chỉnh, rà soát toàn bộ code mẫu qua một đợt review độc lập.
10. ⬜ Build bản PDF (Puppeteer in từ HTML).
11. ⬜ Xuất bản EPUB.

## 8. Các quyết định đã chốt trong quá trình lên kế hoạch

- **Ngôn ngữ code mẫu**: Java thuần xuyên suốt toàn bộ sách; công cụ build (Maven/Gradle) chỉ
  được giới thiệu từ Phần 9 trở đi, các chương trước chạy bằng `javac`/`java` thuần để người mới
  không bị công cụ làm rối trước khi hiểu ngôn ngữ.
- **Phiên bản Java**: dự kiến chốt một bản LTS gần nhất (Java 21) làm chuẩn cho code mẫu, có ghi
  chú riêng cho các tính năng chỉ có ở bản mới (record, sealed class, pattern matching, virtual
  thread) để người dùng bản cũ hơn biết giới hạn.
- **Thứ tự dạy OOP**: tách riêng "Nhập môn OOP" (Phần 2 — class/object/encapsulation/package) và
  "OOP nâng cao" (Phần 3 — kế thừa/đa hình/abstract/interface) thay vì dồn chung một phần, vì đây
  là phần khó nhất với người mới, cần chia nhỏ để hấp thụ dần.
- **Công cụ build tài liệu**: dự kiến giữ hướng script Node.js tự viết giống `android_book` (xem
  mục 6) thay vì mdBook/Honkit/Pandoc, để tái sử dụng kinh nghiệm và pipeline đã có sẵn.
