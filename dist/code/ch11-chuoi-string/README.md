# Chương 11 — Code mẫu: String và StringBuilder

## `StringBasicsDemo.java`

```
javac StringBasicsDemo.java
java StringBasicsDemo
```

Các thao tác `String` cơ bản, và đặc biệt: minh hoạ trực tiếp lỗi kinh điển `==` vs `.equals()` khi
so sánh chuỗi (`new String("hello") == new String("hello")` cho `false`, trong khi
`.equals(...)` cho `true`), và hiện tượng String pool khiến hai literal giống nhau (`"hello"`) lại
`==` cho `true`.

## `StringBuilderDemo.java`

```
javac StringBuilderDemo.java
java StringBuilderDemo
```

Minh hoạ `StringBuilder` cho các thao tác nối/sửa chuỗi nhiều lần, và đo thời gian thực tế so sánh
nối chuỗi bằng `+` trong vòng lặp (chậm dần khi số lần lặp lớn) với `StringBuilder.append` (gần như
không đổi). Thử tăng `soLanNoi` lên `200_000` để thấy chênh lệch rõ hơn.
