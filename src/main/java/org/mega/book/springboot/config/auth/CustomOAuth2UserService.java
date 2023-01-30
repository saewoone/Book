package org.mega.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.mega.book.springboot.config.auth.dto.OAuthAttributes;
import org.mega.book.springboot.config.auth.dto.SessionUser;
import org.mega.book.springboot.domain.user.User;
import org.mega.book.springboot.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository; //쿼리문 실행
    private final HttpSession httpSession; //http 세션 가져오기

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{ // 이게 로그인
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); //업캐스팅
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //내 기준 형제에서 재정의한거 갖다씀...... 나도 자식 쟤도 자식

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //구글이 보낸거에서 등록 아이디빼고

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName(); //이름도 빼고

        OAuthAttributes attributes = OAuthAttributes //이건 내가 정의한거
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user)); //(최상위인) 웹으로 user 올려보냈기 때문에 웹에서 다 쓸수있게됨
        //우리쪽에서 쓸수있게 해주는 애는 사실상 얘. 이름 나오는거 여기서 빼서 쓰는거 (컨트롤러에서)
        
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), //컬렉션에서 singleton 하나 만들거야
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
        //얘는 그냥 시큐리티에 알려주는거. 시큐리티에 등록.
        //끝나면 url /로 감
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) // 뺀 객체를 이걸로 바꿔치기(객체가 있으면)
                .orElse(attributes.toEntity()); // 없으면 객체 만듦
        return userRepository.save(user); //여기서 이제 db에 저장됨
        //entity => user의 엔티티.. 그걸 수정한다는 소리
        //있으면 수정해서 저장, 없으면 만들어서 저장
    }
}
