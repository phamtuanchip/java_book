# Chương 30 — Code mẫu: lambda & functional interface

```
javac *.java
java LambdaBasicsDemo
java FunctionalInterfacesDemo
java MethodReferenceDemo
```

- `LambdaBasicsDemo.java` — chuyển từ anonymous class (Chương 19) sang lambda cho **cùng một**
  functional interface tự định nghĩa (`PhepToan`), minh hoạ lambda nhiều dòng, và quy tắc "effectively
  final" khi lambda dùng biến từ bên ngoài.
- `FunctionalInterfacesDemo.java` — 5 functional interface có sẵn trong `java.util.function`:
  `Predicate`, `Function`, `Consumer`, `Supplier`, `BiFunction`.
- `MethodReferenceDemo.java` — 4 loại method reference: static, instance của object cụ thể, instance
  trên tham số được truyền vào, và constructor.
