package com.example.demo.APITest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Modifier;
import java.util.Arrays;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReflectTest {

    @Test
    public void 리플렉션() throws ClassNotFoundException, Exception {
        Class<Book> bookClass = Book.class;
        Class<?> aClass1 = Class.forName("com.example.demo.APITest.Book");  // 리플렉션사용
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {

            int modifiers = f.getModifiers();
            System.out.println("modifiers = " + modifiers);
            System.out.println(f);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));

        });

        //4. 클래스 내부필드 전부  가져오기
        System.out.println("-- 클래스 내부필드 전부 가져오기 --");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        //5. 필드의 값을 전부 가져오기
        System.out.println("-- 내부필드 값 전부 가져오기 --");
        Book book = new Book(); //값을 가져오기위해서는 인스턴스가 필요하다.
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true); //접근지시자를 무시한다
                System.out.printf("%s, %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.out.println("-- 메소드조회-- ");
        Arrays.stream(aClass1.getMethods()).forEach(System.out::println);

    }

    @Test
    public void 어노테이션() throws Exception {
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);
    }
}

//어노테이션은 주석과 마찬가지다. /** */ 근본적으로 주석과같은취급을받음.

// 정보가 클래스와 소스에는 남지만, 바이트코드를 로딩할시 메모리상에 남지않는다. 빼고로딩한다.
// 런타임까지도 어노테이션을 유지하고싶을경우 RetentionPolicy.Runtime