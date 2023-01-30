package org.mega.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.mega.book.springboot.config.auth.dto.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver { //로그인유저 조종할 핸들러
    //이걸로 로그인유저 조종한다는 소리...
    private final HttpSession httpSession;
    @Override
    public boolean supportsParameter(MethodParameter parameter){ //가져오는 parameter가 제대로인지 확인하는거
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; //null인지 아닌지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType()); //파라미터 클래스 맞게 들어왔나
        return isLoginUserAnnotation && isUserClass;
    }
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
        throws Exception{ //실행하는거. 어노테이션 붙은 쪽에서 가져오는거
        return httpSession.getAttribute("user");
    }

}   //이제 설정에 들어가야 돼요. 내가 만든 어노테이션 쓸 수 있게.
