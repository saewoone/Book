package org.mega.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이 생성될 수 있는 위치를 지정 PARAMETER 지정하면 파라미터 선언 객체만 사용 가능 //매개변수(파라미터)에 넣는 어노테이션
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 클래스로 지정 LoginUser라는 이름을 가진 어노테이션 생성 //LoginUser를 어노테이션 이름으로 지정함
public @interface LoginUser {
}
