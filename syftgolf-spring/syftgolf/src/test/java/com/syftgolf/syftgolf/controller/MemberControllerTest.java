package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.TestPage;
import com.syftgolf.syftgolf.TestUtil;
import com.syftgolf.syftgolf.configuration.AppConfiguration;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.vm.member.MemberUpdateVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import com.syftgolf.syftgolf.service.MemberService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MemberControllerTest {

    private static final String MEMBERS = "/member";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    MemberService memberService;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    SocietyRepo societyRepo;


    @BeforeEach
    public void cleanup() {
        memberRepo.deleteAll();
        societyRepo.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postMember_whenMemberIsValid_MemberSavedToDatabase() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        postSignup(member, Object.class);
        assertThat(memberRepo.count()).isEqualTo(1);
    }

    @Test
    public void postMember_whenMemberIsValid_ReceiveSuccessMessage() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        ResponseEntity<GenericResponse> response = postSignup(member, GenericResponse.class);
        assertThat(response.getBody().getMessage()).isEqualTo("member saved");
    }

    @Test
    public void postMember_whenMemberIsValid_passwordIsHashedInDatabase() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        postSignup(member, Object.class);
        List<Member> users = memberRepo.findAll();
        Member inDB = users.get(0);
        assertThat(inDB.getPassword()).isNotEqualTo(member.getPassword());
    }

    @Test
    public void postMember_whenMemberHasNullUsername_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setUsername(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullFirstName_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setFirstName(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullSurname_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setSurname(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullEmail_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setEmail(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullMobile_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setMobile(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullCdh_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setCdh(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postMember_whenMemberHasNullPassword_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword(null);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasUsernameWithLessThanRequiredCharacters_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setUsername("abc");
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasPasswordWithLessThanRequiredCharacters_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setUsername("Pas");
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasPasswordWithMoreThanRequiredCharacters_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        String valueOf256Chars = IntStream.rangeClosed(1,256).mapToObj(x -> "a").collect(Collectors.joining());
        member.setPassword(valueOf256Chars);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasUserNameWithMoreThanRequiredCharacters_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        String valueOf256Chars = IntStream.rangeClosed(1,256).mapToObj(x -> "a").collect(Collectors.joining());
        member.setUsername(valueOf256Chars);
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllLowercase_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword("alllowercase");
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllUppercase_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword("ALLUPPERCASE");
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserHasPasswordWithAllNumbers_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword("012345678");
        ResponseEntity<Object> response = postSignup(member, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenUserIsInvalid_receiveApiError() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = new Member();
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        assertThat(response.getBody().getUrl()).isEqualTo("/management/member/1");
    }

    @Test
    public void postUser_whenUserIsInvalid_receiveApiErrorWithValidationErrors() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = new Member();
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        assertThat(response.getBody().getValidationErrors().size()).isEqualTo(7);
    }

    @Test
    public void postUser_whenUserHasNullUsername_receiveMessageOfNullErrorForUsername() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setUsername(null);
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("username")).isEqualTo("Username cannot be null");
    }

    @Test
    public void postUser_whenUserHasNullPassword_receiveMessageOfNullErrorForPassword() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword(null);
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("password")).isEqualTo("Cannot be null");
    }

    @Test
    public void postUser_whenUserHasNullFirstname_receiveMessageOfNullErrorForFirstName() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setFirstName(null);
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("firstName")).isEqualTo("Name cannot be null");
    }

    @Test
    public void postUser_whenUserHasIncorrectLengthMobile_receiveMessageLengthErrorForMobile() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setMobile("0745");
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("mobile")).isEqualTo("Please enter a valid mobile number which should be 11 digits long");
    }

    @Test
    public void postUser_whenUserHasInvalidPasswordPattern_receiveMessageOfPasswordPatternError() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = TestUtil.createValidMember();
        member.setPassword("alllowercase");
        ResponseEntity<ApiError> response = postSignup(member, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("password")).isEqualTo("Password must have at least one uppercase, one lowercase letter and one number");
    }

    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveBadRequest() {
        societyRepo.save(TestUtil.createValidSociety());
        memberRepo.save(TestUtil.createValidMember());

        Member user = TestUtil.createValidMember();
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveMessageOfDuplicateUserName() {
        societyRepo.save(TestUtil.createValidSociety());
        memberRepo.save(TestUtil.createValidMember());

        Member user = TestUtil.createValidMember();
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("username")).isEqualTo("This name is in use");
    }

    @Test
    public void getUsers_whenThereAreNoUsersInDB_receiveOK() {
        ResponseEntity<Object> response = getMembers(new ParameterizedTypeReference<Object>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getUsers_whenThereAreNoUsersInDB_receivePageWithZeroItems() {
        ResponseEntity<TestPage<Object>> response = getMembers(new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getTotalElements()).isEqualTo(0);
    }

    @Test
    public void getUsers_whenThereIsAUserInDB_receivePageWithUser() {
        societyRepo.save(TestUtil.createValidSociety());
        memberRepo.save(TestUtil.createValidMember());
        ResponseEntity<TestPage<Object>> response = getMembers(new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getNumberOfElements()).isEqualTo(1);
    }

    @Test
    public void getUsers_whenThereIsAUserInDB_receiveUserWithoutPassword() {
        societyRepo.save(TestUtil.createValidSociety());
        memberRepo.save(TestUtil.createValidMember());
        ResponseEntity<TestPage<Map<String, Object>>> response = getMembers(new ParameterizedTypeReference<TestPage<Map<String, Object>>>() {});
        Map<String, Object> entity = response.getBody().getContent().get(0);
        assertThat(entity.containsKey("password")).isFalse();
    }

    @Test
    public void getUsers_whenPageIsRequestedFor3ItemsPerPageWhereTheDatabaseHas20Users_receive3Users() {
        IntStream.rangeClosed(1, 20).mapToObj(i -> "test-user-"+i)
                .map(TestUtil::createValidUser)
                .forEach(memberRepo::save);
        String path = "/members/1" + "?page=0&size=3";
        ResponseEntity<TestPage<Object>> response = getMembers(path, new ParameterizedTypeReference<TestPage<Object>>() {});
        assertThat(response.getBody().getContent().size()).isEqualTo(3);
    }

    @Test
    public void putMember_whenUnauthorizedUserSendsTheRequest_receiveUnauthorized() {
        ResponseEntity<Object> response = putMember(123, null, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void putUser_whenAuthorizedUserSendsUpdateForAnotherUser_receiveForbidden() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = memberRepo.save(TestUtil.createValidMember());
        authenticate(member.getUsername());

        long anotherUserId = member.getId() + 123;
        ResponseEntity<Object> response = putMember(anotherUserId, null, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void putUser_whenUnauthorizedUserSendsTheRequest_receiveApiError() {
        ResponseEntity<ApiError> response = putMember(123, null, ApiError.class);
        assertThat(response.getBody().getUrl()).contains("/updateMember/123");
    }

    @Test
    public void putUser_whenAuthorizedUserSendsUpdateForAnotherUser_receiveApiError() {
        societyRepo.save(TestUtil.createValidSociety());
        Member member = memberService.save(TestUtil.createValidMember());
        authenticate(member.getUsername());

        long anotherUserId = member.getId() + 123;
        ResponseEntity<ApiError> response = putMember(anotherUserId, null, ApiError.class);
        assertThat(response.getBody().getUrl()).contains("users/"+anotherUserId);
    }



    private String readFileToBase64(String fileName) throws IOException {
        ClassPathResource imageResource = new ClassPathResource(fileName);
        byte[] imageArr = FileUtils.readFileToByteArray(imageResource.getFile());
        String imageString = Base64.getEncoder().encodeToString(imageArr);
        return imageString;
    }

    private MemberUpdateVM createValidUserUpdateVM() {
        MemberUpdateVM updatedMember = new MemberUpdateVM();
        updatedMember.setUsername("newUsernameName");
        return updatedMember;
    }

    private void authenticate(String username) {
        testRestTemplate.getRestTemplate()
                .getInterceptors().add(new BasicAuthenticationInterceptor(username, "P4ssword"));
    }

    public <T> ResponseEntity<T> postSignup(Object request, Class<T> response){
        return testRestTemplate.postForEntity("/management/member/1", request, response);
    }

    public <T> ResponseEntity<T> getMembers(ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange("/members/1", HttpMethod.GET, null, responseType);
    }

    public <T> ResponseEntity<T> getMembers(String path, ParameterizedTypeReference<T> responseType){
        return testRestTemplate.exchange(path, HttpMethod.GET, null, responseType);
    }

    public <T> ResponseEntity<T> getMembers(String username, Class<T> responseType){
        String path = "/members/1" + "/" + username;
        return testRestTemplate.getForEntity(path, responseType);
    }

    public <T> ResponseEntity<T> putMember(long id, HttpEntity<?> requestEntity, Class<T> responseType){
        String path = "/updateMember" + "/" + id;
        return testRestTemplate.exchange(path, HttpMethod.PUT, requestEntity, responseType);
    }


}
