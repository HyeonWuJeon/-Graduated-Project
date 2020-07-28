package com.example.demo.APITest;


@MyAnnotation("gachon") //value적용시 값을 명시할 필요없다 value  = "gachon" x 단 여러개속성을 정의 시 이름을 전부 줘야한다.
public class Book {
    private static String B = "Book";

    private static final String C = "Book";

    private String a = "a";

    public String d = "d";

    protected String e = "e";

    public Book() {

    }

    public Book(String a, String d, String e) {

        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }
}
